package com.TheRPGAdventurer.ROTD.client.items;

import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDragonArmor extends Item {

	public String name;

	public ItemDragonArmor(String name) {
		this.name = name;
		this.setUnlocalizedName(name);
		this.setCreativeTab(DragonMounts.TAB);
		this.maxStackSize = 1;
		this.setRegistryName(DragonMounts.MODID, name);
		this.setCreativeTab(DragonMounts.TAB);

	}


}