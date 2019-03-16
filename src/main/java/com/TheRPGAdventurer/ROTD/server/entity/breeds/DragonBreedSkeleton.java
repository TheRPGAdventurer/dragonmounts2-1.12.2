package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class DragonBreedSkeleton extends DragonBreed {

    DragonBreedSkeleton() {
        super("skeleton", 0xffffff);
        
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);
    }
    
    @Override
    public boolean isHabitatEnvironment(EntityTameableDragon dragon) {
        if (dragon.posY > dragon.world.getHeight() * 0.25) {
            // woah dude, too high!
            return false;
        }

        int bx = MathHelper.floor(dragon.posX);
        int by = MathHelper.floor(dragon.posY);
        int bz = MathHelper.floor(dragon.posZ);

        BlockPos blockPos = new BlockPos(bx, by, bz);
        if (dragon.world.canBlockSeeSky(blockPos)) {
            // sun is shining!
            return false;
        }
        
        if (dragon.world.getLightBrightness(blockPos) > 4) {
            // too bright!
            return false;
        }

        return true;
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {}

    @Override
    public void onDisable(EntityTameableDragon dragon) {}

    @Override
    public void onDeath(EntityTameableDragon dragon) {}
    
    @Override
    public SoundEvent getLivingSound() {
      return SoundEvents.ENTITY_SKELETON_AMBIENT;      
    }
    
    @Override
    public SoundEvent getRoarSoundEvent() {
    	return ModSounds.ENTITY_SKELETON_DRAGON_GROWL;
    }
    
//	@Override
//	public boolean canChangeBreed() {return false;}
	
	@Override
	public boolean canUseBreathWeapon() {
		return false;
	}
	
	@Override
  public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
		return;
   }
  
	@Override
   public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
		return;
   }
	
	@Override
	public ResourceLocation getLootTable(EntityTameableDragon dragon) {
		return DragonMountsLootTables.ENTITIES_DRAGON_SKELETON;
	}
    
//	@Override
//	public boolean isInfertile() {
//		return true;
//	}
	
	@Override
	public EnumParticleTypes getSneezeParticle() {
		return null;
	}
	
	@Override
	public double getHealth() {
		return 145;
	}

}
	
