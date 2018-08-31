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

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonInteractTame extends DragonInteract {

    public DragonInteractTame(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean interact(EntityPlayer player, ItemStack item) { 
		// baby dragons are tameable now! :D
        if (dragon.isServer() && !dragon.isTamed() && ItemUtils.consumeEquipped(player, dragon.getBreed().getBreedingItem())) {
            dragon.tamedFor(player, dragon.getRNG().nextInt(5) == 0);
            return true;
        }
        
        return false;
    }    
}
