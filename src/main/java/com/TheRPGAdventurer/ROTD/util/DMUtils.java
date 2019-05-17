package com.TheRPGAdventurer.ROTD.util;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

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
	 * @param start
	 * @param end
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

	/**
	 * taken from stackoverflow
	 * @param rnd
	 * @param start
	 * @param end
	 * @param exclude
	 * @return
	 */
	public static int getRandomWithExclusionstatic(Random rnd, int start, int end, int... exclude) {
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