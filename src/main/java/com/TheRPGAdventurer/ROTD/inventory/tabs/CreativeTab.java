package com.TheRPGAdventurer.ROTD.inventory.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
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
	public ItemStack getTabIconItem()
	{
		return new ItemStack(Blocks.DRAGON_EGG);
	}

	@Override
	public boolean hasSearchBar() {
		return false;
	}
}
