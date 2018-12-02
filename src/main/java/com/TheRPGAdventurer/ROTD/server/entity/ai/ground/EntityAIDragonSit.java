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
    @Override
    public boolean shouldExecute() {
        if (!this.dragon.isTamed()) {
            return false;
        } else if (!dragon.onGround) {
            return false;
        } else {          
            return this.isSitting;       
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.dragon.getNavigator().clearPathEntity();
        this.dragon.setSitting(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void resetTask() {
        this.dragon.setSitting(false);
    }

    /**
     * Sets the sitting flag.
     */
    @Override
    public void setSitting(boolean sitting) {
        this.isSitting = sitting;
    }
}