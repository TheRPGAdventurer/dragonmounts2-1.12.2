package com.TheRPGAdventurer.ROTD.client.inventory;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {

	public CreativeTab(String label) {
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Blocks.DRAGON_EGG);
	}

	@Override
	public boolean hasSearchBar() {
		return false;
	}
}
