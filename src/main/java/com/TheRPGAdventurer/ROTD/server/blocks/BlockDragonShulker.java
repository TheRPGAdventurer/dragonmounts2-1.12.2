package com.TheRPGAdventurer.ROTD.server.blocks;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.server.blocks.tileentities.TileEntityDragonShulker;
import com.TheRPGAdventurer.ROTD.server.initialization.ModBlocks;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Physical Block of the Dragon Core
 * @author WolfShotz
 */

public class BlockDragonShulker extends BlockContainer
{

	public BlockDragonShulker(String name)
	{
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(DragonMounts.mainTab);
		
		ModBlocks.BLOCKS.add(this);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			playerIn.openGui(DragonMounts.instance, GuiHandler.GUI_DRAGON_SHULKER, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityDragonShulker tileentity = (TileEntityDragonShulker) worldIn.getTileEntity(pos);
		// NOT a normal shulker box. so drop items on ground if destroyed.
		InventoryHelper.dropInventoryItems(worldIn, pos, tileentity);
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (stack.hasDisplayName())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			
			if (tileentity instanceof TileEntityDragonShulker)
			{
				((TileEntityDragonShulker) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDragonShulker();
	}
	
	
	//Create That Particle Surrounding the Box. Similar to Ender Chest
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        for (int i = 0; i < 3; ++i)
        {
            int j = rand.nextInt(2) * 2 - 1;
            int k = rand.nextInt(2) * 2 - 1;
            //Coords
            double x = (double)pos.getX() + 0.5D + 0.25D * (double)j;
            double y = (double)((float)pos.getY() + rand.nextFloat());
            double z = (double)pos.getZ() + 0.5D + 0.25D * (double)k;
            //Speed
            double s1 = (double)(rand.nextFloat() * (float)j);
            double s2 = ((double)rand.nextFloat() - 0.5D) * 0.125D;
            double s3 = (double)(rand.nextFloat() * (float)k);
            worldIn.spawnParticle(EnumParticleTypes.PORTAL, x, y, z, s1, s2, s3);
        }
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
}
