package com.TheRPGAdventurer.ROTD.util.compat;

import com.TheRPGAdventurer.ROTD.inits.ModBlocks;

import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

/**
 * General Ore dictionary registries for mod compatibility <p>
 * 
 * @author WolfShotz
 */
public class OreDictionaryCompat {
	
	public static void registerOres() {
		OreDictionary.registerOre("dragonEgg", ModBlocks.AETHER_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.ENCHANT_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.ENDER_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.FIRE_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.FOREST_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.ICE_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.MOONLIGHT_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.NETHER_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.SKELETON_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.STORM_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.SUNLIGHT_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.SYLPHID_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.TERRA_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.WITHER_DRAGONEGG);
		OreDictionary.registerOre("dragonEgg", ModBlocks.ZOMBIE_DRAGONEGG);
		
		OreDictionary.registerOre("listAllfishraw", Items.FISH); // Register fish for oredict because forge doesnt do it on its own.. >.>
	}
}
