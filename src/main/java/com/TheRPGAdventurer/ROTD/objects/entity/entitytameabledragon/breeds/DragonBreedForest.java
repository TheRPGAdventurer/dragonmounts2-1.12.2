package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.BiomeDictionary;

;


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
    public void onEnable(EntityTameableDragon dragon) {
    }

    @Override
    public void onDisable(EntityTameableDragon dragon) {
    }

    @Override
    public void onDeath(EntityTameableDragon dragon) {
    }

    @Override
    public void onLivingUpdate(EntityTameableDragon dragon) {
        boolean isForest = BiomeDictionary.hasType(dragon.world.getBiome(dragon.getPosition()), BiomeDictionary.Type.JUNGLE)
                || BiomeDictionary.hasType(dragon.world.getBiome(dragon.getPosition()), BiomeDictionary.Type.CONIFEROUS)
                || BiomeDictionary.hasType(dragon.world.getBiome(dragon.getPosition()), BiomeDictionary.Type.FOREST);
        boolean isSavanna = BiomeDictionary.hasType(dragon.world.getBiome(dragon.getPosition()), BiomeDictionary.Type.SAVANNA)
                || BiomeDictionary.hasType(dragon.world.getBiome(dragon.getPosition()), BiomeDictionary.Type.DRY);
        if (!dragon.isAlbino()) {
            if (isForest) {
                dragon.setaltTextures(false); // use green texture
            } else if (isSavanna) {
                dragon.setaltTextures(true); // use brown texture
            }
        }
    }
}
	
