/*
 ** 2016 April 26
 **
 ** The author disclaims copyright to this source code. In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.air;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.EntityAIDragonBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class EntityAIDragonFollowOwnerElytraFlying extends EntityAIDragonBase {

    protected EntityPlayer owner = (EntityPlayer) dragon.getOwner();

    public EntityAIDragonFollowOwnerElytraFlying(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean shouldExecute() {

        if (!dragon.canFly())
            return false;


        // don't follow if sitting
        if (dragon.isSitting())
            return false;


        if (dragon.getLeashed())
            return false;


        // don't follow if ownerless 
        if (owner == null)
            return false;


        // don't follow if already being ridden
        if (dragon.isPassenger(owner))
            return false;


        if (!dragon.nothing())
            return false;


        // follow only if the owner is using an Elytra
        return owner.isElytraFlying();
    }

    @Override
    public void updateTask() {
        // liffoff
        if (!dragon.isFlying())
            dragon.liftOff();


        // mount owner if close enough, otherwise move to owner
        if (dragon.getDistance(owner) <= dragon.width || dragon.getDistance(owner) <= dragon.height && owner.isSneaking())
            owner.startRiding(dragon);


        dragon.getNavigator().tryMoveToXYZ(owner.posX, owner.posY, owner.posZ, 1);
        dragon.setBoosting(dragon.getDistance(owner) > 18);
    }
}
