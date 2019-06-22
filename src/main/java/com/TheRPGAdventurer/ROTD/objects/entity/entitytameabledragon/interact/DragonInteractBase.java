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
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
		boolean hasFood = DMUtils.consumeEquippedArray(player, DragonBreed.getFoodItems()) || DMUtils.consumeFish(player);
		
        if (!dragon.isTamed() && !hasFood) {
            player.sendStatusMessage(new TextComponentTranslation("dragon.notTamed"), true);
            return dragon.isTamedFor(player);
        } else if (!dragon.allowedOtherPlayers() && !dragon.isTamedFor(player) && dragon.isTamed() && !(dragon.getHealthRelative() < 1 && hasFood)) {
            player.sendStatusMessage(new TextComponentTranslation("dragon.locked"), true);
            return dragon.isTamedFor(player);
        } else return true;
    }
    
    public static boolean hasInteractItemsEquipped(EntityPlayer player) {
    	return DMUtils.hasEquippedUsable(player)
    			|| DMUtils.hasEquipped(player, ModTools.diamond_shears)
    			|| DMUtils.hasEquipped(player, ModItems.dragon_wand)
    			|| DMUtils.hasEquipped(player, ModItems.dragon_whistle)
    			|| DMUtils.hasEquipped(player, ModItems.Amulet)
    			|| DMUtils.hasEquipped(player, Items.BONE)
    			|| DMUtils.hasEquipped(player, Items.STICK)
    			|| DMUtils.hasEquippedFood(player);
    }
    
}