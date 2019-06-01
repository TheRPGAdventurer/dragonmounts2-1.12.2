package com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breeds;

import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breath.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.helper.EnumDragonLifeStage;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DragonBreedMoonlight extends DragonBreed {

	DragonBreedMoonlight() {
		super("moonlight", 0x2c427c);
		
		setHabitatBlock(Blocks.DAYLIGHT_DETECTOR_INVERTED);
		setHabitatBlock(Blocks.BLUE_GLAZED_TERRACOTTA);
	}

	@Override
	public void onEnable(EntityTameableDragon dragon) {}

	@Override
	public void onDisable(EntityTameableDragon dragon) {}

	@Override
	public void onDeath(EntityTameableDragon dragon) {}
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {
		if(dragon.posY > dragon.world.getHeight() + 15 && !dragon.world.isDaytime()) doParticles(dragon);
	}
	
    @SideOnly(Side.CLIENT)
    private void doParticles(EntityTameableDragon dragon) {
        if (!dragon.isEgg() && !dragon.isHatchling()) {
	        float s = dragon.getScale() * 1.2f;
	        double x = dragon.posX + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
	        double y = dragon.posY + (rand.nextDouble() - 0.5) * dragon.height * s;
	        double z = dragon.posZ + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
		        
	        dragon.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x, y, z, 0, 0, 0);
        }
    }

	@Override
	public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getBreathAffectedAreaIce().continueBreathing(world, origin, endOfLook, power, dragon);
		dragon.getBreathHelper().getBreathAffectedAreaIce().updateTick(world);
	}

	@Override
	public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
		dragon.getBreathHelper().getEmitter().spawnBreathParticlesforIceDragon(world, power, tickCounter);
	}

	public SoundEffectNames[] getBreathWeaponSoundEffects(EnumDragonLifeStage stage) {
		final SoundEffectNames soundEffectNames[]={SoundEffectNames.ADULT_BREATHE_ICE_START, SoundEffectNames.ADULT_BREATHE_ICE_LOOP, SoundEffectNames.ADULT_BREATHE_ICE_STOP};

		return soundEffectNames;

	}
}
