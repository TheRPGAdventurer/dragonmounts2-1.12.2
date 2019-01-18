package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonSkull;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static Block pileofsticks;
	public static Block pileofstickssnow;
	
	public static Block aetherSkull;
	public static Block enchantSkull;
	public static Block endSkull;
	public static Block fireSkull;
	public static Block forestSkull;
	public static Block iceSkull;
	public static Block netherSkull;
	public static Block skeletonSkull;
	public static Block stormSkull;
	public static Block sunlightSkull;
	public static Block terraSkull;
	public static Block waterSkull;
	public static Block witherSkull;
	public static Block zombieSkull;
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