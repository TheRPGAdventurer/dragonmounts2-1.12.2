package com.TheRPGAdventurer.ROTD.entity.breeds;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class DragonBreedForest extends DragonBreed {

    DragonBreedForest() {
        super("forest", 0x298317);
        
        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);
        
        setHabitatBlock(Blocks.YELLOW_FLOWER);
        setHabitatBlock(Blocks.RED_FLOWER);
        setHabitatBlock(Blocks.MOSSY_COBBLESTONE);
        setHabitatBlock(Blocks.VINE);
        setHabitatBlock(Blocks.SAPLING);
        setHabitatBlock(Blocks.LEAVES);
        setHabitatBlock(Blocks.LEAVES2);
        
        setHabitatBiome(Biomes.JUNGLE);
        setHabitatBiome(Biomes.JUNGLE_HILLS);
        
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
	        double x = dragon.posX + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
	        double y = dragon.posY + (rand.nextDouble() - 0.5) * dragon.height * s;
	        double z = dragon.posZ + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
		        
	        dragon.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x, y, z, 0, 0, 0);
        }
    }
}
	
