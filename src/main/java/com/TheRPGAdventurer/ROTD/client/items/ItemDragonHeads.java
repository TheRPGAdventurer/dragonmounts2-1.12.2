package com.TheRPGAdventurer.ROTD.client.items;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.TheRPGAdventurer.ROTD.client.tile.TileEntityDragonHead;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.mojang.authlib.GameProfile;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemDragonHeads extends Item {

    public ItemDragonHeads(EnumDragonBreed breed) {
        this.setRegistryName(breed + "_dragon_head");
        this.setUnlocalizedName(breed + "_dragon_head");
        
    }
    
    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing == EnumFacing.DOWN)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            if (worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos))
            {
                facing = EnumFacing.UP;
                pos = pos.down();
            }
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            boolean flag = block.isReplaceable(worldIn, pos);

            if (!flag)
            {
                if (!worldIn.getBlockState(pos).getMaterial().isSolid() && !worldIn.isSideSolid(pos, facing, true))
                {
                    return EnumActionResult.FAIL;
                }

                pos = pos.offset(facing);
            }

            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && Blocks.SKULL.canPlaceBlockAt(worldIn, pos))
            {
                if (worldIn.isRemote) {
                    return EnumActionResult.SUCCESS;
                }
                else
                {
                    worldIn.setBlockState(pos, Blocks.SKULL.getDefaultState().withProperty(BlockSkull.FACING, facing), 11);
                    int i = 0;

                    if (facing == EnumFacing.UP)
                    { 
                        i = MathHelper.floor((double)(player.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
                    }

                    TileEntity tileentity = worldIn.getTileEntity(pos);

                    if (tileentity instanceof TileEntityDragonHead)
                    {
                    	TileEntityDragonHead tileentityskull = (TileEntityDragonHead)tileentity;

                    //    if (itemstack.getMetadata() == 3)
                   //     {
                   //         GameProfile gameprofile = null;

                         //   if (itemstack.hasTagCompound())
                          //  {
                          //      NBTTagCompound nbttagcompound = itemstack.getTagCompound();

                          //      if (nbttagcompound.hasKey("SkullOwner", 10))
                          //      {
                         //           gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
                          //      }
                         //       else if (nbttagcompound.hasKey("SkullOwner", 8) && !StringUtils.isBlank(nbttagcompound.getString("SkullOwner")))
                          //      {
                            //        gameprofile = new GameProfile((UUID)null, nbttagcompound.getString("SkullOwner"));
                          //      }
                          //  }

                         //   tileentityskull.setPlayerProfile(gameprofile);
                    //    }
                   //     else
                   //     {
                        //    tileentityskull.setType(itemstack.getMetadata());
                      //  }

                        tileentityskull.setSkullRotation(i);
                    //    Blocks.SKULL.checkWitherSpawn(worldIn, pos, tileentityskull);
                    }

                    if (player instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
                    }

                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
    }
}
