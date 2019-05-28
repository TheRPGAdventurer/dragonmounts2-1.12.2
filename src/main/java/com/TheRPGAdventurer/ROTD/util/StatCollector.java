package com.TheRPGAdventurer.ROTD.util;

import net.minecraft.client.resources.I18n;

/*
 * Legacy translations helper
 */
public class StatCollector {

	public static String translateToLocal(String s) {
		return I18n.format(s);
	}
}