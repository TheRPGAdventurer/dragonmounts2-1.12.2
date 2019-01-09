package com.TheRPGAdventurer.ROTD.server.entity.interact;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;

public class DragonInteractBreed extends DragonInteract {

	public DragonInteractBreed(EntityTameableDragon dragon) {
		super(dragon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean interact(EntityPlayer player, ItemStack item) {
		if(dragon.getBreedType() == EnumDragonBreed.ENCHANT && ItemUtils.hasEquipped(player, Items.GLASS_BOTTLE)) { 
	        ItemStack itemstack = player.getHeldItem(player.getActiveHand());
			this.turnBottleIntoItem(itemstack, player, new ItemStack(Items.EXPERIENCE_BOTTLE));
			dragon.world.playSound(player, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 2, 1);
			return true;
		}
		return false;
	}
	
	protected ItemStack turnBottleIntoItem(ItemStack p_185061_1_, EntityPlayer player, ItemStack stack) {
        p_185061_1_.shrink(1);
    //    player.addStat(StatList.getObjectUseStats(this));

        if (p_185061_1_.isEmpty())
        {
            return stack;
        }
        else
        {
            if (!player.inventory.addItemStackToInventory(stack))
            {
                player.dropItem(stack, false);
            }

            return p_185061_1_;
        }
    }

}
