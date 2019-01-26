package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonEssence;
import com.TheRPGAdventurer.ROTD.client.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.EnumDragonLifeStage;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

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
    public boolean useColdSound() {
    	return true;
    }
	
	@Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getbreathAffectedAreaHydro().continueBreathing(world, origin, endOfLook, power);
		dragon.getBreathHelper().getbreathAffectedAreaHydro().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforWaterDragon(world, power, tickCounter);
    }
	
	@Override
	public boolean isInfertile() {
		return true;
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
			((WorldServer) world).spawnParticle(EnumParticleTypes.DRIP_WATER, (double) dragon.posX,
					(double) dragon.posY + dragon.getEyeHeight(), (double) dragon.posZ, 1, 0.5D, 0.25D, 0.5D, 0.0D);
		}
    	
    //	if(!dragon.isPotionActive(new PotionEffect(MobEffects.STRENGTH).getPotion())) {
    //		dragon.setBreedType(EnumDragonBreed.SYLPHID);
    //	}
	}
	
	@Override
	public ResourceLocation getLootTable(EntityTameableDragon dragon) {
		return dragon.isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_STORM : DragonMountsLootTables.ENTITIES_DRAGON_STORM2;
	}
	
	@Override
	public Item getShearDropitem(EntityTameableDragon dragon) {		
		return dragon.isMale() ? ModItems.StormDragonScales : ModItems.StormDragonScales2;
	}
	
	@Override
	public EnumParticleTypes getSneezeParticle() {
		return null;
	}
	
}
