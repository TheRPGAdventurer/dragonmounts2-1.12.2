/*
 ** 2013 November 03
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.ai;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class EntityAIDragonCatchOwner extends EntityAIDragonBase {
    
    protected EntityPlayer owner;
    
    public EntityAIDragonCatchOwner(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean shouldExecute() {        
        // don't catch if leashed
        if (dragon.getLeashed()) {
            return false;
        }
        
        if(!dragon.isSaddled()) {
        	return false;
        }
        
        owner = (EntityPlayer) dragon.getOwner();
        
        // don't catch if ownerless 
        if (owner == null) {
            return false;
        }
        
        // no point in catching players in creative mode
        if (owner.capabilities.isCreativeMode) {
            return false;
        }
        
        // don't catch if already being ridden
        if (dragon.isPassenger(owner)) {
            return false;
        }
        
        // don't follow if sitting
        if (dragon.isSitting()) {
        	return false;
        }
        
        dragon.setBoosting(dragon.getDistance(owner) < dragon.width + dragon.getScale());
        
        // don't catch if owner has a working Elytra equipped
        // note: isBroken() is misleading, it actually checks if the items is usable
        ItemStack itemStack = owner.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (itemStack != null && itemStack.getItem() == Items.ELYTRA && ItemElytra.isUsable(itemStack)) {
            return false;
        }    
                
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return shouldExecute() && !dragon.getNavigator().noPath();
    }
    
    @Override
    public void updateTask() {
    	
        // catch owner in flight if possible
        if (!dragon.isFlying()) {
            dragon.liftOff();
        }
        
        // don't catch if owner is too far away
        double followRange = getFollowRange();
        if (dragon.getDistance(owner) < followRange) {
          // mount owner if close enough, otherwise move to owner
           if (dragon.getDistance(owner) < dragon.width + dragon.getScale()) {
              owner.startRiding(dragon);
              dragon.setBoosting(dragon.getDistance(owner) < dragon.width + dragon.getScale());
           } else {
              dragon.getNavigator().tryMoveToEntityLiving(owner, 5);
           }
        }
    }
}
