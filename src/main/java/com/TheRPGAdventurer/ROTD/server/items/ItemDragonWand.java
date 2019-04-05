package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemDragonWand extends Item {
	
	public ItemDragonWand(String name) {
		this.setTranslationKey(name);
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name));
		this.setMaxStackSize(1);
	//	this.setCreativeTab(DragonMounts.TAB);
	}
	

}
