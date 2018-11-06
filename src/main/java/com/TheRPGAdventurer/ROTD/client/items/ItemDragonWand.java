package com.TheRPGAdventurer.ROTD.client.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemDragonWand extends Item {
	
	public ItemDragonWand(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name));
		this.setMaxStackSize(1);
	//	this.setCreativeTab(DragonMounts.TAB);
	}
	

}
