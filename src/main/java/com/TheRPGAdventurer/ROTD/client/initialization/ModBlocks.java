package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonSkull;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static Block pileofsticks = new BlockDragonNest("pileofsticks", Material.WOOD);
	public static Block pileofstickssnow = new BlockDragonNest("pileofstickssnow", Material.WOOD);
	
	public static final Block aetherSkull = new BlockDragonSkull(EnumDragonBreed.AETHER);
	public static final Block enchantSkull = new BlockDragonSkull(EnumDragonBreed.ENCHANT);
	public static final Block endSkull = new BlockDragonSkull(EnumDragonBreed.END);
	public static final Block fireSkull = new BlockDragonSkull(EnumDragonBreed.FIRE);
	public static final Block forestSkull = new BlockDragonSkull(EnumDragonBreed.FOREST);
	public static final Block iceSkull = new BlockDragonSkull(EnumDragonBreed.ICE);
	public static final Block netherSkull = new BlockDragonSkull(EnumDragonBreed.NETHER);
	public static final Block skeletonSkull = new BlockDragonSkull(EnumDragonBreed.SKELETON);
	public static final Block stormSkull = new BlockDragonSkull(EnumDragonBreed.STORM);
	public static final Block sunlightSkull = new BlockDragonSkull(EnumDragonBreed.SUNLIGHT);
	public static final Block terraSkull = new BlockDragonSkull(EnumDragonBreed.TERRA);
	public static final Block waterSkull = new BlockDragonSkull(EnumDragonBreed.SYLPHID);
	public static final Block witherSkull = new BlockDragonSkull(EnumDragonBreed.WITHER);
	public static final Block zombieSkull = new BlockDragonSkull(EnumDragonBreed.ZOMBIE);
	
	public static final Block[] BLOCKS = {
		pileofstickssnow, pileofsticks,
		aetherSkull, enchantSkull, endSkull, fireSkull, forestSkull, iceSkull, netherSkull, skeletonSkull,
		stormSkull, sunlightSkull, terraSkull, waterSkull, witherSkull, zombieSkull
				

	};
	
}