package com.ovarelag.uoc.subastas.gob.scrapper.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ovarelag.uoc.subastas.gob.scrapper.model.skeleton.SkeletonEntity;

public class ScrapperTools {

	static Logger logger = LoggerFactory.getLogger(ScrapperTools.class);

	public String getUrl(String url, Path htmlPath) {
		URL urlObj = null;
		htmlPath.toFile().getParentFile().mkdirs();
		try {
			urlObj = new URL(url);
			logger.info("Objeto URL creado con exito " + urlObj.toString());
		} catch (MalformedURLException e) {
			logger.error("Error creando objeto URL ", e);
		}
		BufferedReader reader = null;
		BufferedWriter writer = null;
		String totalData = "";
		if (urlObj != null) {
			try {
				reader = new BufferedReader(new InputStreamReader(urlObj.openStream()));
				writer = new BufferedWriter(new FileWriter(htmlPath.toString()));
				String line;

				while ((line = reader.readLine()) != null) {
					totalData = totalData + line;
					writer.write(line);
					writer.newLine();
				}
			} catch (IOException e) {
				logger.error("Error descargando HTML", e);
			} finally {
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					logger.error("Error cerrando buffers", e);
				}
			}
		}
		return totalData;

	}

	public <T extends SkeletonEntity> void exportToCSVByList(List<T> exportList, Path csvPath) {
		Writer csvWriter = null;
		StatefulBeanToCsv<T> sbc;
		csvPath.toFile().getParentFile().mkdirs();
		try {
			csvWriter = new FileWriter(csvPath.toString());

			StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder<T>(csvWriter);

			sbc = builder.withSeparator(CSVWriter.DEFAULT_SEPARATOR).withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
					.withApplyQuotesToAll(Boolean.TRUE).withThrowExceptions(Boolean.TRUE).build();

			sbc.write(exportList);
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			logger.error("Excepción exportando CSV", e);
		} finally {

			if (csvWriter != null) {
				try {
					csvWriter.close();
					logger.info("File writer cerrado!");
				} catch (IOException e) {
					logger.error("Excepción cerrando file writer", e);
				}
			}
		}

	}

}
