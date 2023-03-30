package it.units.sdm.project;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainLogger {
    private static final String NAME = "it.units.sdm.project";
    private final Logger logger;

    public MainLogger(@NotNull String loggerFilePath) throws IOException {
        FileHandler handler = new FileHandler(loggerFilePath);
        SimpleFormatter formatter = new SimpleFormatter();
        logger = Logger.getLogger(NAME);
        logger.setUseParentHandlers(false);
        handler.setFormatter(formatter);
        logger.addHandler(handler);
     }

     public void log(@NotNull Level level,@NotNull String message) {
        logger.log(level, message);
     }
}
