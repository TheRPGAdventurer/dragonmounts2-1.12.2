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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

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
        if (dragon.isServer() && !dragon.isTamed() && !dragon.isChild() &&
                ItemUtils.consumeFish(player) ||
                ItemUtils.consumeEquipped(player, Items.PORKCHOP) ||
                ItemUtils.consumeEquipped(player, Items.FISH) ||
                ItemUtils.consumeEquipped(player, Items.COOKED_FISH) ||
                ItemUtils.consumeEquipped(player, Items.COOKED_PORKCHOP) || 
                ItemUtils.consumeEquipped(player, Items.BEEF) || 
                ItemUtils.consumeEquipped(player, Items.COOKED_BEEF) || 
                ItemUtils.consumeEquipped(player, Items.RABBIT) || 
                ItemUtils.consumeEquipped(player, Items.COOKED_RABBIT) || 
                ItemUtils.consumeEquipped(player, Items.MUTTON) || 
                ItemUtils.consumeEquipped(player, Items.COOKED_MUTTON) || 
                ItemUtils.consumeEquipped(player, Items.COOKED_FISH)) {
            dragon.tamedFor(player, dragon.getRNG().nextInt(5) == 0);
            return true;
        }
        return false;
    }    
}
