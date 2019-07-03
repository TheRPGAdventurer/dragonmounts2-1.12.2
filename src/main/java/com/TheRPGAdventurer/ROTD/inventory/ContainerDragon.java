package com.TheRPGAdventurer.ROTD.inventory;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;

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

public class ContainerDragon extends Container {
	private final IInventory dragonInv;
	private final EntityTameableDragon dragon;
	public static final int chestStartIndex = 3;

	public ContainerDragon(final EntityTameableDragon dragon, EntityPlayer player) {
		this.dragonInv = dragon.dragonInv;
		this.dragon = dragon;
		final int inventoryColumn = 9;
		dragonInv.openInventory(player);

		// location of the slot for the saddle in the dragon inventory
		this.addSlotToContainer(new Slot(dragonInv, 0, 8, 18) {

			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.SADDLE && !this.getHasStack();
			}

			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				return !dragon.isBaby();
			}

		});

		// location of the slot for chest in the dragon inventory
		this.addSlotToContainer(new Slot(dragonInv, 1, 8, 35) {

			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Item.getItemFromBlock(Blocks.CHEST) && !this.getHasStack();
			}

			@Override
			public void onSlotChanged() {
				ContainerDragon.this.dragon.refreshInventory();
			}

			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				return true;
			}
		});

		// location of the slot for armor in the dragon inventory
		this.addSlotToContainer(new Slot(dragonInv, 2, 8, 53) {

			public boolean isItemValid(ItemStack stack) {
				return dragon.getIntFromArmor(stack) != 0;
			}

		});
		
		// Build Banner Slots
		for (int b = 0; b <= 3; ++b) {
			this.addSlotToContainer(new Slot(dragonInv, 31 + b, b == 1 || b == 2 ? 135 : 153, b < 2 ? 36 : 18) {
				public boolean isItemValid(ItemStack stack) {
					return stack.getItem() == Items.BANNER && !this.getHasStack();
				}
				
				@SideOnly(Side.CLIENT)
				public boolean isEnabled() {
					return true;
				}
			});
		}
				
		// Build Chest Inventory Slots
		for (int k = 0; k < 3; ++k) {
			for (int l = 0; l < 9; ++l) {
				this.addSlotToContainer(new Slot(dragonInv, chestStartIndex + l + k * inventoryColumn, 8 + l * 18, 74 + k * 18) {

					@SideOnly(Side.CLIENT)
					public boolean isEnabled() {
						return ContainerDragon.this.dragon.isChested();
					}

				});
			}
		}
		
		/*
		 * Player Inventory Slots within Dragon GUI
		 */
		// Build Inventory Slots
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 149 + j * 18 - 18));
			}
		}
		// Build hotbar slots
		for (int j = 0; j < 9; ++j) {
			this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 189));
		}
		// offhand
		this.addSlotToContainer(new Slot(player.inventory, 40, -13, 189));
	}
		
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.dragonInv.isUsableByPlayer(playerIn) && this.dragon.isEntityAlive() && this.dragon.getDistance(playerIn) < 8.0F;
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < this.dragonInv.getSizeInventory()) {
				if (!this.mergeItemStack(itemstack1, this.dragonInv.getSizeInventory(), this.inventorySlots.size(), true)) {
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
			} else if (this.getSlot(0).isItemValid(itemstack1)) {
				if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.dragonInv.getSizeInventory() <= chestStartIndex || !this.mergeItemStack(itemstack1, chestStartIndex, this.dragonInv.getSizeInventory(), false)) {
				return ItemStack.EMPTY;
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
		this.dragonInv.closeInventory(playerIn);
	}
}