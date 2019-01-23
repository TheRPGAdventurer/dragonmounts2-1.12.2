package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonEssence;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class DragonBreedSunlight extends DragonBreed {

	DragonBreedSunlight() {
		super("sunlight", 0xbf8425);
		
		setHabitatBlock(Blocks.DAYLIGHT_DETECTOR);
		setHabitatBlock(Blocks.DAYLIGHT_DETECTOR_INVERTED);
		setHabitatBlock(Blocks.GLOWSTONE);
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
	
	@Override
	public ItemDragonEssence dragonEssence() {
    return ModItems.EssenceSunlight;
 }
}
