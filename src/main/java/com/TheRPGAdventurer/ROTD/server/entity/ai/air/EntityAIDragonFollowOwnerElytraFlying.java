/*
** 2016 April 26
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.ai.air;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.ai.EntityAIDragonBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class EntityAIDragonFollowOwnerElytraFlying extends EntityAIDragonBase {
    
    protected EntityPlayer owner;

    public EntityAIDragonFollowOwnerElytraFlying(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean shouldExecute() {
    	
    	if (!dragon.canFly()) {
    		return false;
    	}
        
        // don't follow if sitting
        if (dragon.isSitting()) {
        	return false;
        }
        
        owner = (EntityPlayer) dragon.getOwner2();
        
        // don't follow if ownerless 
        if (owner == null) {
            return false;
        }
        
        // don't follow if already being ridden
        if (dragon.isPassenger(owner)) {
            return false;
        }
        
        // follow only if the owner is using an Elytra
        return owner.isElytraFlying();
    }
    
    @Override
    public void updateTask() {
        dragon.getNavigator().tryMoveToEntityLiving(owner, 1);
        
        if(dragon.getDistanceToEntity(owner) > 10) {
    		PotionEffect speed = new PotionEffect(MobEffects.SPEED, 20*10, 3);
        	if (!dragon.isPotionActive(speed.getPotion())) { // If the Potion isn't currently active,
        		dragon.addPotionEffect(speed); // Apply a copy of the PotionEffect to the player
    		} 
        }
    }
}
