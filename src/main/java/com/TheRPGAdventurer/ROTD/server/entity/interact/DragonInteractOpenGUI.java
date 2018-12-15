package com.TheRPGAdventurer.ROTD.server.entity.interact;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

public class DragonInteractOpenGUI extends DragonInteract {

	public DragonInteractOpenGUI(EntityTameableDragon dragon) {
		super(dragon);
	}
	
	@Override
	public boolean interact(EntityPlayer player, ItemStack item) {
		if (player.isSneaking() && dragon.getScale() >= 0.77 && dragon.isTamed()) {
			dragon.openGUI(player, GuiHandler.GUI_DRAGON);
			return true;
		} else if(dragon.isHatchling() && dragon.isTamed() && player.isSneaking() && !ItemUtils.hasEquipped(player, ModItems.dragon_wand)) {
			player.sendStatusMessage(new TextComponentTranslation("entity.dragon.tooYoung", new Object[0]), true);
		} else if (ItemUtils.hasEquipped(player, ModItems.dragon_wand)) {
			dragon.openGUI(player, GuiHandler.GUI_DRAGON_WAND);
			return true;
		}
		return false;
	}

}
