package com.TheRPGAdventurer.ROTD.client.inventory;

import com.TheRPGAdventurer.ROTD.server.blocks.BlockDragonBreedEgg;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {

	public CreativeTab(String label) {
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(BlockDragonBreedEgg.DRAGON_BREED_EGG);
	}

	@Override
	public boolean hasSearchBar() {
		return false;
	}
}
