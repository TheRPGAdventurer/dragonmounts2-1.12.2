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
    
    protected EntityPlayer owner2;
    
    public EntityAIDragonCatchOwner(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean shouldExecute() {        
        // don't catch if leashed
      //  if (dragon.getLeashed()) {
     //       return false;
    //    }
        
        if(!dragon.isSaddled()) {
        	return false;
        }
        
        owner2 = (EntityPlayer) dragon.getOwner2();
        
        // don't catch if ownerless 
        if (owner2 == null) {
            return false;
        }
        
        // no point in catching players in creative mode
        if (owner2.capabilities.isCreativeMode) {
            return false;
        }
        
        // don't catch if already being ridden
        if (dragon.isPassenger(owner2)) {
            return false;
        }
        
        // don't catch if owner has a working Elytra equipped
        // note: isBroken() is misleading, it actually checks if the items is usable
//        ItemStack itemStack = owner2.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
//        if (itemStack != null && itemStack.getItem() == Items.ELYTRA && ItemElytra.isUsable(itemStack)) {
//            return false;
 //       }
        
        // don't catch if owner is too far away
        double followRange = getFollowRange();
        if (dragon.getDistanceToEntity(owner2) > followRange) {
            return false;
        }
                
        return owner2.fallDistance > 4;
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
        
        // mount owner if close enough, otherwise move to owner
        if (dragon.getDistanceToEntity(owner2) < dragon.width) {
            owner2.startRiding(dragon);
        } else {
            dragon.getNavigator().tryMoveToEntityLiving(owner2, 5);
        }
    }
}
