package com.TheRPGAdventurer.ROTD.inventory;

import com.TheRPGAdventurer.ROTD.objects.tileentities.TileEntityDragonShulker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


/**
 * Container of the Dragon Core
 *
 * @author WolfShotz
 */
public class ContainerDragonShulker extends Container {
    private final int numRows;
    private final TileEntityDragonShulker shulkerInventory;

    public ContainerDragonShulker(InventoryPlayer playerInv, TileEntityDragonShulker tileEntityDragonShulker, EntityPlayer player) {
        this.shulkerInventory=tileEntityDragonShulker;
        this.numRows=tileEntityDragonShulker.getSizeInventory() / 1;
        tileEntityDragonShulker.openInventory(player);

        //Build Dragon Core Inventory Slots
        this.addSlotToContainer(new Slot(tileEntityDragonShulker, 0, 80, 36));

        //Build Player Inventory Slots
        for (int i1=0; i1 < 3; ++i1) {
            for (int k1=0; k1 < 9; ++k1) {
                this.addSlotToContainer(new Slot(playerInv, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
            }
        }

        //Build Player Hotbar Slots
        for (int j1=0; j1 < 9; ++j1) {
            this.addSlotToContainer(new Slot(playerInv, j1, 8 + j1 * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.shulkerInventory.isUsableByPlayer(playerIn);
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        shulkerInventory.closeInventory(playerIn);
        if(shulkerInventory.isEmpty()) {
            playerIn.world.setBlockToAir(shulkerInventory.getPos());
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack=ItemStack.EMPTY;
        Slot slot=this.inventorySlots.get(index);

        if (slot!=null && slot.getHasStack()) {
            ItemStack itemstack1=slot.getStack();
            itemstack=itemstack1.copy();

            if (index < this.numRows * 9) {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
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

    public TileEntityDragonShulker getShulkerInventory() {
        return this.shulkerInventory;
    }
}
