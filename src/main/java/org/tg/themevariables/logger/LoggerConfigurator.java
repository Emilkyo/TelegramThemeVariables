package org.tg.themevariables.logger;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;

public class LoggerConfigurator {

    public static void configureLogger(String logFilePath) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        context.reset();

        try {
            configurator.doConfigure(logFilePath);
        } catch (JoranException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }
}
