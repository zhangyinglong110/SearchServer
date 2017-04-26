package com.edu.util;

import org.apache.log4j.Logger;

public class Log4j {

	public static void info(Class<?> clazz, String info) {
		Logger logger = Logger.getLogger(clazz);
		logger.info(info);
	}

	public static void error(Class<?> clazz, String error) {
		Logger logger = Logger.getLogger(clazz);
		logger.error(error);
	}

	public static void debug(Class<?> clazz, String debug) {
		Logger logger = Logger.getLogger(clazz);
		logger.error(debug);
	}

}
