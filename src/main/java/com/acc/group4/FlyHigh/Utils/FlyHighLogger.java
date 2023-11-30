package com.acc.group4.FlyHigh.Utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FlyHighLogger {
        private static Logger logger = Logger.getLogger("FlyHigh");
        private static FileHandler fh;
        public static Logger initLogger() throws SecurityException, IOException {
        	fh = new FileHandler("C:/Users/shubh/OneDrive/Desktop/FlyHigh.log",true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			return logger;
        }
}
