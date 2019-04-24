package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.util.IHasModel;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemDragonWand extends Item implements IHasModel
{
	
	public ItemDragonWand(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name));
		this.setMaxStackSize(1);
	//	this.setCreativeTab(DragonMounts.TAB);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void RegisterModels()
	{
		DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
