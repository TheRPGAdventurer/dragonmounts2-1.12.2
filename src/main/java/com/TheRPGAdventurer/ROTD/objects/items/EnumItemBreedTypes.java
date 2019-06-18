package com.TheRPGAdventurer.ROTD.objects.items;

import net.minecraft.util.text.TextFormatting;

public enum EnumItemBreedTypes {
	
	AETHER(TextFormatting.DARK_AQUA), 
	WATER(TextFormatting.BLUE),
	SYLPHID(TextFormatting.BLUE), //Register Sylphid for other purposes
	ICE(TextFormatting.AQUA), 
	FIRE(TextFormatting.RED), 
	FIRE2(TextFormatting.GOLD), 
	FOREST(TextFormatting.DARK_GREEN),
	SKELETON(TextFormatting.WHITE),
	WITHER(TextFormatting.DARK_GRAY),
	NETHER(TextFormatting.DARK_RED),
	NETHER2(TextFormatting.DARK_RED),
	END(TextFormatting.DARK_PURPLE),
	ENCHANT(TextFormatting.LIGHT_PURPLE),
	SUNLIGHT(TextFormatting.YELLOW),
	SUNLIGHT2(TextFormatting.LIGHT_PURPLE),
	MOONLIGHT(TextFormatting.BLUE),
	STORM(TextFormatting.BLUE),
	STORM2(TextFormatting.BLUE),
	TERRA(TextFormatting.GOLD),
	TERRA2(TextFormatting.GOLD),
	GHOST(TextFormatting.YELLOW),
	ZOMBIE(TextFormatting.WHITE);
	
	public TextFormatting color;
	
	private EnumItemBreedTypes(TextFormatting color) {
		this.color = color;
	}
}
