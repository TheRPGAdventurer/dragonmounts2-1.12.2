package com.TheRPGAdventurer.ROTD.client.inventory;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerDragonWand extends Container {
	EntityTameableDragon dragon;
	EntityPlayer player;
	
	public ContainerDragonWand(EntityTameableDragon dragon, EntityPlayer player) {
		this.dragon = dragon;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return false;
	}

}
