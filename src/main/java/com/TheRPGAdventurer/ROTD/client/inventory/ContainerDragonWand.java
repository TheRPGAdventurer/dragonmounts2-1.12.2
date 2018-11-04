package com.TheRPGAdventurer.ROTD.client.inventory;

import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerDragonWand extends Container {
	private final IInventory dragonStats;
	private final EntityTameableDragon dragon;
	private final EntityPlayer player;
	public static final int chestStartIndex = 3;


	public ContainerDragonWand(final EntityTameableDragon dragon, EntityPlayer player) {
		this.dragonStats = dragon.dragonStats;
		this.dragon = dragon;
		this.player = player;
		int b0 = 3;
		int i = (b0 - 4) * 18;
		final int inventoryColumn = 9;
		dragonStats.openInventory(player);
		int j = -21;		
		
		int j1;
		int k;
		// location of the player's inventory in the dragon inventory 
		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 150 + j * 18 + i));
			}
		}

		for (j = 0; j < 9; ++j) {
			this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 208 + i));
		}
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.dragonStats.isUsableByPlayer(playerIn) && this.dragon.isEntityAlive() && this.dragon.getDistanceToEntity(playerIn) < 8.0F;
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < this.dragonStats.getSizeInventory()) {
				if (!this.mergeItemStack(itemstack1, this.dragonStats.getSizeInventory(), this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(2).isItemValid(itemstack1)) {
				if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack()) {
				if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
					return ItemStack.EMPTY;
				}
			}  

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		this.dragonStats.closeInventory(playerIn);
	}
}