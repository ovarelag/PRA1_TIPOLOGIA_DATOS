/**
 * 
 */
package com.ovarelag.uoc.subastas.gob.scrapper.service.datasetcompleto;

import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ovarelag.uoc.subastas.gob.scrapper.model.SubastaMiniatura;
import com.ovarelag.uoc.subastas.gob.scrapper.model.SubastaRow;

/**
 * @author oscar
 *
 */
public interface IDataSetCompletoService {

	public void mainServiceCrearYAlmacenarDataset() throws IOException;

	/**
	 * Dada una lista de objetos de subastas en miniaturas, construye un archivo
	 * CSV y lo almacena en la ruta relativa definida en la constante
	 * DatePATH_CSV_DATA_SET_REDUCIDO
	 * 
	 * @param listaSubastas
	 *            : Lista de subastas utilizada para generar el data set.
	 * 
	 * @throws IOException
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 */
	void almacenarDataSetFormatoCSV(List<SubastaRow> listaSubastas)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

	/**
	 * Crea una lista de miniaturas a partir de una URL. Parsea el el codigo
	 * HTML y construye objetos sencillos de manipular
	 * 
	 * @param urlBase
	 *            : URL utilizada para extraer las miniaturas.
	 * @param nextScrapURL
	 *            : Objeto con la siguiente URL a la que se va a navegar que se
	 *            extrae de la pagina que estamos navegando actualmente
	 * 
	 * @return Lista con las miniaturas extraidas de la URL.
	 * @throws IOException
	 */
	List<SubastaMiniatura> crearListaMiniaturas(String urlBase, StringBuilder nextScrapURL, int i) throws IOException;

	List<SubastaMiniatura> scrapListaMiniaturasFromHTML(String html, StringBuilder nextScrapURL, int indiceInicial)
			throws IOException;

	void almacenarDataSetFormatoCSV(List<?> listaMiniaturas, Integer contadorUrlProcesadas)
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException;

}
