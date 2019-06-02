package com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breeds;


import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.inits.ModSounds;

import net.minecraft.init.Biomes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class DragonBreedNether extends DragonBreed {

    DragonBreedNether() {
        super("nether", 0xe5b81b);
        setHabitatBiome(Biomes.HELL);
        
        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);          
        
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {
        dragon.getBrain().setAvoidsWater(true);
    }

    @Override
    public void onDisable(EntityTameableDragon dragon) {
        dragon.getBrain().setAvoidsWater(false);
   }

	@Override
	public void onDeath(EntityTameableDragon dragon) {
		
	}
    
    public SoundEvent getLivingSound() {
        if (rand.nextInt(3) == 0) {
            return ModSounds.ENTITY_NETHER_DRAGON_GROWL;
        } else {
            return ModSounds.ENTITY_NETHER_DRAGON_GROWL;
        }
    }
    
//	@Override
//	public boolean canChangeBreed() {
//		return false;
//	}
	
	@Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getBreathAffectedAreaNether().continueBreathing(world, origin, endOfLook, power, dragon);
		dragon.getBreathHelper().getBreathAffectedAreaNether().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforNetherDragon(world, power, tickCounter);
    }
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {
		doParticles(dragon);
	}
	
    @SideOnly(Side.CLIENT)
    private void doParticles(EntityTameableDragon dragon) {
        if (!dragon.isEgg() && !dragon.isHatchling()) {
	        float s = dragon.getScale() * 1.2f;
	        for (double x1 = 0; x1 < s + 2; ++x1) {
		        double x = dragon.posX + (rand.nextDouble() - 0.5) * (dragon.width - 1.5) * s;
		        double y = dragon.posY + (rand.nextDouble() - 0.5) * dragon.height;
		        double z = dragon.posZ + (rand.nextDouble() - 0.5) * (dragon.width - 1.5) * s;
		        
		        dragon.world.spawnParticle(EnumParticleTypes.DRIP_LAVA, x, y, z, 0, 0, 0);
		        dragon.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y + 5, z, 0, 0.04, 0);
	        }
        }
    }
}
