/*
 ** 2013 November 05
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.ai.ground;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.ai.EntityAIDragonBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

/**
 * Modified EntityAIFollowOwner that won't run if the pet is sitting.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class EntityAIDragonFollowOwner extends EntityAIDragonBase {

    private Entity owner;
    private World world;
    private double speed;
    private PathNavigate nav;
    private int updateTicks;
    private float maxDist;
    private float minDist;
    private boolean avoidWater;

    public EntityAIDragonFollowOwner(EntityTameableDragon dragon, double speed, float minDist, float maxDist) {
        super(dragon);
        this.speed = speed;
        this.minDist = minDist;
        this.maxDist = maxDist;

        nav = dragon.getNavigator();
        world = dragon.world;

        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        Entity ownerCurrent = dragon.getOwner();

        if (ownerCurrent == null) {
            return false;
        }

        if (ownerCurrent instanceof EntityPlayer) {
            if (((EntityPlayer) ownerCurrent).isSpectator()) {
                return false;
            }
        }

        if (dragon.isSitting()) {
            return false;
        }

        if (dragon.getDistanceSqToEntity(ownerCurrent) < minDist) {
            return false;
        }

        owner = ownerCurrent;
        return true;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        if (nav.noPath()) {
            return false;
        }

        if (dragon.isSitting()) {
            return false;
        }

        if (dragon.getDistanceToEntity(owner) < minDist) {
            return false;
        }

        return true;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        updateTicks = 0;

        //        avoidWater = dragon.getNavigator().getAvoidsWater();
        //        dragon.getNavigator().setAvoidsWater(false);
        // guess, based on vanilla EntityAIFollowOwner
        PathNavigate pathNavigate = dragon.getNavigator();
        if (pathNavigate instanceof PathNavigateGround) {
            PathNavigateGround pathNavigateGround = (PathNavigateGround) pathNavigate;
            this.avoidWater = ((PathNavigateGround) dragon.getNavigator()).getCanSwim();
            dragon.getBrain().setAvoidsWater(avoidWater);
        }

    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        owner = null;
        nav.clearPathEntity();
        PathNavigate pathNavigate = dragon.getNavigator();
        if (pathNavigate instanceof PathNavigateGround) {
            PathNavigateGround pathNavigateGround = (PathNavigateGround) pathNavigate;
            dragon.getBrain().setAvoidsWater(avoidWater);  // best guess, based on vanilla EntityAIFollowOwner
        }
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        // don't move when sitting
        if (dragon.isSitting()) {
            return;
        }

        // look towards owner
        dragon.getLookHelper().setLookPositionWithEntity(owner, dragon.getHeadYawSpeed(), dragon.getHeadPitchSpeed());

        // update every 10 ticks only from here
        if (--updateTicks > 0) {
            return;
        }
        updateTicks = 10;

        // finish task if it can move to the owner
        if (nav.tryMoveToEntityLiving(owner, speed)) {
            return;
        }

        // move only but don't teleport if leashed
        if (dragon.getLeashed()) {
            return;
        }

//        if(dragon.getDistanceSqToEntity(owner) > maxDist * maxDist) dragon.comeToPlayerFlying(owner.getPosition(), (EntityLivingBase) owner);

    }
}
