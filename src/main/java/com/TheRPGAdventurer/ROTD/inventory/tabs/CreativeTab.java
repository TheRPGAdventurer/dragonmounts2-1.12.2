package com.TheRPGAdventurer.ROTD.inventory.tabs;

import com.TheRPGAdventurer.ROTD.DragonMounts;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs
{
	public CreativeTab(String label)
	{
		super("maintab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack createIcon()
	{
		return new ItemStack(Blocks.DRAGON_EGG);
	}
	@Override
	public boolean hasSearchBar() {
		return false;
	}
}
