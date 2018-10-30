package com.TheRPGAdventurer.ROTD.util;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.TheRPGAdventurer.ROTD.DragonMounts;

public class DMUtils {
	
	private static Logger logger;
	
	public static Logger getLogger() {
		if(logger == null) {
			logger = LogManager.getFormatterLogger(DragonMounts.MODID);
		}
		return logger;
	}
	
	/**
	 * taken from stackoverflow
	 * @param rnd
	 * @param min
	 * @param max
	 * @param exclude
	 * @return
	 */
	public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
	    int random = start + rnd.nextInt(end - start + 1 - exclude.length);
	    for (int ex : exclude) {
	        if (random < ex) {
	            break;
	        }
	        random++;
	    }
	    return random;
	}
	
}
