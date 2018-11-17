/**
 * 
 */
package com.ovarelag.uoc.subastas.gob.scrapper.service.datasetreducido;

import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ovarelag.uoc.subastas.gob.scrapper.model.SubastaMiniatura;

/**
 * @author oscar
 *
 */
public interface IDataSetReducidoService {

	public void mainServiceCrearYAlmacenarDataset() throws IOException;

	/**
	 * Dada una lista de objetos de subastas en miniaturas, construye un archivo
	 * CSV y lo almacena en la ruta relativa definida en la constante
	 * DatePATH_CSV_DATA_SET_REDUCIDO
	 * 
	 * @param listaMiniaturas
	 *            : Lista de miniaturas utilizada para construir el data set.
	 * @param numeroParte
	 *            : indica si al archivo se añade la coletilla parte-NUMEROPARTE
	 *            al final de su nombre.
	 * 
	 * @throws IOException
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 */
	void almacenarDataSetFormatoCSV(List<SubastaMiniatura> listaMiniaturas, Integer numeroParte)
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

}
