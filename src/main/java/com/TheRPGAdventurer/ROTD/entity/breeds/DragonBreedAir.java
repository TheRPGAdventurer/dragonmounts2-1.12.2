package com.TheRPGAdventurer.ROTD.entity.breeds;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.entity.breath.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.entity.helper.EnumDragonLifeStage;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class DragonBreedAir extends DragonBreed {

    public static final UUID MODIFIER_ID=UUID.fromString("60be8770-29f2-4bbe-bb8c-7a41143c9974");
    public static final AttributeModifier MODIFIER=new AttributeModifier(MODIFIER_ID, "Air dragon speed bonus", 0.2, 2).setSaved(false);

    public DragonBreedAir() {
        super("aether", 0x0294bd);

        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);

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
        //	   dragon.getAttributeMap().getAttributeInstance(EntityTameableDragon.MOVEMENT_SPEED_AIR).applyModifier(MODIFIER);
    }

    @Override
    public void onDisable(EntityTameableDragon dragon) {
        //	   dragon.getAttributeMap().getAttributeInstance(EntityTameableDragon.MOVEMENT_SPEED_AIR).removeModifier(MODIFIER);
    }

    @Override
    public void onDeath(EntityTameableDragon dragon) {
    }

    @Override
    public void onLivingUpdate(EntityTameableDragon dragon) {
        super.onLivingUpdate(dragon);
    }


    @Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getbreathAffectedAreaAether().continueBreathing(world, origin, endOfLook, power, dragon);
        dragon.getBreathHelper().getbreathAffectedAreaAether().updateTick(world);
    }

    @Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforAetherDragon(world, power, tickCounter);
    }

    public SoundEffectNames[] getBreathWeaponSoundEffects(EnumDragonLifeStage stage) {
        final SoundEffectNames soundEffectNames[]={SoundEffectNames.ADULT_BREATHE_AIR_START, SoundEffectNames.ADULT_BREATHE_AIR_LOOP, SoundEffectNames.ADULT_BREATHE_AIR_STOP};

        return soundEffectNames;

    }

}
	
