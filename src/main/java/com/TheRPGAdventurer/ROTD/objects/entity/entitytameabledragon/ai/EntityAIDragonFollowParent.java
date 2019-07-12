package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;

import java.util.List;

public class EntityAIDragonFollowParent extends EntityAIDragonBase {

    // assume any adult dragon nearby is a parent even if its not
    EntityTameableDragon adultDragon;
    double moveSpeed;
    private int delayCounter;

    public EntityAIDragonFollowParent(EntityTameableDragon dragon, double speed) {
        super(dragon);
        this.moveSpeed = speed;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (!dragon.isBaby()) {
            return false;
        } else {
            List<EntityTameableDragon> list = this.dragon.world.<EntityTameableDragon>getEntitiesWithinAABB(this.dragon.getClass(), this.dragon.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
            EntityTameableDragon adultDragon1 = null;
            double d0 = Double.MAX_VALUE;

            for (EntityTameableDragon adultDragon11 : list) {
                if (adultDragon11.getGrowingAge() >= 0) {
                    double d1 = this.dragon.getDistanceSq(adultDragon11);

                    if (d1 <= d0) {
                        d0 = d1;
                        adultDragon1 = adultDragon11;
                    }
                }
            }

            // play the follow owner method
            if (dragon.getOwner() != null && dragon.getOwner().isSneaking() && adultDragon != null) {
                return false;
            }

            if (adultDragon1 == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else if (!adultDragon1.isTamed() && adultDragon1.getControllingPlayer() != null) {
                return false;
            } else if (dragon.isSitting()) {
                return false;
            } else {
                this.adultDragon = adultDragon1;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        if (this.dragon.getGrowingAge() >= 0) {
            return false;
        } else if (!this.adultDragon.isEntityAlive()) {
            return false;
        } else {
            double d0 = this.dragon.getDistanceSq(this.adultDragon);
            return d0 >= 9.0D && d0 <= 256.0D;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.delayCounter = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.adultDragon = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            this.dragon.getNavigator().tryMoveToEntityLiving(this.adultDragon, this.moveSpeed);
        }
    }
}