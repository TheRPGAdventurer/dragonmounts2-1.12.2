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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public abstract class DragonInteract {
    
    protected final EntityTameableDragon dragon;
    
    public DragonInteract(EntityTameableDragon dragon) {
        this.dragon = dragon;
    }
    
    public abstract boolean interact(EntityPlayer player, ItemStack item);
    
    protected boolean lockingProcedures(EntityPlayer player) {
    	if(!dragon.isTamedFor(player) && !dragon.allowedOtherPlayers()) {
    	 	player.sendStatusMessage(new TextComponentTranslation("dragon.locked",new Object[0]), true);	
     		return false;
    	} else {
			  		return true;
    	}
    }
    
}
