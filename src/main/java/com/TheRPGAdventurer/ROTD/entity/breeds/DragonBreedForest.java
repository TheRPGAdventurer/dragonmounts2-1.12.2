<<<<<<< HEAD:src/main/java/com/TheRPGAdventurer/ROTD/entity/breeds/DragonBreedForest.java
package com.TheRPGAdventurer.ROTD.entity.breeds;
=======
package com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breeds;

import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;
>>>>>>> 487f066b... changes:src/main/java/com/TheRPGAdventurer/ROTD/entity/entitytameabledragon/breeds/DragonBreedForest.java

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;


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
    
}
	
