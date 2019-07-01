package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIDragonHurtByTarget extends EntityAITarget {
    private final boolean entityCallsForHelp;
    private final Class<?>[] excludedReinforcementTypes;
    /**
     * Store the previous revengeTimer value
     */
    private int revengeTimerOld;
    private EntityTameableDragon dragon;

    public EntityAIDragonHurtByTarget(EntityTameableDragon dragon, boolean entityCallsForHelpIn, Class<?>... excludedReinforcementTypes) {
        super(dragon, true, true);
        this.dragon = dragon;
        this.entityCallsForHelp = entityCallsForHelpIn;
        this.excludedReinforcementTypes = excludedReinforcementTypes;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        int i = this.dragon.getRevengeTimer();
        EntityLivingBase entitylivingbase = this.dragon.getRevengeTarget();
        return i != this.revengeTimerOld && entitylivingbase != null && this.isSuitableTarget(entitylivingbase, false) && this.dragon.shouldAttackEntity(entitylivingbase, dragon.getOwner());
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.dragon.setAttackTarget(this.dragon.getRevengeTarget());
        this.target = this.dragon.getAttackTarget();
        this.revengeTimerOld = this.dragon.getRevengeTimer();
        this.unseenMemoryTicks = 300;

        if (this.entityCallsForHelp) {
            this.alertOthers();
        }

        super.startExecuting();
    }

    protected void alertOthers() {
        double d0 = this.getTargetDistance();

        for (EntityCreature entitycreature : this.dragon.world.getEntitiesWithinAABB(this.dragon.getClass(), (new AxisAlignedBB(this.dragon.posX, this.dragon.posY, this.dragon.posZ, this.dragon.posX + 1.0D, this.dragon.posY + 1.0D, this.dragon.posZ + 1.0D)).grow(d0, 10.0D, d0))) {
            if (this.dragon != entitycreature && entitycreature.getAttackTarget() == null && (!(this.dragon instanceof EntityTameable) || this.dragon.getOwner() == ((EntityTameable) entitycreature).getOwner()) && !entitycreature.isOnSameTeam(this.dragon.getRevengeTarget())) {
                boolean flag = false;

                for (Class<?> oclass : this.excludedReinforcementTypes) {
                    if (entitycreature.getClass() == oclass) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    this.setEntityAttackTarget(entitycreature, this.dragon.getRevengeTarget());
                }
            }
        }
    }

    protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
        creatureIn.setAttackTarget(entityLivingBaseIn);
    }
}