package com.TheRPGAdventurer.ROTD.inits;

import java.util.ArrayList;
import java.util.List;

import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonShulker;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;

import net.minecraft.block.Block;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block NESTBLOCK = new BlockDragonNest("pileofsticks");
	public static final Block DRAGONSHULKER = new BlockDragonShulker("block_dragon_shulker");
	
	public static final Block AETHER_DRAGONEGG = new BlockDragonBreedEgg("aether_dragonEgg", EnumDragonBreed.AETHER);
	public static final Block ENCHANT_DRAGONEGG = new BlockDragonBreedEgg("enchant_dragonEgg", EnumDragonBreed.ENCHANT);
	public static final Block ENDER_DRAGONEGG = new BlockDragonBreedEgg("ender_dragonEgg", EnumDragonBreed.END);
	public static final Block FIRE_DRAGONEGG = new BlockDragonBreedEgg("fire_dragonEgg", EnumDragonBreed.FIRE);
	public static final Block FOREST_DRAGONEGG = new BlockDragonBreedEgg("forest_dragonEgg", EnumDragonBreed.FOREST);
	public static final Block ICE_DRAGONEGG = new BlockDragonBreedEgg("ice_dragonEgg", EnumDragonBreed.ICE);
	public static final Block MOONLIGHT_DRAGONEGG = new BlockDragonBreedEgg("moonlight_dragonEgg", EnumDragonBreed.MOONLIGHT);
	public static final Block NETHER_DRAGONEGG = new BlockDragonBreedEgg("nether_dragonEgg", EnumDragonBreed.NETHER);
	public static final Block SKELETON_DRAGONEGG = new BlockDragonBreedEgg("skeleton_dragonEgg", EnumDragonBreed.SKELETON);
	public static final Block STORM_DRAGONEGG = new BlockDragonBreedEgg("storm_dragonEgg", EnumDragonBreed.STORM);
	public static final Block SUNLIGHT_DRAGONEGG = new BlockDragonBreedEgg("sunlight_dragonEgg", EnumDragonBreed.SUNLIGHT);
	public static final Block SYLPHID_DRAGONEGG = new BlockDragonBreedEgg("sylphid_dragonEgg", EnumDragonBreed.SYLPHID);
	public static final Block TERRA_DRAGONEGG = new BlockDragonBreedEgg("terra_dragonEgg", EnumDragonBreed.TERRA);
	public static final Block WITHER_DRAGONEGG = new BlockDragonBreedEgg("wither_dragonEgg", EnumDragonBreed.WITHER);
	public static final Block ZOMBIE_DRAGONEGG = new BlockDragonBreedEgg("zombie_dragonEgg", EnumDragonBreed.ZOMBIE);
	
}