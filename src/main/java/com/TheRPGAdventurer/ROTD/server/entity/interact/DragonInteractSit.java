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

import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * @Modifier James Miller <TheRPGAdventurer.>
 */
public class DragonInteractSit extends DragonInteract {

    public DragonInteractSit(EntityTameableDragon dragon) {
        super(dragon); 
    }

    @Override
    public boolean interact(EntityPlayer player, ItemStack item) {
        if (dragon.isServer() && dragon.isTamed() && (ItemUtils.hasEquipped(player, Items.STICK) || ItemUtils.hasEquipped(player, Items.BONE) || ItemUtils.hasEquipped(player, ModItems.dragon_whistle)) 
        		&& dragon.onGround) {
       // 	if(!dragon.isSleeping()) {
            dragon.getAISit().setSitting(!dragon.isSitting());
       //     dragon.setSleeping(false);
            dragon.getNavigator().clearPathEntity();
            return true;
    //    	}
        	
      //  	if(dragon.isSleeping()) {
      //     dragon.setSleeping(false);
      //  	}
        }
        
        return false;
    }
    
}
