package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonSkull;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static final Block pileofsticks;
	public static final Block pileofstickssnow;
	
	public static final Block aetherSkull;
	public static final Block enchantSkull;
	public static final Block endSkull;
	public static final Block fireSkull;
	public static final Block forestSkull;
	public static final Block iceSkull;
	public static final Block netherSkull;
	public static final Block skeletonSkull;
	public static final Block stormSkull;
	public static final Block sunlightSkull;
	public static final Block terraSkull;
	public static final Block waterSkull;
	public static final Block witherSkull;
	public static final Block zombieSkull;
//	public static Block aetherSkull;
	
	public static final Block[] BLOCKS = {
		pileofstickssnow = new BlockDragonNest("pileofstickssnow", Material.WOOD),
		pileofsticks = new BlockDragonNest("pileofsticks", Material.WOOD),
				
		aetherSkull = new BlockDragonSkull(EnumDragonBreed.AETHER),
		enchantSkull = new BlockDragonSkull(EnumDragonBreed.ENCHANT),
		endSkull = new BlockDragonSkull(EnumDragonBreed.END),
		fireSkull = new BlockDragonSkull(EnumDragonBreed.FIRE),
		forestSkull = new BlockDragonSkull(EnumDragonBreed.FOREST),
		iceSkull = new BlockDragonSkull(EnumDragonBreed.ICE),
		netherSkull = new BlockDragonSkull(EnumDragonBreed.NETHER),
		skeletonSkull = new BlockDragonSkull(EnumDragonBreed.SKELETON),
		stormSkull = new BlockDragonSkull(EnumDragonBreed.STORM),
		sunlightSkull = new BlockDragonSkull(EnumDragonBreed.SUNLIGHT),
		terraSkull = new BlockDragonSkull(EnumDragonBreed.TERRA),
		waterSkull = new BlockDragonSkull(EnumDragonBreed.SYLPHID),
		witherSkull = new BlockDragonSkull(EnumDragonBreed.WITHER),
		zombieSkull = new BlockDragonSkull(EnumDragonBreed.ZOMBIE),

	};
	
}