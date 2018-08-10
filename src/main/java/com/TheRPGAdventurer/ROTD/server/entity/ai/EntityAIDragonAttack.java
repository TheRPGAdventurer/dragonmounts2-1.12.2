package com.TheRPGAdventurer.ROTD.server.entity.ai;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityAIDragonAttack extends EntityAIDragonBase {
	
    World world;
    /** An amount of decrementing ticks that allows the entity to attack once the tick reaches 0. */
    protected int attackTick;
    /** The speed with which the mob will approach the target */
    double speedTowardsTarget;
    /** When true, the mob will continue chasing its target, even if it can't find a path to them right now. */
    boolean longMemory;
    /** The PathEntity of our entity. */
    Path entityPathEntity;
    private int delayCounter;
    private double targetX;
    private double targetY;
    private double targetZ;
    protected final int attackInterval = 20;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;
    EntityPlayer rider = dragon.getControllingPlayer();

    public EntityAIDragonAttack(EntityTameableDragon dragon, double speedIn, boolean useLongMemory) {
    	super(dragon);
        this.world = dragon.world;
        this.speedTowardsTarget = speedIn;
        this.longMemory = useLongMemory;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @SuppressWarnings("null")
	public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.dragon.getAttackTarget();

        if (entitylivingbase == null && dragon.getLifeStageHelper().getTicksSinceCreation() <= dragon.getAppropriateAgeForInteraction() && ((EntityTameable) entitylivingbase).isTamed()) {
            return false;
        } else if (!entitylivingbase.isEntityAlive()) {
            return false;
        } else if(rider != null)  {
           return false;
        }  else if(dragon.isSitting()) {       
        	return false;        
        } else if(dragon.getControllingPlayer() != null) {
        	return false;                
        } else {
            if (canPenalize) {
                if (--this.delayCounter <= 0) {
                    this.entityPathEntity = this.dragon.getNavigator().getPathToEntityLiving(entitylivingbase);
                    this.delayCounter = 4 + this.dragon.getRNG().nextInt(7);
                    return this.entityPathEntity != null;
                } else {
                    return true;
                }
            }
            this.entityPathEntity = this.dragon.getNavigator().getPathToEntityLiving(entitylivingbase);

            if (this.entityPathEntity != null) {
                return true;
            } else {
                return this.getAttackReachSqr(entitylivingbase) >= this.dragon.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        EntityLivingBase entitylivingbase = this.dragon.getAttackTarget();

        if (entitylivingbase == null) {
            return false;
        } else if (!entitylivingbase.isEntityAlive()) {
            return false;
        } else if (!this.longMemory) {
            return !this.dragon.getNavigator().noPath();
        }  else if (!this.dragon.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase))) {
            return false;
        } else {
            return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).isSpectator() && !((EntityPlayer)entitylivingbase).isCreative() && dragon.getControllingPlayer() == null
            		&& !(((EntityPlayer)entitylivingbase).world.getDifficulty() == EnumDifficulty.PEACEFUL);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.dragon.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.delayCounter = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        EntityLivingBase entitylivingbase = this.dragon.getAttackTarget();

        if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative())) {
            this.dragon.setAttackTarget((EntityLivingBase)null);
        }

        this.dragon.getNavigator().clearPathEntity();
    }


    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
        EntityLivingBase entitylivingbase = this.dragon.getAttackTarget();
        boolean useBreath;
        boolean useBite;
        this.dragon.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        double d0 = this.dragon.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
        --this.delayCounter;

        if ((this.longMemory || this.dragon.getEntitySenses().canSee(entitylivingbase))) {
        		if(this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.dragon.getRNG().nextFloat() < 0.05F)) {
            this.targetX = entitylivingbase.posX;
            this.targetY = entitylivingbase.getEntityBoundingBox().minY;
            this.targetZ = entitylivingbase.posZ;
            this.delayCounter = 4 + this.dragon.getRNG().nextInt(7);

            if (this.canPenalize) {
                this.delayCounter += failedPathFindingPenalty;
                if (this.dragon.getNavigator().getPath() != null) {
                    net.minecraft.pathfinding.PathPoint finalPathPoint = this.dragon.getNavigator().getPath().getFinalPathPoint();
                    if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                        failedPathFindingPenalty = 0;
                    else
                        failedPathFindingPenalty += 10;
                } else {
                    failedPathFindingPenalty += 10;
                }
            }

            if (d0 > 1024.0D) {
                this.delayCounter += 10;
            } else if (d0 > 256.0D) {
                this.delayCounter += 5;
            }

            if (!this.dragon.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget)) {
                this.delayCounter += 15;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(entitylivingbase, d0);
        } 
        
    }


    protected void checkAndPerformAttack(EntityLivingBase target, double p_190102_2_) {
        double d0 = this.getAttackReachSqr(target);

        if (p_190102_2_ <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            this.dragon.swingArm(EnumHand.MAIN_HAND);
            this.dragon.attackEntityAsMob(target); 
        } 
    }
    
    protected void checkAndPerformUncontrolledBreathAttack(EntityLivingBase target, double p_190102_2_) {
        double d0 = this.getAttackReachSqrBreath(target);

        if (p_190102_2_ <= d0 && this.attackTick <= 0 && attackTick <= 40) { 
            this.attackTick = 20;
            dragon.attackEntityWithRanged(target);
        }
    	
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
        return (double)(this.dragon.width * 2.0F * this.dragon.width * 2.0F + attackTarget.width);
    }
    
    protected double getAttackReachSqrBreath(EntityLivingBase attackTarget) {
        return (double)(this.dragon.width * 10.0F * this.dragon.width * 10.0F + attackTarget.width);
    }

}
