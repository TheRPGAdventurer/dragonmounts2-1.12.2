package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import java.util.UUID;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonEssence;
import com.TheRPGAdventurer.ROTD.client.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.EnumDragonLifeStage;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonBreedAir extends DragonBreed {

	 public static final UUID MODIFIER_ID = UUID.fromString("60be8770-29f2-4bbe-bb8c-7a41143c9974");
	 public static final AttributeModifier MODIFIER = new AttributeModifier(MODIFIER_ID, "Air dragon speed bonus", 0.2, 2).setSaved(false);

	    public DragonBreedAir() {
	        super("aether", 0x0294bd);
	        
	     setImmunity(DamageSource.MAGIC);
	     setImmunity(DamageSource.HOT_FLOOR);
	     setImmunity(DamageSource.LIGHTNING_BOLT);
	     setImmunity(DamageSource.WITHER);
	        
	     setHabitatBiome(Biomes.EXTREME_HILLS);
	     setHabitatBlock(Blocks.LAPIS_BLOCK);
	     setHabitatBlock(Blocks.LAPIS_ORE);
	}

	@Override
    public boolean isHabitatEnvironment(EntityTameableDragon dragon) {
	   // true if located pretty high (> 2/3 of the maximum world height)
	   return dragon.posY > dragon.world.getHeight() * 0.66;
	}

	@Override
	public void onEnable(EntityTameableDragon dragon) {
	   dragon.getAttributeMap().getAttributeInstance(EntityTameableDragon.MOVEMENT_SPEED_AIR).applyModifier(MODIFIER);
	}

    @Override
	public void onDisable(EntityTameableDragon dragon) {
	   dragon.getAttributeMap().getAttributeInstance(EntityTameableDragon.MOVEMENT_SPEED_AIR).removeModifier(MODIFIER);
	}

    @Override
    public void onDeath(EntityTameableDragon dragon) {}
    
    @Override
    public void onLivingUpdate(EntityTameableDragon dragon) {
    	super.onLivingUpdate(dragon);
    }
    
    @Override
    public ResourceLocation getLootTable(EntityTameableDragon dragon) {
    	return DragonMountsLootTables.ENTITIES_DRAGON_AETHER;
    }
    
    @Override
    public Item getShearDropitem(EntityTameableDragon dragon) {    	
    	return ModItems.AetherDragonScales;
    }
	
	@Override
    public boolean useColdSound() {
    	return true;
    }
	
	@Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getBreathAffectedAreaIce().continueBreathing(world, origin, endOfLook, power);
		dragon.getBreathHelper().getBreathAffectedAreaIce().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
		dragon.getBreathHelper().getEmitter().spawnBreathParticlesforIceDragon(world, power, tickCounter);
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
    
}
	
