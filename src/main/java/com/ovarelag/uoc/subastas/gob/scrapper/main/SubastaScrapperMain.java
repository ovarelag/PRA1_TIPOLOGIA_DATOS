package com.ovarelag.uoc.subastas.gob.scrapper.main;

import java.io.IOException;

import com.ovarelag.uoc.subastas.gob.scrapper.service.datasetreducido.DataSetReducidoService;
import com.ovarelag.uoc.subastas.gob.scrapper.service.datasetreducido.IDataSetReducidoService;

public class SubastaScrapperMain {

	/**
	 * Realiza la operación de crawwlin en la pagina del BOE y construye dos
	 * data sets.
	 * 
	 * Primero construye un data set reducido con información resumida de todas
	 * la miniaturas obtenidas.
	 * 
	 * A continuación lee el data set reducido y construye un data set ampliado
	 * con toda la informacion de la subasta.
	 * 
	 * Ambos data sets se vuelcan en sendos archivos CSV. - DataSetReducido.csv:
	 * - DataSetAmpliado.csv:
	 * 
	 * * Ver archivo para más información sobre el modelo datos.
	 * 
	 * 
	 */

	public static void main(String[] args) throws IOException {
		IDataSetReducidoService servicio = new DataSetReducidoService();
		servicio.mainServiceCrearYAlmacenarDataset();
	}

}