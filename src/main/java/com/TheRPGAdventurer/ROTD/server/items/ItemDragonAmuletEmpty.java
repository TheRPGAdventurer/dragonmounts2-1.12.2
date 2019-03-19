package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemDragonAmuletEmpty extends Item {

    public ItemDragonAmuletEmpty() {
        String name = "dragon_amulet";
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name));
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(DragonMounts.TAB);
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.setTagCompound(new NBTTagCompound());
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int f, boolean f1) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
    }
}
