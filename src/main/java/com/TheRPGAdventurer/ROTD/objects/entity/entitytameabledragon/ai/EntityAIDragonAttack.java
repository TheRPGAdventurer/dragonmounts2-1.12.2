package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIDragonAttack extends EntityAIDragonBase {
    World world;
    /**
     * An amount of decrementing ticks that allows the entity to attack once the tick reaches 0.
     */
    protected int attackTick;
    /**
     * The speed with which the mob will approach the target
     */
    double speedTowardsTarget;
    /**
     * When true, the mob will continue chasing its target, even if it can't find a path to them right now.
     */
    boolean longMemory;
    /**
     * The PathEntity of our entity.
     */
    Path entityPathEntity;
    private int delayCounter;
    private double targetX;
    private double targetY;
    private double targetZ;
    protected final int attackInterval = 20;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;
    public float breathTick;
    private boolean shouldUseRange = false;

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
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.dragon.getAttackTarget();

        if (entitylivingbase == null) {
            return false;
        } else if (!entitylivingbase.isEntityAlive()) {
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
        } else if (dragon.getControllingPassenger() != null) {
            return false;
        } else if (!entitylivingbase.isEntityAlive()) {
            return false;
        } else if (!this.longMemory) {
            return !this.dragon.getNavigator().noPath();
        } else if (!this.dragon.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase))) {
            return false;
        } else {
            return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).isSpectator() && !((EntityPlayer) entitylivingbase).isCreative();
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
        if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer) entitylivingbase).isSpectator() || ((EntityPlayer) entitylivingbase).isCreative())) {
            this.dragon.setAttackTarget(null);
            dragon.setUsingBreathWeapon(false);
        }
        this.dragon.getNavigator().clearPath();
        dragon.setUsingBreathWeapon(false);
    }

    private int getPoints(EntityLivingBase target) {
        if (target instanceof EntityAnimal) {
            return 90;
        } else
            return 40;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
        EntityLivingBase target = this.dragon.getAttackTarget();
        this.dragon.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        double targetDistSq = this.dragon.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
        --this.delayCounter;
        if (target.isDead) {
            dragon.setHunger(dragon.getHunger() + getPoints(target));
        }

        if ((this.longMemory || this.dragon.getEntitySenses().canSee(target)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || target.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.dragon.getRNG().nextFloat() < 0.05F)) {
            this.targetX = target.posX;
            this.targetY = target.getEntityBoundingBox().minY;
            this.targetZ = target.posZ;
            this.delayCounter = 4 + this.dragon.getRNG().nextInt(7);

            if (this.canPenalize && !shouldUseRange) {
                this.delayCounter += failedPathFindingPenalty;
                if (dragon.getNavigator().getPath() != null) {
                    PathPoint finalPathPoint = this.dragon.getNavigator().getPath().getFinalPathPoint();
                    if (finalPathPoint != null && target.getDistanceSq(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                        failedPathFindingPenalty = 0;
                    else failedPathFindingPenalty += 10;
                } else {
                    failedPathFindingPenalty += 10;
                }
            }

            if (targetDistSq > 1024.0D) {
                this.delayCounter += 10;
            } else if (targetDistSq > 256.0D) {
                this.delayCounter += 5;
            }

            if (!this.dragon.getNavigator().tryMoveToEntityLiving(target, this.speedTowardsTarget)) {
                this.delayCounter += 15;
            }

        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(target, targetDistSq);
    }

    public boolean isWithinBreathRange(double targetDistSq) {
        return targetDistSq > 4 && targetDistSq < 95 ? true : false;
    }

    public boolean isWithinMeleeRange(double targetDistSq) {
        return targetDistSq < 3 ? true : false;
    }

    public boolean shouldUseBreathWeapon() {
        return false;
    }

    public int threatLevel() {
        return 0;
    }

    public boolean lookingAtTarget(EntityLivingBase target) {
        Vec3d vec3d = dragon.getLook(1.0F).normalize();
        Vec3d vec3d1 = new Vec3d(target.posX - dragon.posX, target.getEntityBoundingBox().minY + (double) target.getEyeHeight() - (dragon.posY + (double) dragon.getEyeHeight()), target.posZ - dragon.posZ);
        double d0 = vec3d1.length();
        vec3d1 = vec3d1.normalize();
        double d1 = vec3d.dotProduct(vec3d1);
        return d1 > 1.0D - 0.025D / d0 ? dragon.canEntityBeSeen(target) : false;
    }

    protected void checkAndPerformAttack(EntityLivingBase target, double targetDistSq) {
        double attackReach = this.getAttackReachSqr(target);
        boolean shouldUseMelee = this.attackTick <= 0 && targetDistSq <= attackReach;
        shouldUseRange = this.attackTick <= 0 && (isWithinBreathRange(targetDistSq) || dragon.isFlying()) && dragon.getEntitySenses().canSee(target) && !(target instanceof EntityAnimal) && dragon.isFlying();// && lookingAtTarget(target);

        if (shouldUseMelee) {
            this.attackTick = 20;
            this.dragon.swingArm(EnumHand.MAIN_HAND);
            this.dragon.attackEntityAsMob(target);
        } else if (shouldUseRange) {
            this.attackTick = 20;
            dragon.setUsingBreathWeapon(target.isEntityAlive());
            dragon.getLookHelper().setLookPositionWithEntity(target, 120, 90);
        }
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
        return (double) (this.dragon.width * 2.0F * this.dragon.width * 2.0F + attackTarget.width);
    }
}