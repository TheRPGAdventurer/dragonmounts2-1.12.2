package com.TheRPGAdventurer.ROTD.server.initialization;

import java.util.ArrayList;
import java.util.List;

import com.TheRPGAdventurer.ROTD.server.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.server.blocks.BlockDragonShulker;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block pileofsticks = new BlockDragonNest("pileofsticks");
	public static final Block dragonshulker = new BlockDragonShulker("dragon_shulker");
//	public static final Block pileofsticks = new BlockBase("pileofstickssnow");
	
}