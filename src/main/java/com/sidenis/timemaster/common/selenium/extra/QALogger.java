package com.sidenis.timemaster.common.selenium.extra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QALogger {
    public static Logger qaLogger = LoggerFactory.getLogger(QALogger.class);

    public static void log(String message) {
        qaLogger.info(message);
    }
}
