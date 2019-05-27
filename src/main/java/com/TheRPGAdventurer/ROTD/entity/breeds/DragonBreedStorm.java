<<<<<<< HEAD:src/main/java/com/TheRPGAdventurer/ROTD/entity/breeds/DragonBreedStorm.java
package com.TheRPGAdventurer.ROTD.entity.breeds;
=======
package com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breeds;

import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breath.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.helper.EnumDragonLifeStage;
>>>>>>> 487f066b... changes:src/main/java/com/TheRPGAdventurer/ROTD/entity/entitytameabledragon/breeds/DragonBreedStorm.java

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.entity.breath.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.entity.helper.EnumDragonLifeStage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class DragonBreedStorm extends DragonBreed {

	DragonBreedStorm() {
		super("storm", 0xf5f1e9);

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
	
//	@Override
//	public boolean isInfertile() {
//		return true;
//	}

	public SoundEffectNames[] getBreathWeaponSoundEffects(EnumDragonLifeStage stage) {
		final SoundEffectNames soundEffectNames[]={SoundEffectNames.ADULT_BREATHE_ICE_START, SoundEffectNames.ADULT_BREATHE_ICE_LOOP, SoundEffectNames.ADULT_BREATHE_ICE_STOP};

		return soundEffectNames;

	}
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {
		EntityLivingBase target = dragon.getAttackTarget();
		PotionEffect watereffect = new PotionEffect(MobEffects.WATER_BREATHING, 20*10);
    	if (!dragon.isPotionActive(watereffect.getPotion()) && dragon.isInWater()) { // If the Potion isn't currently active,
    		dragon.addPotionEffect(watereffect); // Apply a copy of the PotionEffect to the player
		}   
    	
    	Random random = new Random();
    	boolean shouldelectrecute = target != null && target.isEntityAlive();
    	
    	if(target instanceof EntityPlayer) {
    		EntityPlayer playertarget = (EntityPlayer) target;
    		if(playertarget.capabilities.isCreativeMode) {
    			shouldelectrecute = false;
    		}
    	}
    	
    	if(shouldelectrecute) {
    		  if(random.nextInt(70) == 1 && dragon.world.isRaining()) {
    		     dragon.world.addWeatherEffect(new EntityLightningBolt(target.world, target.posX, target.posY, target.posZ, false)); 		   
    		}
    	}
    	
		World world = dragon.world;
		
		if (world instanceof WorldServer && !dragon.isDead && !dragon.isEgg()) {
			((WorldServer) world).spawnParticle(EnumParticleTypes.DRIP_WATER, dragon.posX,
					dragon.posY + dragon.getEyeHeight(), dragon.posZ, 1, 0.5D, 0.25D, 0.5D, 0.0D);
		}
    	
    //	if(!dragon.isPotionActive(new PotionEffect(MobEffects.STRENGTH).getPotion())) {
    //		dragon.setBreedType(EnumDragonBreed.SYLPHID);
    //	}
	}
	
	@Override
	public EnumParticleTypes getSneezeParticle() {
		return null;
	}
	
}
