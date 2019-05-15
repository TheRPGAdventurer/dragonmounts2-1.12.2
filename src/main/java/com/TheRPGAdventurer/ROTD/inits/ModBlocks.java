package com.TheRPGAdventurer.ROTD.inits;

import com.TheRPGAdventurer.ROTD.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.blocks.BlockDragonShulker;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block NESTBLOCK = new BlockDragonNest("pileofsticks");
	public static final Block DRAGONSHULKER = new BlockDragonShulker("block_dragon_shulker");
//	public static final Block pileofsticks = new BlockBase("pileofstickssnow");
	
}