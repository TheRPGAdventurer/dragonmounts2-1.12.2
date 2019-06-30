package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;

public class DragonBreedSunlight extends DragonBreed {

	DragonBreedSunlight() {
		super("sunlight", 0Xffde00);
		
		setHabitatBlock(Blocks.DAYLIGHT_DETECTOR);
		setHabitatBlock(Blocks.GLOWSTONE);
		setHabitatBlock(Blocks.YELLOW_GLAZED_TERRACOTTA);
	}

	@Override
	public void onEnable(EntityTameableDragon dragon) {}

	@Override
	public void onDisable(EntityTameableDragon dragon) {}

	@Override
	public void onDeath(EntityTameableDragon dragon) {}
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {
		if(dragon.posY > dragon.world.getHeight() + 8 && dragon.world.isDaytime()) doParticles(dragon);
	}
	
    private void doParticles(EntityTameableDragon dragon) {
        if (!dragon.isEgg() && !dragon.isBaby()) {
	        float s = dragon.getScale() * 1.2f;
	        for (double x1 = 0; x1 < s + 2; ++x1) {
		        double x = dragon.posX + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
		        double y = dragon.posY + (rand.nextDouble() - 0.5) * dragon.height * s;
		        double z = dragon.posZ + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
		        
		        dragon.world.spawnParticle(EnumParticleTypes.CRIT, x, y, z, 0, 0, 0);
	        }
        }
    }
}
