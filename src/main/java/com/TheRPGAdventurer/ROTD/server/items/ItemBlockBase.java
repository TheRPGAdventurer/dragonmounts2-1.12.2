package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockBase extends ItemBlock {
	

	public ItemBlockBase(String name, Block block) {
		super(block);
		this.setRegistryName(DragonMounts.MODID, name);
		this.setTranslationKey(this.getRegistryName().toString());
		this.setCreativeTab(DragonMounts.TAB);
	}

}
