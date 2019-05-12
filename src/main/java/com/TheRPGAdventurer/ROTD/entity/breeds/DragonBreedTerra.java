package com.TheRPGAdventurer.ROTD.entity.breeds;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;

public class DragonBreedTerra extends DragonBreed {

	DragonBreedTerra() {
		super("terra", 0Xa56c21);
		
		setHabitatBiome(Biomes.MESA);
		setHabitatBiome(Biomes.MESA_ROCK);
		setHabitatBiome(Biomes.MESA_CLEAR_ROCK);
		setHabitatBiome(Biomes.MUTATED_MESA_CLEAR_ROCK);
		setHabitatBiome(Biomes.MUTATED_MESA_ROCK);
		setHabitatBlock(Blocks.HARDENED_CLAY);

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

}
