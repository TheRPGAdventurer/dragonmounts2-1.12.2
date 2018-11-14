package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonBreedZombie extends DragonBreed {

	DragonBreedZombie() {
		super("zombie", 0X5e5602);
		addImmunity(DamageSource.MAGIC);
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
        dragon.getBreathHelper().getbreathAffectedAreaPoison().continueBreathing(world, origin, endOfLook, power);
        dragon.getBreathHelper().getbreathAffectedAreaPoison().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforPoison(world, power, tickCounter);
    }
	
	@Override
	public ResourceLocation getLootTable(EntityTameableDragon dragon) {
		return DragonMountsLootTables.ENTITIES_DRAGON_ZOMBIE;
	}
	
	@Override
	public SoundEvent getLivingSound() {
		return ModSounds.ZOMBIE_DRAGON_GROWL;
	}
	
	@Override
	public SoundEvent getDeathSound() {
		return ModSounds.ENTITY_DRAGON_DEATH;
	}
	
	@Override
	public boolean isInfertile() {
		return true;
	}
	
	@Override
	public EnumParticleTypes getSneezeParticle() {
		return null;
	}
	
	@Override
	public boolean canChangeBreed() {
		return false;
	}

}
