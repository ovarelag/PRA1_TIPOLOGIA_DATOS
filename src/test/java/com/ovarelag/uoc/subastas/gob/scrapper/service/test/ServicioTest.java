/**
 * 
 */
package com.ovarelag.uoc.subastas.gob.scrapper.service.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ovarelag.uoc.subastas.gob.scrapper.model.SubastaMiniatura;
import com.ovarelag.uoc.subastas.gob.scrapper.service.datasetreducido.DataSetReducidoService;
import com.ovarelag.uoc.subastas.gob.scrapper.service.datasetreducido.IDataSetReducidoService;
import com.ovarelag.uoc.subastas.gob.scrapper.tools.ScrapperTools;

/**
 * @author oscar
 *
 */
public class ServicioTest {

	/**
	 * Test method for
	 * {@link com.ovarelag.uoc.subastas.gob.scrapper.service.datasetreducido.DataSetReducidoService#crearYAlmacenarDataset()}.
	 */

	IDataSetReducidoService service = new DataSetReducidoService();
	List<SubastaMiniatura> listaMiniaturas = this.mockListaMiniaturas();
	public ScrapperTools scrapperTools = new ScrapperTools();

	static Logger logger = LoggerFactory.getLogger(ServicioTest.class);

	// @Ignore
	@Test
	public void testBugURL() throws IOException {
		Path pathToDownloadedFile = FileSystems.getDefault().getPath("logs", "TESTURL" + ".html");
		String urlBase = DataSetReducidoService.URL_BASE + DataSetReducidoService.URL_BUSQUEDA;
		logger.info("URL scrap creacion miniaturas: " + urlBase);
		String html = scrapperTools.getUrl(urlBase, pathToDownloadedFile);
		List<SubastaMiniatura> listaSubastasMiniatura = null;

		// Reseteamos el string builder

		StringBuilder strBuilderNextScrapURL = new StringBuilder();
		strBuilderNextScrapURL.setLength(0);

		listaSubastasMiniatura = service.scrapListaMiniaturasFromHTML(html, strBuilderNextScrapURL, 0);
		logger.info(listaSubastasMiniatura.toString());
		logger.info("Proximo scrap en:" + strBuilderNextScrapURL);

		// https: //
		// subastas.boe.es/subastas_ava.php?accion=Mas&id_busqueda=_RGdlaDR2UXgrWWlnV01jMEU1dWtjSEYxemJoYTY5SFVXYmI1SHAwNWJZYml3UmxUYTVHVnI0K28xWGw4dmxnaEtBcU1ZenNEWXl6TjN1cEVhb0Z5V3RiMjJ1S0dycW9UM3EzNDJMT2VGeHJLMVBsZjNXOSt0d3cxTGR1bWhQQW9Fc2QydDE4S3gvODZncFJ4TWdDa0V0YzBHbGMwZG1qMW1PTjdkUDdvRGN2RDkvbjhBcXluTWdHMG01aVg2OXNtQ1ZEalY3VUZFVFQvY2N0eVVKU1NFMG5NcnU5TjRzWDV2akxYblZ4aGN1dmE1UVRlZUVqdmhnRWxIejNFb082ZmZlYkkrRk1EWHhucklXQXNEOU90T2toNzB0MzBTNjBuakJGUnN4Zk54VG89-200-200
		Path pathToPagina2 = FileSystems.getDefault().getPath("logs", "nextScrapURL" + ".html");
		html = scrapperTools.getUrl(strBuilderNextScrapURL.toString(), pathToPagina2);

		// Reseteamos el string builder
		strBuilderNextScrapURL.setLength(0);

		listaSubastasMiniatura = service.scrapListaMiniaturasFromHTML(html, strBuilderNextScrapURL, 200);
		logger.info(listaSubastasMiniatura.toString());
		logger.info("Proximo scrap en:" + strBuilderNextScrapURL);

	}

	@Test
	public void testCrearYAlmacenarDataset() throws IOException {
		service.mainServiceCrearYAlmacenarDataset();
	}

	private List<SubastaMiniatura> mockListaMiniaturas() {
		SubastaMiniatura miniatura1 = new SubastaMiniatura(1, "titulo1", "juzgado1", "expedienmte1", "estado1", "desc1",
				"aa.com");
		SubastaMiniatura miniatura2 = new SubastaMiniatura(2, "titulo2", "juzgado2", "expedienmte2", "estado2", "desc2",
				"aa.com");
		SubastaMiniatura miniatura3 = new SubastaMiniatura(3, "titulo3", "juzgado3", "expedienmte3", "estado3", "desc3",
				"aa.com");
		List<SubastaMiniatura> toReturnList = new ArrayList<SubastaMiniatura>();
		toReturnList.add(miniatura1);
		toReturnList.add(miniatura2);
		toReturnList.add(miniatura3);
		return toReturnList;
	}

	// @Ignore
	@Test
	public void testAlmacenarDataSetFormatoCSV() {

		try {
			service.almacenarDataSetFormatoCSV(listaMiniaturas, null);
			Assert.assertTrue("Ok", 1 == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception: " + e.getStackTrace());

		}
	}

}
