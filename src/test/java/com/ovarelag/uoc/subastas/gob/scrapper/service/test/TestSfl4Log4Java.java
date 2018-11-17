package com.ovarelag.uoc.subastas.gob.scrapper.service.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSfl4Log4Java {

	static Logger logger = LoggerFactory.getLogger(TestSfl4Log4Java.class);


	@Test
	public void testLof4J() {
		logger.info("Example log from {}", TestSfl4Log4Java.class.getSimpleName());
	}

}
