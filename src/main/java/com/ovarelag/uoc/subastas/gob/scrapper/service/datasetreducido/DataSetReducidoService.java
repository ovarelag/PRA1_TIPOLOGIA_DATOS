package com.ovarelag.uoc.subastas.gob.scrapper.service.datasetreducido;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ovarelag.uoc.subastas.gob.scrapper.model.SubastaMiniatura;
import com.ovarelag.uoc.subastas.gob.scrapper.tools.ScrapperTools;

public class DataSetReducidoService implements IDataSetReducidoService {

	public final static String URL_BASE = "https://subastas.boe.es/";
	// TEST 8 RESULTADOS :
	// subastas_ava.php?campo[0]=SUBASTA.ORIGEN&dato[0]=J&campo[1]=SUBASTA.ESTADO&dato[1]=PU&campo[2]=BIEN.TIPO&dato[2]=M&dato[3]=&campo[4]=BIEN.DIRECCION&dato[4]=&campo[5]=BIEN.CODPOSTAL&dato[5]=&campo[6]=BIEN.LOCALIDAD&dato[6]=&campo[7]=BIEN.COD_PROVINCIA&dato[7]=&campo[8]=SUBASTA.POSTURA_MINIMA_MINIMA_LOTES&dato[8]=&campo[9]=SUBASTA.NUM_CUENTA_EXPEDIENTE_1&dato[9]=&campo[10]=SUBASTA.NUM_CUENTA_EXPEDIENTE_2&dato[10]=&campo[11]=SUBASTA.NUM_CUENTA_EXPEDIENTE_3&dato[11]=&campo[12]=SUBASTA.NUM_CUENTA_EXPEDIENTE_4&dato[12]=&campo[13]=SUBASTA.NUM_CUENTA_EXPEDIENTE_5&dato[13]=&campo[14]=SUBASTA.ID_SUBASTA_BUSCAR&dato[14]=&campo[15]=SUBASTA.FECHA_FIN_YMD&dato[15][0]=&dato[15][1]=&campo[16]=SUBASTA.FECHA_INICIO_YMD&dato[16][0]=&dato[16][1]=&page_hits=40&sort_field[0]=SUBASTA.FECHA_FIN_YMD&sort_order[0]=desc&sort_field[1]=SUBASTA.FECHA_FIN_YMD&sort_order[1]=asc&sort_field[2]=SUBASTA.HORA_FIN&sort_order[2]=asc&accion=Buscar
	// public final static String URL_BUSQUEDA =
	// "subastas_ava.php?campo[0]=SUBASTA.ORIGEN&dato[0]=J&campo[1]=SUBASTA.ESTADO&dato[1]=PU&campo[2]=BIEN.TIPO&dato[2]=M&dato[3]=&campo[4]=BIEN.DIRECCION&dato[4]=&campo[5]=BIEN.CODPOSTAL&dato[5]=&campo[6]=BIEN.LOCALIDAD&dato[6]=&campo[7]=BIEN.COD_PROVINCIA&dato[7]=&campo[8]=SUBASTA.POSTURA_MINIMA_MINIMA_LOTES&dato[8]=&campo[9]=SUBASTA.NUM_CUENTA_EXPEDIENTE_1&dato[9]=&campo[10]=SUBASTA.NUM_CUENTA_EXPEDIENTE_2&dato[10]=&campo[11]=SUBASTA.NUM_CUENTA_EXPEDIENTE_3&dato[11]=&campo[12]=SUBASTA.NUM_CUENTA_EXPEDIENTE_4&dato[12]=&campo[13]=SUBASTA.NUM_CUENTA_EXPEDIENTE_5&dato[13]=&campo[14]=SUBASTA.ID_SUBASTA_BUSCAR&dato[14]=&campo[15]=SUBASTA.FECHA_FIN_YMD&dato[15][0]=&dato[15][1]=&campo[16]=SUBASTA.FECHA_INICIO_YMD&dato[16][0]=&dato[16][1]=&page_hits=40&sort_field[0]=SUBASTA.FECHA_FIN_YMD&sort_order[0]=desc&sort_field[1]=SUBASTA.FECHA_FIN_YMD&sort_order[1]=asc&sort_field[2]=SUBASTA.HORA_FIN&sort_order[2]=asc&accion=Buscar";
	public final static String URL_BUSQUEDA = "./subastas_ava.php?campo%5B0%5D=SUBASTA.ORIGEN&dato%5B0%5D=&campo%5B1%5D=SUBASTA.ESTADO&dato%5B1%5D=&campo%5B2%5D=BIEN.TIPO&dato%5B2%5D=&campo%5B4%5D=BIEN.DIRECCION&dato%5B4%5D=&campo%5B5%5D=BIEN.CODPOSTAL&dato%5B5%5D=&campo%5B6%5D=BIEN.LOCALIDAD&dato%5B6%5D=&campo%5B7%5D=BIEN.COD_PROVINCIA&dato%5B7%5D=15&campo%5B8%5D=SUBASTA.POSTURA_MINIMA_MINIMA_LOTES&dato%5B8%5D=&campo%5B9%5D=SUBASTA.NUM_CUENTA_EXPEDIENTE_1&dato%5B9%5D=&campo%5B10%5D=SUBASTA.NUM_CUENTA_EXPEDIENTE_2&dato%5B10%5D=&campo%5B11%5D=SUBASTA.NUM_CUENTA_EXPEDIENTE_3&dato%5B11%5D=&campo%5B12%5D=SUBASTA.NUM_CUENTA_EXPEDIENTE_4&dato%5B12%5D=&campo%5B13%5D=SUBASTA.NUM_CUENTA_EXPEDIENTE_5&dato%5B13%5D=&campo%5B14%5D=SUBASTA.ID_SUBASTA_BUSCAR&dato%5B14%5D=&campo%5B15%5D=SUBASTA.FECHA_FIN_YMD&dato%5B15%5D%5B0%5D=&dato%5B15%5D%5B1%5D=&campo%5B16%5D=SUBASTA.FECHA_INICIO_YMD&dato%5B16%5D%5B0%5D=&dato%5B16%5D%5B1%5D=&page_hits=200&sort_field%5B0%5D=SUBASTA.FECHA_FIN_YMD&sort_order%5B0%5D=desc&sort_field%5B1%5D=SUBASTA.FECHA_FIN_YMD&sort_order%5B1%5D=asc&sort_field%5B2%5D=SUBASTA.HORA_FIN&sort_order%5B2%5D=asc&accion=Buscar";
	public final static String PATH_CSV_DATA_SET_REDUCIDO = "dataSets/dataSetReducido.csv";
	public final static int NUM_MINIATURAS_PAGINA = 200;
	public ScrapperTools scrapperTools = new ScrapperTools();
	static Logger logger = LoggerFactory.getLogger(DataSetReducidoService.class);

