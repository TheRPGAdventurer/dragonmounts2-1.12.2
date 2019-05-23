package com.TheRPGAdventurer.ROTD.entity.breeds;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DragonBreedTerra extends DragonBreed {

	DragonBreedTerra() {
		super("terra", 0Xa56c21);
		
		setHabitatBiome(Biomes.MESA);
		setHabitatBiome(Biomes.MESA_ROCK);
		setHabitatBiome(Biomes.MESA_CLEAR_ROCK);
		setHabitatBiome(Biomes.MUTATED_MESA_CLEAR_ROCK);
		setHabitatBiome(Biomes.MUTATED_MESA_ROCK);
		setHabitatBlock(Blocks.HARDENED_CLAY);

	}

	@Override
	public void onEnable(EntityTameableDragon dragon) {}

	@Override
	public void onDisable(EntityTameableDragon dragon) {}

	@Override
	public void onDeath(EntityTameableDragon dragon) {}
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {
		doParticles(dragon);
	}
	
    @SideOnly(Side.CLIENT)
    private void doParticles(EntityTameableDragon dragon) {
        if (!dragon.isEgg() && !dragon.isHatchling()) {
	        float s = dragon.getScale() * 1.2f;
	        for (double x1 = 0; x1 < s; ++x1) {
		        double x = dragon.posX + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
		        double y = dragon.posY + (rand.nextDouble() - 0.5) * dragon.height * s;
		        double z = dragon.posZ + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
		        
		        dragon.world.spawnParticle(EnumParticleTypes.FALLING_DUST, x, y - 1, z, 0, 0, 0, (dragon.isMale() ? 3 : 5));
	        }
        }
    }

}
