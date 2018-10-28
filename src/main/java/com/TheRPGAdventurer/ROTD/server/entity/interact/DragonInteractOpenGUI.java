package com.TheRPGAdventurer.ROTD.server.entity.interact;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

public class DragonInteractOpenGUI extends DragonInteract {

	public DragonInteractOpenGUI(EntityTameableDragon dragon) {
		super(dragon);
	}
	
	@Override
	public boolean interact(EntityPlayer player, ItemStack item) {
		if (player.isSneaking() && dragon.isTamed() && !dragon.isHatchling()) {
			dragon.openGUI(player);
			return true;
		} else if(dragon.isHatchling() && dragon.isTamed() && player.isSneaking()) {
			player.sendStatusMessage(new TextComponentTranslation("entity.dragon.tooYoung", new Object[0]), true);
		}
		return false;
	}

}
