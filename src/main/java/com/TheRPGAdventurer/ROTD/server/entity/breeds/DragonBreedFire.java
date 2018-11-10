/*
 ** 2013 October 24
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonBreedFire extends DragonBreed {

    public DragonBreedFire() {
        super("fire",0x960b0f);
        
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);
        
        addHabitatBlock(Blocks.FIRE);
        addHabitatBlock(Blocks.LIT_FURNACE);
        addHabitatBlock(Blocks.LAVA);
        addHabitatBlock(Blocks.FLOWING_LAVA);
        
        addHabitatBiome(Biomes.DESERT);
        addHabitatBiome(Biomes.DESERT_HILLS);
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {
        dragon.getBrain().setAvoidsWater(true);
    }

    @Override
    public void onDisable(EntityTameableDragon dragon) {
        dragon.getBrain().setAvoidsWater(false);
   }

	@Override
	public void onDeath(EntityTameableDragon dragon) {		
		
	}
	
	@Override
	public ResourceLocation getLootTable(EntityTameableDragon dragon) {		
		return dragon.isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_FIRE : DragonMountsLootTables.ENTITIES_DRAGON_FIRE2;
	}
	
    @Override
    public Item getShearDropitem(EntityTameableDragon dragon) {    	
    	return dragon.isMale() ? ModItems.FireDragonScales : ModItems.FireDragonScales2;
    }
	
}
