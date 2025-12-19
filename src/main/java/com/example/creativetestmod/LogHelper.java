package com.example.creativetestmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LogHelper {
    private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    private LogHelper() {
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }
}
