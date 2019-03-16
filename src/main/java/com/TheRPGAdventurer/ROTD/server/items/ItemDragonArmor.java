package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;

import net.minecraft.item.Item;

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