package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonSkull;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static Block pileofsticks = new BlockDragonNest("pileofsticks");
	public static Block pileofstickssnow = new BlockDragonNest("pileofstickssnow");
	
	public static final Block[] BLOCKS = {
		pileofsticks, pileofstickssnow, 

	};
	
}