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

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonBreedWater extends DragonBreed {

    public DragonBreedWater() {
        super("sylphid", 0x4f69a8);
        
        addImmunity(DamageSource.DROWN);
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);
        
        addHabitatBlock(Blocks.WATER);
        addHabitatBlock(Blocks.FLOWING_WATER);
        
        addHabitatBiome(Biomes.OCEAN);
        addHabitatBiome(Biomes.RIVER);
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
    public boolean useColdSound() {
    	return true;
    }
	
	@Override
	public boolean canBreathFire() {
		return false;
	}

}
