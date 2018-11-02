package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonBreedZombie extends DragonBreed {

	DragonBreedZombie() {
		super("zombie", 0Xb6d035);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEnable(EntityTameableDragon dragon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisable(EntityTameableDragon dragon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeath(EntityTameableDragon dragon) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {     
        dragon.getBreathHelper().getbreathAffectedAreaWither().continueBreathing(world, origin, endOfLook, power);
        dragon.getBreathHelper().getbreathAffectedAreaWither().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticles(world, power, tickCounter);
    }
	
	@Override
	public ResourceLocation getLootTable() {
		return DragonMountsLootTables.ENTITIES_DRAGON_SKELETON;
	}
	
	@Override
	public boolean isInfertile() {
		return true;
	}

}
