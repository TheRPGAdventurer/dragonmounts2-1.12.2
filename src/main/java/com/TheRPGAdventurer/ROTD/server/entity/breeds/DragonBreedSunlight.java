package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class DragonBreedSunlight extends DragonBreed {

	DragonBreedSunlight() {
		super("sunlight", 0xe29315);
		
		addHabitatBlock(Blocks.DAYLIGHT_DETECTOR);
		addHabitatBlock(Blocks.DAYLIGHT_DETECTOR_INVERTED);
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
	public ResourceLocation getLootTable() {
		return DragonMountsLootTables.ENTITIES_DRAGON_SUNLIGHT;
	}

}
