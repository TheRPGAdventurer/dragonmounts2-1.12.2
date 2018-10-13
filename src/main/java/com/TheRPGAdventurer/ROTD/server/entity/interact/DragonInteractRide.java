/*
** 2016 April 24
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.interact;

import com.TheRPGAdventurer.ROTD.client.initialization.ModTools;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonInteractRide extends DragonInteract {

    public DragonInteractRide(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean interact(EntityPlayer player, ItemStack item) {
        if (dragon.isServer() && dragon.isTamed() && dragon.isSaddled() && !ItemUtils.hasEquippedUsable(player) && 
        	!ItemUtils.hasEquipped(player, ModTools.diamond_shears) && !player.isSneaking() && dragon.getControllingPlayer() == null) { // isInAppropriateAgeForInteraction()
            dragon.setRidingPlayer(player);
            return true;
        }

        return false;
    }

}