	private String URLActual = "";

	/**
	 * Método principal que descarga las paginas, parsea el contenido y almacena
	 * el dataset en formato csv.
	 */
	public void mainServiceCrearYAlmacenarDataset() {

		String URL = URL_BASE + URL_BUSQUEDA;

		List<SubastaMiniatura> listaMiniaturas = new ArrayList<SubastaMiniatura>();
		logger.info("------------------------------------------------------\n" + "\t Iniciando scraping en URL: " + URL
				+ "\n------------------------------------------------------");

		List<String> listaUrlProcesadas = new ArrayList<String>();
		int contadorUrlProcesadas = 0;

		List<SubastaMiniatura> listaMiniaturasExtraidasPorCadaScrap = new ArrayList<SubastaMiniatura>();
		StringBuilder nextScrapUrl = new StringBuilder("");
		Boolean varControl = true;
		do {
			try {

				listaMiniaturas = crearListaMiniaturas(URL, nextScrapUrl,
						contadorUrlProcesadas * NUM_MINIATURAS_PAGINA);
				contadorUrlProcesadas++;
				almacenarDataSetFormatoCSV(listaMiniaturas, contadorUrlProcesadas);
			} catch (Exception e) {
				logger.error("Exception!! --> ", e.getCause());
			}
			listaUrlProcesadas.add(URL);

			if (listaMiniaturas != null) {
				listaMiniaturasExtraidasPorCadaScrap.addAll(listaMiniaturas);
			}

			URL = nextScrapUrl.toString();

			varControl = !URL.isEmpty();

		} while (varControl);

		almacenarDataSetCompleto(listaMiniaturasExtraidasPorCadaScrap);
	}

