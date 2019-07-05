package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.entity.passive.EntityAnimal;

import java.util.List;

public class EntityAIDragonFollowParent extends EntityAIDragonBase {

    EntityAnimal parentAnimal;
    double moveSpeed;
    private int delayCounter;

    public EntityAIDragonFollowParent(EntityTameableDragon dragon, double speed) {
        super(dragon);
        this.setMutexBits(1);
        this.moveSpeed = speed;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.dragon.getGrowingAge() >= 0) {
            return  false;
        } else {
            List<EntityAnimal> list = this.dragon.world.<EntityAnimal>getEntitiesWithinAABB(this.dragon.getClass(), this.dragon.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
            EntityAnimal entityanimal = null;
            double d0 = Double.MAX_VALUE;

            for (EntityAnimal entityanimal1 : list) {
                if (entityanimal1.getGrowingAge() >= 0) {
                    double d1 = this.dragon.getDistanceSq(entityanimal1);

                    if (d1 <= d0) {
                        d0 = d1;
                        entityanimal = entityanimal1;
                    }
                }
            }

            if (entityanimal == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else {
                this.parentAnimal = entityanimal;
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
        } else if (!this.parentAnimal.isEntityAlive()) {
            return false;
        } else {
            double d0 = this.dragon.getDistanceSq(this.parentAnimal);
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
        this.parentAnimal = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            this.dragon.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
        }
    }
}