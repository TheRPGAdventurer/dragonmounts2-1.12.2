package com.TheRPGAdventurer.ROTD.server.blocks;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiDragonShulker;
import com.TheRPGAdventurer.ROTD.server.tile.TileEntityDragonShulker;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDragonShulker extends BlockContainer {

    public BlockDragonShulker() {
        super(Material.ROCK, MapColor.AIR);
        this.setCreativeTab(DragonMounts.TAB);
        this.setUnlocalizedName("dragon_shulker_box");
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_shulker_box"));
        this.setResistance(100);
        this.setHardness(10);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
    }

    @SideOnly(Side.CLIENT)
    public void openDragonShulkerGui(EntityPlayer player) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiDragonShulker(player.inventory, new InventoryBasic("dragon_shulker", true, 1)));
    }

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else if (playerIn.isSpectator()) {
            return true;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityDragonShulker) {
                EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
                boolean flag;

                if (((TileEntityDragonShulker) tileentity).getAnimationStatus() == TileEntityDragonShulker.AnimationStatus.CLOSED) {
                    AxisAlignedBB axisalignedbb = FULL_BLOCK_AABB.expand((double) (0.5F * (float) enumfacing.getFrontOffsetX()), (double) (0.5F * (float) enumfacing.getFrontOffsetY()), (double) (0.5F * (float) enumfacing.getFrontOffsetZ())).contract((double) enumfacing.getFrontOffsetX(), (double) enumfacing.getFrontOffsetY(), (double) enumfacing.getFrontOffsetZ());
                    flag = !worldIn.collidesWithAnyBlock(axisalignedbb.offset(pos.offset(enumfacing)));
                } else {
                    flag = true;
                }

                if (flag) {
                    openDragonShulkerGui(playerIn);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing");


    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean causesSuffocation(IBlockState state) {
        return true;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return true;
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
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
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDragonShulker();
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this block
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityDragonShulker) {
            TileEntityDragonShulker tileentityshulkerbox = (TileEntityDragonShulker) worldIn.getTileEntity(pos);
            tileentityshulkerbox.setDestroyedByCreativePlayer(player.capabilities.isCreativeMode);
            tileentityshulkerbox.fillWithLoot(player);
        }
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityDragonShulker) {
                ((TileEntityDragonShulker) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        TileEntity tileentity = source.getTileEntity(pos);
        return tileentity instanceof TileEntityDragonShulker ? ((TileEntityDragonShulker) tileentity).getBoundingBox(state) : FULL_BLOCK_AABB;
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        ItemStack itemstack = super.getItem(worldIn, pos, state);
        TileEntityDragonShulker tileentityshulkerbox = (TileEntityDragonShulker) worldIn.getTileEntity(pos);
        NBTTagCompound nbttagcompound = tileentityshulkerbox.saveToNbt(new NBTTagCompound());

        if (!nbttagcompound.hasNoTags()) {
            itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
        }

        return itemstack;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        p_193383_2_ = this.getActualState(p_193383_2_, p_193383_1_, p_193383_3_);
        EnumFacing enumfacing = (EnumFacing) p_193383_2_.getValue(FACING);
        TileEntityDragonShulker.AnimationStatus animationStatus = ((TileEntityDragonShulker) p_193383_1_.getTileEntity(p_193383_3_)).getAnimationStatus();
        return animationStatus != TileEntityDragonShulker.AnimationStatus.CLOSED && (animationStatus != TileEntityDragonShulker.AnimationStatus.OPENED || enumfacing != p_193383_4_.getOpposite() && enumfacing != p_193383_4_) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
    }
}
