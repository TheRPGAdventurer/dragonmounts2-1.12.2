package com.TheRPGAdventurer.ROTD.inits;

import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonShulker;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block NESTBLOCK = new BlockDragonNest("pileofsticks");
	public static final Block DRAGONSHULKER = new BlockDragonShulker("block_dragon_shulker");
}