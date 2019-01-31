package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

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
	
	@Override
	public Item getShearDropitem(EntityTameableDragon dragon) {
		return dragon.isMale() ? ModItems.TerraDragonScales : ModItems.TerraDragonScales2;
	}
	
	@Override
	public ResourceLocation getLootTable(EntityTameableDragon dragon) {
		return dragon.isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_TERRA : DragonMountsLootTables.ENTITIES_DRAGON_TERRA2;
	}
	

}