	/**
	 * @param listaMiniaturasExtraidasPorCadaScrap
	 */
	private void almacenarDataSetCompleto(List<SubastaMiniatura> listaMiniaturasExtraidasPorCadaScrap) {
		try {
			this.almacenarDataSetFormatoCSV(listaMiniaturasExtraidasPorCadaScrap, null);
		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el enlace de las 200 miniaturas siguientes con el objetivo de que
	 * el scrapper haga su trabajo de forma automatizada.
	 * 
	 * @param doc
	 *            : Documento que queremos parsear para extraer el enlace que
	 *            vamos a utilizar para seguir navegando
	 * @return String con la URL absoluta a la que apunta el enlace de pagina
	 *         siguiente. Si no existe este enlace, devuelve null con el
	 *         objetivo de parar el scrapeo de miniaturas.
	 * 
	 * @throws IOException
	 */
	public static String scrapEnlaceSiguientePagina(Document doc, StringBuilder nextPageToScrap) throws IOException {
		if (doc != null) {
			String querySiguienteBusqueda = "div.paginar2:nth-child(6) > ul:nth-child(1) > li:last-child > a:last-child";

			Elements nodoProxBusqueda = doc.select(querySiguienteBusqueda);
			// Reseteamos el string
			nextPageToScrap.setLength(0);
			logger.debug("nodoProxBusqueda: " + nodoProxBusqueda);

			if (nodoProxBusqueda.isEmpty()) {
				logger.info("No se encontraron más enlaces!! ** FIN SCRAPPING **");
				return null;
			} else {
				nextPageToScrap.append(URL_BASE).append(nodoProxBusqueda.attr("href").toString());
				logger.info("Next scrap: " + nextPageToScrap.toString());
				return nextPageToScrap.toString();
			}
		}
		return null;
	}

	/**
	 * Dado un documento HTML de la pagina de subastas del BOE, lo parsea y
	 * obtiene una lista con las miniaturas extraidas.
	 * 
	 * @param html
	 *            : documento HTML que vamos a parsear.
	 * @param indiceInicial
	 * @return Lista de miniaturas lista para exportar a CSV.
	 * @throws IOException
	 */
	@Override
	public List<SubastaMiniatura> scrapListaMiniaturasFromHTML(String html, StringBuilder nextScrapURL,
			int indiceInicial) throws IOException {
		int contadorTotalElementos = 0;
		List<SubastaMiniatura> listaMiniaturasPagina = null;
		if (!html.isEmpty()) {
			Document doc = Jsoup.parse(html);

			listaMiniaturasPagina = parsearHtmlConMiniaturas(doc, indiceInicial);
			contadorTotalElementos = contadorTotalElementos + listaMiniaturasPagina.size();
			scrapEnlaceSiguientePagina(doc, nextScrapURL);
			logger.info("scrapListaMiniaturasFromHTML valor de nextScrapURL:  " + nextScrapURL);

		}
		return listaMiniaturasPagina;
	}

	/**
	 * @param
	 * @param doc
	 * @param indiceInicial
	 * @param enlacesIt
	 * @param numElementosEncontrados
	 */
	private static List<SubastaMiniatura> parsearHtmlConMiniaturas(Document doc, int indiceInicial) {

		String queryCuentaEnlaces = "html body div#contenedor div#l-cuerpo div#l-unaColumna div#l-contenido div.listadoResult ul li.resultado-busqueda div.enlacesMas a.resultado-busqueda-link-defecto";
		Iterator<Element> enlacesIt = doc.select(queryCuentaEnlaces).listIterator();
		int numElementosEncontrados = doc.select(queryCuentaEnlaces).size();

		List<SubastaMiniatura> toReturnListSubastaMin = new ArrayList<SubastaMiniatura>();
		String queryInfoMiniatura = "li.resultado-busqueda:nth-child(::nodo::) > p";
		logger.info("Nodo en proceso: " + replaceToken(queryInfoMiniatura, 1));

		int numElementosMiniatura = doc.select(replaceToken(queryInfoMiniatura, 1)).size();
		logger.info("Componentes miniatura: " + numElementosMiniatura);

		for (int i = 1; i <= numElementosEncontrados; i++) {
			String queryParsearMiniatura = replaceToken(queryInfoMiniatura, i);
			Element enlace = enlacesIt.next();
			Elements miniatura = doc.select(queryParsearMiniatura);
			logger.info("--------------------------------------------------------------");
			logger.info("Nodo en proceso: " + i);
			logger.debug(("Query parser: " + queryParsearMiniatura));
			SubastaMiniatura encontrado = null;
			try {
				encontrado = procesarMiniatura(i + indiceInicial, enlace, miniatura);
			} catch (Exception e) {
				logger.error("Excepcion procesando elemento encontrado con id: " + i);
			}
			if (encontrado != null) {
				toReturnListSubastaMin.add(encontrado);
			}
			logger.info("--------------------------------------------------------------");
		}
		return toReturnListSubastaMin;
	}

	/**
	 * @param queryInfoMiniatura
	 * @param i
	 * @return
	 */
	private static String replaceToken(String queryInfoMiniatura, int i) {
		return queryInfoMiniatura.replace("::nodo::", String.valueOf(i));
	}

	/**
	 * Con los elementos de una de las miniatura extraidas de la pagina de
	 * resultados de busqueda, y su enlace a la pagina completa, construye
	 * objetos de tipo SubastaMiniatura
	 * 
	 * @param id
	 *            : Identificador que se asignará a la miniatura.
	 * @param enlace
	 *            : Elemento con la URL para accedder a la información completa
	 *            de la subasta.
	 * @param miniatura
	 *            : Elementos con la información de la miniatura a partir del
	 *            cual extraeremos los datos de la miniatura.
	 * @return Objeto de tipo SubastaMiniatura creado a partir del parseo de los
	 *         nodos que se pasan como parámetro.
	 */
	private static SubastaMiniatura procesarMiniatura(int id, Element enlace, Elements miniatura) {
		String absolutePath = URL_BASE + enlace.attr("href").toString();
		logger.info("Enlace url subasta completa: " + absolutePath);

		Iterator<?> miniaturaIt = miniatura.listIterator();

		SubastaMiniatura toReturnElemento = null;

		while (miniaturaIt.hasNext()) {
			String valor = miniaturaIt.next().toString().replaceAll("<p class=\"epigrafeDpto\">", "").replaceAll("</p>",
					"");
			String titulo = valor;
			logger.info("titulo " + titulo);

			valor = miniaturaIt.next().toString().replaceAll("<p class=\"epigrafeDpto\">", "").replaceAll("</p>", "");
			String juzgado = valor;
			logger.info("juzgado" + juzgado);

			valor = miniaturaIt.next().toString().replaceAll("<p class=\"epigrafeDpto\">", "").replaceAll("</p>", "");
			String expediente = valor;
			logger.info("expediente" + expediente);

			valor = miniaturaIt.next().toString().replaceAll("<p class=\"epigrafeDpto\">", "").replaceAll("</p>", "");
			String estado = valor;
			logger.info("estado" + estado);

			valor = miniaturaIt.next().toString().replaceAll("<p class=\"documento\">", "").replaceAll("</p>", "");
			String descripcion = valor;
			logger.info("descripcion" + descripcion);

			toReturnElemento = new SubastaMiniatura(id, titulo, juzgado, expediente, estado, descripcion, absolutePath);
			logger.info("Elemento creado con éxito! \t-->\t" + toReturnElemento.toString());

		}

		return toReturnElemento;

	}

	@Override
	public List<SubastaMiniatura> crearListaMiniaturas(String urlBase, StringBuilder nextScrapURL, int indiceInicial)
			throws IOException {
		logger.info("URL scrap creacion miniaturas: " + urlBase);
		Path pathToDownloadedFile = FileSystems.getDefault().getPath("html_descargado",
				"DESCARGA_" + indiceInicial / 200 + ".html");

		String html = scrapperTools.getUrl(urlBase, pathToDownloadedFile);
		List<SubastaMiniatura> listaSubastasMiniatura = null;

		// Reseteamos el string builder
		nextScrapURL.setLength(0);

		listaSubastasMiniatura = scrapListaMiniaturasFromHTML(html, nextScrapURL, indiceInicial);
		logger.info("Proximo scrap en:" + nextScrapURL);
		return listaSubastasMiniatura;
	}

	@Override
	public void almacenarDataSetFormatoCSV(List<SubastaMiniatura> listaMiniaturas, Integer contadorUrlProcesadas)
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		Path pathToFile = null;
		if (contadorUrlProcesadas != null) {
			pathToFile = Paths.get(PATH_CSV_DATA_SET_REDUCIDO + "-parte" + contadorUrlProcesadas);
		} else {
			pathToFile = Paths.get(PATH_CSV_DATA_SET_REDUCIDO);
		}
		logger.info("Path data set CSV: " + pathToFile.toAbsolutePath());

		scrapperTools.exportToCSVByList(listaMiniaturas, pathToFile);
		logger.info("Data set almacenado con exito! Path : " + pathToFile.toAbsolutePath());

	}

}
