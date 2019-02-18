package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class DragonBreedMoonlight extends DragonBreed {

	DragonBreedMoonlight() {
		super("moonlight", 0x2c427c);
		
		setHabitatBlock(Blocks.DAYLIGHT_DETECTOR_INVERTED);
		setHabitatBlock(Blocks.BLUE_GLAZED_TERRACOTTA);
	}

	@Override
	public void onEnable(EntityTameableDragon dragon) {
		
	}

	@Override
	public void onDisable(EntityTameableDragon dragon) {
		
	}

	@Override
	public void onDeath(EntityTameableDragon dragon) {
		
	}

	@Override
	public ResourceLocation getLootTable(EntityTameableDragon dragon) {
		return DragonMountsLootTables.ENTITIES_DRAGON_MOONLIGHT;
	}

	@Override
	public Item getShearDropitem(EntityTameableDragon dragon) {		
		return ModItems.MoonlightDragonScales;
	}
	
}
