package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;


public class DragonBreedForest extends DragonBreed {

    DragonBreedForest() {
        super("forest", 0x298317);
        
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);
        
        addHabitatBlock(Blocks.YELLOW_FLOWER);
        addHabitatBlock(Blocks.RED_FLOWER);
        addHabitatBlock(Blocks.GRASS);
        addHabitatBlock(Blocks.TALLGRASS);
        addHabitatBlock(Blocks.MOSSY_COBBLESTONE);
        addHabitatBlock(Blocks.VINE);
        addHabitatBlock(Blocks.SAPLING);
        addHabitatBlock(Blocks.LEAVES);
        addHabitatBlock(Blocks.LEAVES2);
        
        addHabitatBiome(Biomes.JUNGLE);
        addHabitatBiome(Biomes.JUNGLE_HILLS);
        
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {}

    @Override
    public void onDisable(EntityTameableDragon dragon) {}

    @Override
    public void onDeath(EntityTameableDragon dragon) {}
    
    @Override
    public ResourceLocation getLootTable() {
    	return DragonMountsLootTables.ENTITIES_DRAGON_FOREST;
    }
    
}
	
