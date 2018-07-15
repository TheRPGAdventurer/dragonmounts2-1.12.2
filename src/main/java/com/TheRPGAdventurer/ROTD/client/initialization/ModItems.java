package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.client.items.ItemDragonScales;

import net.minecraft.item.Item;

public class ModItems {
	
	public static final Item ForestDragonScales;
	public static final Item FireDragonScales;
	public static final Item IceDragonScales;
	public static final Item WaterDragonScales;
	public static final Item AetherDragonScales;
	public static final Item NetherDragonScales;
	public static final Item EnderDragonScales;
	
	
	public static final Item[] ITEMS = {
		ForestDragonScales = new ItemDragonScales("forest_dragonscales", EnumItemBreedTypes.FOREST, 16),
		FireDragonScales = new ItemDragonScales("fire_dragonscales", EnumItemBreedTypes.FIRE, 16),
		IceDragonScales = new ItemDragonScales("ice_dragonscales", EnumItemBreedTypes.ICE, 16),
		WaterDragonScales = new ItemDragonScales("water_dragonscales", EnumItemBreedTypes.WATER, 16),
		AetherDragonScales = new ItemDragonScales("aether_dragonscales", EnumItemBreedTypes.AETHER, 16),
		NetherDragonScales = new ItemDragonScales("nether_dragonscales", EnumItemBreedTypes.NETHER, 16),
		EnderDragonScales = new ItemDragonScales("ender_dragonscales", EnumItemBreedTypes.END, 16),
	};
}
