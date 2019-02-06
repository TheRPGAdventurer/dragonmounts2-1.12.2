package com.TheRPGAdventurer.ROTD.client.initialization;

import net.minecraft.util.text.TextFormatting;

public enum EnumItemBreedTypes {
	
	AETHER(TextFormatting.AQUA), 
	WATER(TextFormatting.BLUE), 
	ICE(TextFormatting.DARK_BLUE), 
	FIRE(TextFormatting.DARK_RED), 
	FIRE2(TextFormatting.GOLD), 
	FOREST(TextFormatting.DARK_GREEN),
	SKELETON(TextFormatting.WHITE),
	WITHER(TextFormatting.WHITE),
	NETHER(TextFormatting.GOLD),
	END(TextFormatting.LIGHT_PURPLE),
	ENCHANT(TextFormatting.LIGHT_PURPLE),
	SUNLIGHT(TextFormatting.YELLOW),
	SUNLIGHT2(TextFormatting.LIGHT_PURPLE),
	MOONLIGHT(TextFormatting.DARK_BLUE),
	STORM(TextFormatting.BLUE),
	TERRA(TextFormatting.YELLOW),
	TERRA2(TextFormatting.YELLOW),
	ZOMBIE(TextFormatting.WHITE);
	
	public TextFormatting color;
	
	private EnumItemBreedTypes(TextFormatting color) {
		this.color = color;
	}
}
