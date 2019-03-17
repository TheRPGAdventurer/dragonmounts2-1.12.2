package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemDragonAmulet extends Item {

    public ItemDragonAmulet(@Nullable EnumItemBreedTypes type, @Nullable EnumDragonBreed breed) {
        String name = type != null && breed != null ?type.toString().toLowerCase() + "_dragon_amulet" : "dragon_amulet";
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name));
        this.setUnlocalizedName(name);
        this.setMaxDamage(1);
        this.setCreativeTab(DragonMounts.TAB);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        stack.setTagCompound(new NBTTagCompound());
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }else if(stack.getTagCompound().getBoolean("Released")){
            stack.shrink(1);
            if(entity instanceof EntityPlayer){
                ((EntityPlayer) entity).inventory.setInventorySlotContents(itemSlot, new ItemStack(ModItems.AmuletEmpty));
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = new ItemStack(this);
        if(stack != null) {
            EntityTameableDragon dragon = new EntityTameableDragon(worldIn);
            dragon.setPosition(pos.getX(),pos.getY(),pos.getZ());
            if (stack.getTagCompound() != null) {
                dragon.readEntityFromNBT(stack.getTagCompound());
            }
            stack.getTagCompound().setBoolean("Released", true);
            if (!worldIn.isRemote) {
                worldIn.spawnEntity(dragon);
            }
        }

        return EnumActionResult.PASS;
    }
}
