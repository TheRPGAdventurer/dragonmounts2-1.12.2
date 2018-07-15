package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonNest;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static Block pileofsticks;
	public static Block pileofstickssnow;
	
	public static final Block[] BLOCKS = {
//		pileofstickssnow = new BlockDragonNest("pileofstickssnow", Material.WOOD),
		pileofsticks = new BlockDragonNest("pileofsticks", Material.WOOD),
		
	};
	
}