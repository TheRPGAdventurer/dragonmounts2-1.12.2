package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.ground;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAISit;

public class EntityAIDragonSit extends EntityAISit {
    public EntityTameableDragon dragon;

    /**
     * If the Entitydragon is sitting.
     */
    private boolean isSitting;

    public EntityAIDragonSit(EntityTameableDragon dragon) {
        super(dragon);
        this.dragon = dragon;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (!this.dragon.isTamed() || this.dragon.isInWater() || !this.dragon.onGround || this.dragon.getControllingPlayer() != null && !dragon.nowhistlecommands()) {
            return false;
        } else {
            EntityLivingBase entitylivingbase = this.dragon.getOwner();

            if (entitylivingbase == null) {
                return true;
            } else {
                return this.dragon.getDistanceSq(entitylivingbase) < 144.0D && entitylivingbase.getRevengeTarget() != null ? false : this.isSitting;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.dragon.getNavigator().clearPath();
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