package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonSkull;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static Block pileofsticks = new BlockDragonNest("pileofsticks");
	public static Block pileofstickssnow = new BlockDragonNest("pileofstickssnow");
	
	public static final Block aetherSkull = new BlockDragonSkull(EnumDragonBreed.AETHER, ModItems.SkullAether);
	public static final Block enchantSkull = new BlockDragonSkull(EnumDragonBreed.ENCHANT, ModItems.SkullEnchant);
	public static final Block endSkull = new BlockDragonSkull(EnumDragonBreed.END, ModItems.SkullEnd);
	public static final Block fireSkull = new BlockDragonSkull(EnumDragonBreed.FIRE, ModItems.SkullFire);
	public static final Block forestSkull = new BlockDragonSkull(EnumDragonBreed.FOREST, ModItems.SkullForest);
	public static final Block iceSkull = new BlockDragonSkull(EnumDragonBreed.ICE, ModItems.SkullIce);
	public static final Block netherSkull = new BlockDragonSkull(EnumDragonBreed.NETHER, ModItems.SkullNether);
	public static final Block skeletonSkull = new BlockDragonSkull(EnumDragonBreed.SKELETON, ModItems.SkullSkeleton);
	public static final Block stormSkull = new BlockDragonSkull(EnumDragonBreed.STORM, ModItems.SkullStorm);
	public static final Block sunlightSkull = new BlockDragonSkull(EnumDragonBreed.SUNLIGHT, ModItems.SkullSunlight);
	public static final Block terraSkull = new BlockDragonSkull(EnumDragonBreed.TERRA, ModItems.SkullTerra);
	public static final Block waterSkull = new BlockDragonSkull(EnumDragonBreed.SYLPHID, ModItems.SkullWater);
	public static final Block witherSkull = new BlockDragonSkull(EnumDragonBreed.WITHER, ModItems.SkullWither);
	public static final Block zombieSkull = new BlockDragonSkull(EnumDragonBreed.ZOMBIE, ModItems.SkullZombie);
	
	public static final Block[] BLOCKS = {
		pileofsticks, pileofstickssnow, 
		aetherSkull, enchantSkull, endSkull, fireSkull, forestSkull, iceSkull, netherSkull, skeletonSkull,
		stormSkull, sunlightSkull, terraSkull, waterSkull, witherSkull, zombieSkull			

	};
	
}