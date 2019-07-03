package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.DragonLifeStage;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonBreedAir extends DragonBreed {
	
	// The amount of the aether dragons flight speed bonus (Added to the dragon base air speed)
//	private static final float AETHER_SPEED_BONUS = 0.68465f; FIXME DISABLED FOR HEAD ROTATE ISSUE!

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
    public void onEnable(EntityTameableDragon dragon) { //FIXME DISALBED FOR HEAD ROTATE ISSUE!
//    	dragon.getEntityAttribute(EntityTameableDragon.MOVEMENT_SPEED_AIR).setBaseValue(EntityTameableDragon.BASE_AIR_SPEED + AETHER_SPEED_BONUS);
    }

    @Override
    public void onDisable(EntityTameableDragon dragon) {
//    	dragon.getEntityAttribute(EntityTameableDragon.MOVEMENT_SPEED_AIR).setBaseValue(EntityTameableDragon.BASE_AIR_SPEED);
    }

    @Override
    public void onDeath(EntityTameableDragon dragon) {
    }

    @Override
    public void onLivingUpdate(EntityTameableDragon dragon) {
        super.onLivingUpdate(dragon);
        
        if(dragon.posY > dragon.world.getHeight() * 1.2 && dragon.world.isDaytime()) doParticles(dragon);
    }

    private void doParticles(EntityTameableDragon dragon) {
        if (!dragon.isEgg() && !dragon.isBaby()) {
	        float s = dragon.getScale() * 1.2f;
	        for (double x1 = 0; x1 < s; ++x1) {
		        double x = dragon.posX + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
		        double y = dragon.posY + (rand.nextDouble() - 0.5) * dragon.height * s;
		        double z = dragon.posZ + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
		        
		        if (rand.nextInt(5) == 0)
		        	dragon.world.spawnParticle(EnumParticleTypes.SPELL_MOB, x, y, z, 1, 1, 0, 0, 0, 0); //yellow
		        else
		        	dragon.world.spawnParticle(EnumParticleTypes.SPELL_MOB, x, y, z, 0, 1, 1, 0, 0, 0); //aqua
	        }
        }
    }
    
//    @Override
//    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
//        dragon.getBreathHelper().getbreathAffectedAreaAether().continueBreathing(world, origin, endOfLook, power, dragon);
//        dragon.getBreathHelper().getbreathAffectedAreaAether().updateTick(world);
//    }
//
//    @Override
//    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
//        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
//        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforAetherDragon(world, power, tickCounter);
//    }

//    public SoundEffectNames[] getBreathWeaponSoundEffects(EnumDragonLifeStage stage) {
//        final SoundEffectNames soundEffectNames[]={SoundEffectNames.ADULT_BREATHE_AIR_START, SoundEffectNames.ADULT_BREATHE_AIR_LOOP, SoundEffectNames.ADULT_BREATHE_AIR_STOP};
//
//        return soundEffectNames;
//
//    }

}
	
