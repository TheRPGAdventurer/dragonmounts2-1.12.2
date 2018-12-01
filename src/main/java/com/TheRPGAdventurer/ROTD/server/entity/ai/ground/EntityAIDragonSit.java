package com.TheRPGAdventurer.ROTD.server.entity.ai.ground;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAISit;

public class EntityAIDragonSit extends EntityAISit {
    /** If the Entitydragon is sitting. */
    private boolean isSitting;
    public EntityTameableDragon dragon;

    public EntityAIDragonSit(EntityTameableDragon entityIn) {
        super(entityIn);
        this.dragon = entityIn;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (!this.dragon.isTamed()) {
            return false;
        } else if (!dragon.onGround) {
            return false;
        } else {
            EntityLivingBase owner = this.dragon.getOwner();
            if (owner == null) {
                return true;
            } else {
                return this.dragon.getDistanceSqToEntity(owner) < 144.0D && owner.getRevengeTarget() != null ? false : this.isSitting;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.dragon.getNavigator().clearPathEntity();
        this.dragon.setSitting(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.dragon.setSitting(false);
    }

    /**
     * Sets the sitting flag.
     */
    public void setSitting(boolean sitting) {
        this.isSitting = sitting;
    }
}