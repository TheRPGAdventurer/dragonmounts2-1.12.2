package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import java.util.UUID;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class DragonBreedAir extends DragonBreed {

	 public static final UUID MODIFIER_ID = UUID.fromString("60be8770-29f2-4bbe-bb8c-7a41143c9974");
	    public static final AttributeModifier MODIFIER = new AttributeModifier(MODIFIER_ID, "Air dragon speed bonus", 0.2, 2).setSaved(false);

	    public DragonBreedAir() {
	        super("aether", 0x1dc4f3);
	        
	     addImmunity(DamageSource.MAGIC);
	     addImmunity(DamageSource.HOT_FLOOR);
	     addImmunity(DamageSource.LIGHTNING_BOLT);
	     addImmunity(DamageSource.WITHER);
	        
	     addHabitatBiome(Biomes.EXTREME_HILLS);
	     addHabitatBlock(Blocks.LAPIS_BLOCK);
	     addHabitatBlock(Blocks.LAPIS_ORE);
	}

	@Override
    public boolean isHabitatEnvironment(EntityTameableDragon dragon) {
	   // true if located pretty high (> 2/3 of the maximum world height)
	   return dragon.posY > dragon.world.getHeight() * 0.66;
	}

	@Override
	public void onEnable(EntityTameableDragon dragon) {
	   dragon.getAttributeMap().getAttributeInstance(EntityTameableDragon.MOVEMENT_SPEED_AIR).applyModifier(MODIFIER);
	}

    @Override
	public void onDisable(EntityTameableDragon dragon) {
	   dragon.getAttributeMap().getAttributeInstance(EntityTameableDragon.MOVEMENT_SPEED_AIR).removeModifier(MODIFIER);
	}

    @Override
    public void onDeath(EntityTameableDragon dragon) {}
    
    @Override
    public ResourceLocation getLootTable() {
    	return super.getLootTable();
    }
    
}
	
