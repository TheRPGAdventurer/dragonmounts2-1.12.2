package com.TheRPGAdventurer.ROTD.entity.breeds;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.entity.breath.sound.SoundController;
import com.TheRPGAdventurer.ROTD.entity.breath.sound.SoundEffectBreathWeapon;
import com.TheRPGAdventurer.ROTD.entity.breath.sound.SoundEffectBreathWeaponIce;
import com.TheRPGAdventurer.ROTD.inits.ModSounds;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonBreedZombie extends DragonBreed {

    DragonBreedZombie() {
        super("zombie", 0X5e5602);

        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);

        setHabitatBlock(Blocks.SOUL_SAND);
        setHabitatBlock(Blocks.NETHER_WART_BLOCK);
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
        dragon.getBreathHelper().getbreathAffectedAreaPoison().continueBreathing(world, origin, endOfLook, power, dragon);
        dragon.getBreathHelper().getbreathAffectedAreaPoison().updateTick(world);
    }

    @Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforPoisonDragon(world, power, tickCounter);
    }

    @Override
    public SoundEvent getLivingSound() {
        return ModSounds.ENTITY_DRAGON_BREATHE;
    }

    @Override
    public SoundEvent getRoarSoundEvent() {
        return ModSounds.ZOMBIE_DRAGON_GROWL;
    }

    @Override
    public SoundEvent getDeathSound() {
        return ModSounds.ENTITY_DRAGON_DEATH;
    }

//	@Override
//	public boolean isInfertile() {
//		return true;
//	}

    @Override
    public SoundEffectBreathWeapon getSoundEffectBreathWeapon(SoundController i_soundController,
                                                              SoundEffectBreathWeapon.WeaponSoundUpdateLink i_weaponSoundUpdateLink) {
        return new SoundEffectBreathWeaponIce(i_soundController, i_weaponSoundUpdateLink);
    }

    @Override
    public EnumParticleTypes getSneezeParticle() {
        return null;
    }

//	@Override
//	public boolean canChangeBreed() {
//		return false;
//	}
}
