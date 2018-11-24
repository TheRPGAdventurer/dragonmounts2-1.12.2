package com.TheRPGAdventurer.ROTD.client.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemDragonWhistle extends Item {
	
	ItemDragonWhistle.Commands comamnds;
	
	public ItemDragonWhistle() {
		this.setUnlocalizedName("dragon_whistle");
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_whistle"));
		this.setMaxStackSize(1);
	}
	
	public void setCommands(ItemDragonWhistle.Commands commands) {
		this.comamnds = commands;
	}
	
	public ItemDragonWhistle.Commands getCommands() {
		return comamnds;
	}

	public enum Commands {
	    COME(),
		CIRCLE();		
		
	}
}
