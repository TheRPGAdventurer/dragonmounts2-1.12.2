package com.TheRPGAdventurer.ROTD.entity.breeds;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breath.BreathNode;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonBreedMoonlight extends DragonBreed {

	DragonBreedMoonlight() {
		super("moonlight", 0x2c427c);
		
		setHabitatBlock(Blocks.DAYLIGHT_DETECTOR_INVERTED);
		setHabitatBlock(Blocks.BLUE_GLAZED_TERRACOTTA);
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
		dragon.getBreathHelper().getBreathAffectedAreaIce().continueBreathing(world, origin, endOfLook, power, dragon);
		dragon.getBreathHelper().getBreathAffectedAreaIce().updateTick(world);
	}

	@Override
	public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
		dragon.getBreathHelper().getEmitter().spawnBreathParticlesforIceDragon(world, power, tickCounter);
	}
}
