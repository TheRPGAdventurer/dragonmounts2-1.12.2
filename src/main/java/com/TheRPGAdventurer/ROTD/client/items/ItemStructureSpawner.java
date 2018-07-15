package com.TheRPGAdventurer.ROTD.client.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.client.initialization.ModBlocks;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemStructureSpawner extends Item {
	
	public ItemStructureSpawner(String unlocalizedName, String registryName) {
	    this.setUnlocalizedName(unlocalizedName);
	    this.setRegistryName(registryName);
	    this.setCreativeTab(DragonMounts.TAB);
	    this.setMaxStackSize(1);
	}
	
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			int x = pos.getX();
		    int y = pos.getY();
		    int z = pos.getZ();
			//upper
		    worldIn.setBlockState(new BlockPos(x+2,y+1,z+0), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+3,y+1,z+0), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+4,y+1,z+0), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+0,y+1,z+2), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+1,y+1,z+1), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+1,y+1,z+5), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+5,y+1,z+5), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+2,y+1,z+6), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+3,y+1,z+6), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+4,y+1,z+6), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+5,y+1,z+1), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+0,y+1,z+2), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+0,y+1,z+3), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+0,y+1,z+4), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+6,y+1,z+2), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+6,y+1,z+3), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+6,y+1,z+4), ModBlocks.pileofsticks.getStateFromMeta(0));
		    //bottom
		    worldIn.setBlockState(new BlockPos(x+1,y+0,z+2), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+1,y+0,z+4), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+2,y+0,z+1), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+2,y+0,z+3), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+2,y+0,z+4), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+3,y+0,z+2), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+3,y+0,z+3), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+3,y+0,z+5), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+4,y+0,z+1), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+4,y+0,z+2), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+4,y+0,z+3), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+4,y+0,z+5), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+5,y+0,z+3), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+5,y+0,z+4), ModBlocks.pileofsticks.getStateFromMeta(0));
		    worldIn.setBlockState(new BlockPos(x+1,y+0,z+3), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
		    worldIn.setBlockState(new BlockPos(x+2,y+0,z+5), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
		    worldIn.setBlockState(new BlockPos(x+2,y+0,z+2), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
		    worldIn.setBlockState(new BlockPos(x+3,y+0,z+1), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
		    worldIn.setBlockState(new BlockPos(x+3,y+0,z+4), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
		    worldIn.setBlockState(new BlockPos(x+4,y+0,z+4), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
		    worldIn.setBlockState(new BlockPos(x+5,y+0,z+2), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT));
		    worldIn.setBlockState(new BlockPos(x+3,y+1,z+3), BlockDragonBreedEgg.DRAGON_BREED_EGG.getStateFromMeta(worldIn.rand.nextInt(5)));
		    return EnumActionResult.SUCCESS;
		}
	    
	}
