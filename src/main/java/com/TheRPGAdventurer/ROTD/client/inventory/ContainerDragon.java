package com.TheRPGAdventurer.ROTD.client.inventory;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
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
	private final EntityPlayer player;
	public static final int chestStartIndex = 3;

	public ContainerDragon(final EntityTameableDragon dragon, EntityPlayer player) {
		this.dragonInv = dragon.dragonInv;
		this.dragon = dragon;
		this.player = player;
		int b0 = 3;
		int i = (b0 - 4) * 18;
		final int inventoryColumn = 9;
		dragonInv.openInventory(player);
		int j = -21;
		int k2;

		// location of the slot for the saddle in the dragon inventory
		this.addSlotToContainer(new Slot(dragonInv, 0, 8, 18) {
			
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.SADDLE && !this.getHasStack();
			}

			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				return true;
			}
			
		});
		
		// location of the slot for chest in the dragon inventory
		this.addSlotToContainer(new Slot(dragonInv, 1, 8, 36) {
			
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Item.getItemFromBlock(Blocks.CHEST) && !this.getHasStack();
			}

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
		
		// location of the slot for the banner1 in the dragon inventory
		this.addSlotToContainer(new Slot(dragonInv, 31, 153, 18) {
			
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.BANNER && !this.getHasStack();
			}

			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				return true;
			}
			
		});
				
		// location of the slot for the dragon banner2 in the dragon inventory
		this.addSlotToContainer(new Slot(dragonInv, 32, 153, 36) {
			
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.BANNER && !this.getHasStack();
			}

			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				return true;
			}
			
		});
		
		// location of the slot for the dragon banner3 in the dragon inventory
		this.addSlotToContainer(new Slot(dragonInv, 33, 135, 18) {
			
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.BANNER && !this.getHasStack();
			}

			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				return true;
			}
			
		});
		
		// location of the slot for the dragon banner4 in the dragon inventory
		this.addSlotToContainer(new Slot(dragonInv, 34, 135, 36) {
			
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.BANNER && !this.getHasStack();
			}

			@SideOnly(Side.CLIENT)
			public boolean isEnabled() {
				return true;
			}
			
		});
		
		// location of the dragon's inventory when chested in the dragon inventory 
		for (int k = 0; k < 3; ++k) {
			for (int l = 0; l < 9; ++l) {                                            
				this.addSlotToContainer(new Slot(dragonInv, chestStartIndex + l + k * inventoryColumn, 8 + l * 18, 75 + k * 18) {
					
					@SideOnly(Side.CLIENT)
					public boolean isEnabled() {
						return ContainerDragon.this.dragon.isChested();
					}
					
				});
			}
		}

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
//		GuiContainerCreative.ContainerCreative
		// player armor position
		// Build Armor + Offhand slots
		if (dragon.isChested()) {
			for (int chested = 0; chested < 2; ++chested) {
				this.addSlotToContainer(new Slot(player.inventory, 36 + chested, -19, 109 - chested * 27));
				this.addSlotToContainer(new Slot(player.inventory, 38 + chested, -73, 109 - chested * 27));
			}
			this.addSlotToContainer(new Slot(player.inventory, 40, -92, 96));
		} else {
			for (int notChested = 0; notChested < 2; ++notChested) {
				this.addSlotToContainer(new Slot(player.inventory, 36 + notChested, 109, 100 - notChested * 27));
				this.addSlotToContainer(new Slot(player.inventory, 38 + notChested, 55, 100 - notChested * 27));
			}
			this.addSlotToContainer(new Slot(player.inventory, 40, 36, 87));
		}
		
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.dragonInv.isUsableByPlayer(playerIn) && this.dragon.isEntityAlive() && this.dragon.getDistanceToEntity(playerIn) < 8.0F;
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