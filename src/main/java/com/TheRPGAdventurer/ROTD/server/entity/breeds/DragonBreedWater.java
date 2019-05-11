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
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.EnumDragonLifeStage;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonBreedWater extends DragonBreed {

    public DragonBreedWater() {
        super("sylphid", 0x4f69a8);
        
        setImmunity(DamageSource.DROWN);
        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);
        
        setHabitatBlock(Blocks.WATER);
        setHabitatBlock(Blocks.FLOWING_WATER);
        
        setHabitatBiome(Biomes.OCEAN);
        setHabitatBiome(Biomes.RIVER);
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
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getbreathAffectedAreaHydro().continueBreathing(world, origin, endOfLook, power, dragon);
		dragon.getBreathHelper().getbreathAffectedAreaHydro().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforWaterDragon(world, power, tickCounter);
    }
	
	@Override
	public SoundEffectNames[] getBreathWeaponSoundEffects(EnumDragonLifeStage stage) {
    	final SoundEffectNames hatchling[] = {SoundEffectNames.ADULT_BREATHE_ICE_START,
                SoundEffectNames.ADULT_BREATHE_ICE_LOOP,
                SoundEffectNames.ADULT_BREATHE_ICE_STOP};

        final SoundEffectNames juvenile[] = {SoundEffectNames.ADULT_BREATHE_ICE_START,
                SoundEffectNames.ADULT_BREATHE_ICE_LOOP,
                SoundEffectNames.ADULT_BREATHE_ICE_STOP};

        final SoundEffectNames adult[] = {SoundEffectNames.ADULT_BREATHE_ICE_START,
            SoundEffectNames.ADULT_BREATHE_ICE_LOOP,
            SoundEffectNames.ADULT_BREATHE_ICE_STOP};
    	
    	switch(stage) {
		case ADULT:
			soundEffectNames = adult;
			break;
		case EGG:
			break;
		case HATCHLING:
			soundEffectNames = hatchling;
			break;
		case JUVENILE:
			soundEffectNames = juvenile;       
			break;
		default:
			break;    	
    	}
    	
		return soundEffectNames;
    }
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {
		PotionEffect watereffect = new PotionEffect(MobEffects.WATER_BREATHING, 20*10, 0, false,false);
    	if (!dragon.isPotionActive(watereffect.getPotion()) && dragon.isInWater()) { // If the Potion isn't currently active,
    		dragon.addPotionEffect(watereffect); // Apply a copy of the PotionEffect to the player
		}  
    	
    //	if(dragon.isPotionActive(new PotionEffect(MobEffects.STRENGTH).getPotion())) {
    //		dragon.setBreedType(EnumDragonBreed.STORM);
     //	}
	}
	
	@Override
	public EnumParticleTypes getSneezeParticle() {
		return null;
	}
	

}
