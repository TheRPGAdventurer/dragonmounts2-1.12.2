package com.TheRPGAdventurer.ROTD.client.initialization;

import net.minecraft.util.text.TextFormatting;

public enum EnumItemBreedTypes {
	
	AETHER(TextFormatting.AQUA), 
	WATER(TextFormatting.BLUE), 
	ICE(TextFormatting.DARK_BLUE), 
	FIRE(TextFormatting.DARK_RED), 
	FOREST(TextFormatting.DARK_GREEN),
	SKELETON(TextFormatting.WHITE),
	WITHER(TextFormatting.WHITE),
	NETHER(TextFormatting.GOLD),
	END(TextFormatting.LIGHT_PURPLE);
	
	public TextFormatting color;
	
	private EnumItemBreedTypes(TextFormatting color) {
		this.color = color;
	}
}
