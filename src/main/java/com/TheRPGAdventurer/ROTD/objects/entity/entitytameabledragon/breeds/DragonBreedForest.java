package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.Biome;
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
        Biome biome = dragon.world.getBiome(dragon.getPosition());

        boolean isSavanna = BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA)
                || BiomeDictionary.hasType(biome, BiomeDictionary.Type.DRY)
                || BiomeDictionary.hasType(biome, BiomeDictionary.Type.MESA)
                || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY);

        boolean isTaiga = BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)
                || BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN);


        if (isSavanna) {
            dragon.setForestType(EntityTameableDragon.EnumForestType.DRY.getName());
        } else if (isTaiga) {
            dragon.setForestType(EntityTameableDragon.EnumForestType.TAIGA.getName());
        } else {
            dragon.setForestType(EntityTameableDragon.EnumForestType.FOREST.getName());
        }
    }
}
	
