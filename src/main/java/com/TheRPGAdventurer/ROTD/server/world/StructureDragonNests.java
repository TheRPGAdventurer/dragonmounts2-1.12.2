package com.TheRPGAdventurer.ROTD.server.world;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.client.initialization.ModBlocks;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureDragonNests {
	
	public static boolean generate(World world, BlockPos pos, Random rand) {
		int x = pos.getX();
	    int y = pos.getY();
	    int z = pos.getZ();
	   // upper
	    world.setBlockState(new BlockPos(x+1,y+1,z-3), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+1,z-3), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x-1,y+1,z-3), ModBlocks.pileofsticks.getDefaultState());
	    
	    world.setBlockState(new BlockPos(x+1,y+1,z+3), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+1,z+3), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x-1,y+1,z+3), ModBlocks.pileofsticks.getDefaultState());
	    
	    world.setBlockState(new BlockPos(x+3,y+1,z+1), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+3,y+1,z+0), ModBlocks.pileofsticks.getDefaultState());		    
	    world.setBlockState(new BlockPos(x+3,y+1,z-1), ModBlocks.pileofsticks.getDefaultState());
	    
	    world.setBlockState(new BlockPos(x-3,y+1,z+1), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x-3,y+1,z+0), ModBlocks.pileofsticks.getDefaultState());		    
	    world.setBlockState(new BlockPos(x-3,y+1,z-1), ModBlocks.pileofsticks.getDefaultState());
	    
	    world.setBlockState(new BlockPos(x-2,y+1,z+2), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+2,y+1,z-2), ModBlocks.pileofsticks.getDefaultState());		    
	    world.setBlockState(new BlockPos(x-2,y+1,z-2), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+2,y+1,z+2), ModBlocks.pileofsticks.getDefaultState());
	    
	    // upper airblocks
	    world.setBlockState(new BlockPos(x+1,y+1,z+1), Blocks.AIR.getDefaultState());	  
	    world.setBlockState(new BlockPos(x+1,y+1,z+2), Blocks.AIR.getDefaultState());	  	  
	    world.setBlockState(new BlockPos(x+2,y+1,z+1), Blocks.AIR.getDefaultState());
	    
	    world.setBlockState(new BlockPos(x+1,y+1,z-1), Blocks.AIR.getDefaultState());	  
	    world.setBlockState(new BlockPos(x+1,y+1,z-2), Blocks.AIR.getDefaultState());
	    world.setBlockState(new BlockPos(x+2,y+1,z-1), Blocks.AIR.getDefaultState());
	    
	    world.setBlockState(new BlockPos(x-1,y+1,z-1), Blocks.AIR.getDefaultState());	  
	    world.setBlockState(new BlockPos(x-1,y+1,z-2), Blocks.AIR.getDefaultState());	  	  
	    world.setBlockState(new BlockPos(x-2,y+1,z-1), Blocks.AIR.getDefaultState());
	    	  
	    world.setBlockState(new BlockPos(x-1,y+1,z+1), Blocks.AIR.getDefaultState());	  
	    world.setBlockState(new BlockPos(x-1,y+1,z+2), Blocks.AIR.getDefaultState());	  	  
	    world.setBlockState(new BlockPos(x-2,y+1,z+1), Blocks.AIR.getDefaultState());
	     
	    world.setBlockState(new BlockPos(x+0,y+1,z+1), Blocks.AIR.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+1,z+2), Blocks.AIR.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+1,z-1), Blocks.AIR.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+1,z-2), Blocks.AIR.getDefaultState());
	    
	    world.setBlockState(new BlockPos(x+1,y+1,z+0), Blocks.AIR.getDefaultState());
	    world.setBlockState(new BlockPos(x+2,y+1,z+0), Blocks.AIR.getDefaultState());
	    world.setBlockState(new BlockPos(x-1,y+1,z+0), Blocks.AIR.getDefaultState());
	    world.setBlockState(new BlockPos(x-2,y+1,z+0), Blocks.AIR.getDefaultState());
	    
	    //below
	    world.setBlockState(new BlockPos(x+0,y+0,z+0), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+0,z+1), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
	    world.setBlockState(new BlockPos(x+2,y+0,z+0), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+1,y+0,z+1), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
	    world.setBlockState(new BlockPos(x-1,y+0,z+0), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x-2,y+0,z+0), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
	    world.setBlockState(new BlockPos(x-2,y+0,z-1), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x-1,y+0,z-1), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
	    world.setBlockState(new BlockPos(x-1,y+0,z-2), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+0,z-1), ModBlocks.pileofsticks.getDefaultState()); 
	    world.setBlockState(new BlockPos(x+1,y+0,z-1), ModBlocks.pileofsticks.getDefaultState()); 
	    world.setBlockState(new BlockPos(x+2,y+0,z-1), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
	    world.setBlockState(new BlockPos(x+2,y+0,z+1), ModBlocks.pileofsticks.getDefaultState()); 
	    world.setBlockState(new BlockPos(x+1,y+0,z-2), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+0,z-2), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
	    world.setBlockState(new BlockPos(x+1,y+0,z+0), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+0,z+2), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+1,y+0,z+2), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x+0,y+0,x+0), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x-1,y+0,z+1), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x-2,y+0,z+1), ModBlocks.pileofsticks.getDefaultState());
	    world.setBlockState(new BlockPos(x-1,y+0,z+2), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
	    
	    world.setBlockState(new BlockPos(x+0,y+1,z+0), BlockDragonBreedEgg.DRAGON_BREED_EGG.getStateFromMeta(world.rand.nextInt(4)));
	    return true;
	}   
	
}
