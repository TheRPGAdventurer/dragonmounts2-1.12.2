package com.TheRPGAdventurer.ROTD.util;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	
	private static Logger logger;
	
	public static Logger getLogger() {
		if(logger == null) {
			logger = LogManager.getFormatterLogger(DragonMounts.MODID);
		}
		return logger;
	}
	
}
