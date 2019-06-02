/*
 ** 2016 April 24
 **
 ** The author disclaims copyright to this source code. In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.interact;

import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.inits.ModTools;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.DragonBreed;
import com.TheRPGAdventurer.ROTD.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public abstract class DragonInteractBase {

    protected final EntityTameableDragon dragon;

    public DragonInteractBase(EntityTameableDragon dragon) {
        this.dragon = dragon;
    }

    public abstract boolean interact(EntityPlayer player, ItemStack item);
    
    protected boolean isAllowed(EntityPlayer player) {
		ItemFood food = (ItemFood) ItemUtils.consumeEquipped(player, DragonBreed.getFoodItems());
        if (!dragon.isTamed() && (food == null || !ItemUtils.consumeFish(player))) {
            player.sendStatusMessage(new TextComponentTranslation("dragon.notTamed"), true);
            return dragon.isTamedFor(player);
        } else if (!dragon.allowedOtherPlayers() && !dragon.isTamedFor(player) && (food == null || !ItemUtils.consumeFish(player)) && !ItemUtils.hasEquippedAmulet(player)) {
            player.sendStatusMessage(new TextComponentTranslation("dragon.locked"), true);
            return dragon.isTamedFor(player);
        } else {
            return true;
        }
    }
    
    public boolean hasInteractItemsEquipped(EntityPlayer player) {
    	return	ItemUtils.hasEquippedUsable(player)
    			|| ItemUtils.hasEquipped(player, ModTools.diamond_shears)
    			|| ItemUtils.hasEquipped(player, ModItems.dragon_wand)
    			|| ItemUtils.hasEquipped(player, ModItems.dragon_whistle)
    			|| ItemUtils.hasEquipped(player, ModItems.Amulet)
    			|| ItemUtils.hasEquipped(player, dragon.dragonEssence())
    			|| ItemUtils.hasEquippedFood(player);
    }
}