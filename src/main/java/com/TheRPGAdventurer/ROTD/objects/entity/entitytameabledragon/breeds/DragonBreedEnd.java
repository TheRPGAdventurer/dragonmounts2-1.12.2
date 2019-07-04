package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds;

import com.TheRPGAdventurer.ROTD.inits.ModSounds;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathNode;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonBreedEnd extends DragonBreed {

    DragonBreedEnd() {
        super("ender", 0xab39be);
        
        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);
        
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {}

    @Override
    public void onDisable(EntityTameableDragon dragon) {}

    @Override
    public void onDeath(EntityTameableDragon dragon) {}
    
    @Override
    public SoundEvent getLivingSound(EntityTameableDragon dragon) {
       return ModSounds.ENTITY_DRAGON_BREATHE;
    }
    
    @Override
    public SoundEvent getRoarSoundEvent(EntityTameableDragon dragon) {
    return SoundEvents.ENTITY_ENDERDRAGON_GROWL;
    }
    
	@Override
	public boolean canChangeBreed() {
		return false;
	}
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {

	}

	
	@Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getBreathAffectedAreaEnd().continueBreathing(world, origin, endOfLook, power, dragon);
		dragon.getBreathHelper().getBreathAffectedAreaEnd().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
		dragon.getBreathHelper().getEmitter().spawnBreathParticlesforEnderDragon(world, power, tickCounter);
    }

    public EnumParticleTypes getSneezeParticle() {
        return EnumParticleTypes.PORTAL;
    }
    
}
	
