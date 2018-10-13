package com.TheRPGAdventurer.ROTD.server.entity.interact;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class DragonInteractOpenGUI extends DragonInteract {

	public DragonInteractOpenGUI(EntityTameableDragon dragon) {
		super(dragon);
	}
	
	@Override
	public boolean interact(EntityPlayer player, ItemStack item) {
		if (player.isSneaking() && dragon.isTamed() && (dragon.isJuvenile() || dragon.isAdult())) {
			dragon.openGUI(player);
			return true;
		}
		return false;
	}

}
