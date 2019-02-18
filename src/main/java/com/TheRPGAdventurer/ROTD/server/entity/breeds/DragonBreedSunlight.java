package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class DragonBreedSunlight extends DragonBreed {

	DragonBreedSunlight() {
		super("sunlight", 0Xffde00);
		
		setHabitatBlock(Blocks.DAYLIGHT_DETECTOR);
		setHabitatBlock(Blocks.GLOWSTONE);
		setHabitatBlock(Blocks.YELLOW_GLAZED_TERRACOTTA);
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
		return dragon.isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_SUNLIGHT : DragonMountsLootTables.ENTITIES_DRAGON_SUNLIGHT2;
	}

	@Override
	public Item getShearDropitem(EntityTameableDragon dragon) {		
		return dragon.isMale() ? ModItems.SunlightDragonScales : ModItems.SunlightDragonScales2;
	}
	
}
