package com.TheRPGAdventurer.ROTD.client.blocks;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.blocks.tileentities.TileEntityDragonShulker;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.inits.ModBlocks;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Physical Block of the Dragon Core
 *
 * @author WolfShotz
 */

public class BlockDragonShulker extends Block {

    public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing");

    public BlockDragonShulker(String name) {
        super(Material.ROCK);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(DragonMounts.mainTab);
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        p_193383_2_ = this.getActualState(p_193383_2_, p_193383_1_, p_193383_3_);
        EnumFacing enumfacing = (EnumFacing) p_193383_2_.getValue(FACING);
        TileEntityDragonShulker.AnimationStatus tileentityshulkerbox$animationstatus = ((TileEntityDragonShulker) p_193383_1_.getTileEntity(p_193383_3_)).getAnimationStatus();
        return tileentityshulkerbox$animationstatus != TileEntityDragonShulker.AnimationStatus.CLOSED && (tileentityshulkerbox$animationstatus != TileEntityDragonShulker.AnimationStatus.OPENED || enumfacing != p_193383_4_.getOpposite() && enumfacing != p_193383_4_) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
    }

//    @Override
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//        if (!worldIn.isRemote) {
//            playerIn.openGui(DragonMounts.instance, GuiHandler.GUI_DRAGON_SHULKER, worldIn, pos.getX(), pos.getY(), pos.getZ());
//        }
//
//        return true;
//    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityDragonShulker tileentity = (TileEntityDragonShulker) worldIn.getTileEntity(pos);
        // NOT a normal shulker box. so drop items on ground if destroyed.
        InventoryHelper.dropInventoryItems(worldIn, pos, tileentity);
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityDragonShulker) {
                ((TileEntityDragonShulker) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public TileEntity createTileEntity(World worldIn, IBlockState state) {
        return new TileEntityDragonShulker();
    }

//    public boolean hasTileEntity(IBlockState state)
//    {
//        return true;
//    }

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote || playerIn.isSpectator()) {
            return true;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityDragonShulker) {
                EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
                boolean flag;

                if (((TileEntityDragonShulker) tileentity).getAnimationStatus() == TileEntityDragonShulker.AnimationStatus.CLOSED) {
                    AxisAlignedBB axisalignedbb = FULL_BLOCK_AABB.expand((double) (0.5F * (float) enumfacing.getFrontOffsetX()), (double) (0.5F * (float) enumfacing.getFrontOffsetY()), (double) (0.5F * (float) enumfacing.getFrontOffsetZ())).contract((double) enumfacing.getFrontOffsetX(), (double) enumfacing.getFrontOffsetY(), (double) enumfacing.getFrontOffsetZ());
                    flag = !worldIn.collidesWithAnyBlock(axisalignedbb.offset(pos.getX(), pos.getY() + 1, pos.getZ()));
                } else {
                    flag = true;
                }

                if (flag) {
                    playerIn.openGui(DragonMounts.instance, GuiHandler.GUI_DRAGON_SHULKER, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }

                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getIndex();
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    //Create That Particle Surrounding the Box. Similar to Ender Chest
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        for (int i = 0; i < 3; ++i) {
            int j = rand.nextInt(2) * 2 - 1;
            int k = rand.nextInt(2) * 2 - 1;
            //Coords
            double x = (double) pos.getX() + 0.5D + 0.25D * (double) j;
            double y = (double) ((float) pos.getY() + rand.nextFloat());
            double z = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
            //Speed
            double s1 = (double) (rand.nextFloat() * (float) j);
            double s2 = ((double) rand.nextFloat() - 0.5D) * 0.125D;
            double s3 = (double) (rand.nextFloat() * (float) k);
            worldIn.spawnParticle(EnumParticleTypes.PORTAL, x, y, z, s1, s2, s3);
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
