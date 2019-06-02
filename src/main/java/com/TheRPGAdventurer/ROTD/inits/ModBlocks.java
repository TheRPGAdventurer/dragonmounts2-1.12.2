package com.TheRPGAdventurer.ROTD.inits;

import com.TheRPGAdventurer.ROTD.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.blocks.BlockDragonShulker;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breeds.EnumDragonBreed;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block NESTBLOCK = new BlockDragonNest("pileofsticks");
	public static final Block DRAGONSHULKER = new BlockDragonShulker("block_dragon_shulker");
	
	public static final Block AETHER_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.AETHER);
	public static final Block ENCHANT_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.ENCHANT);
	public static final Block END_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.END);
	public static final Block FIRE_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.FIRE);
	public static final Block FOREST_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.FOREST);
	public static final Block ICE_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.ICE);
	public static final Block MOONLIGHT_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.MOONLIGHT);
	public static final Block NETHER_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.NETHER);
	public static final Block SKELETON_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.SKELETON);
	public static final Block STORM_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.STORM);
	public static final Block SUNLIGHT_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.SUNLIGHT);
	public static final Block SYLPHID_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.SYLPHID);
	public static final Block TERRA_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.TERRA);
	public static final Block WITHER_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.WITHER);
	public static final Block ZOMBIE_DRAGONEGG = new BlockDragonBreedEgg("dragonEgg", EnumDragonBreed.ZOMBIE);
	
}