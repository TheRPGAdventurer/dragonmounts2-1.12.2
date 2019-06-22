/*
 ** 2013 November 05
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.ground;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.EntityAIDragonBase;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
    private int timeToRecalcPath;
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
        if (ownerCurrent == null) return false;
        if (ownerCurrent instanceof EntityPlayer)
            if (((EntityPlayer) ownerCurrent).isSpectator()) return false;
        if (dragon.isSitting()) return false;
        if (dragon.getDistance(ownerCurrent) < minDist && dragon.isAdult()) return false;
        owner = ownerCurrent;

        return dragon.nothing();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {

        if (nav.noPath()) return false;
        if (dragon.isSitting()) return false;
        if (dragon.getDistance(owner) < minDist) return false;
        return true;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        updateTicks = 0;
        this.timeToRecalcPath = 0;

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
        nav.clearPath();
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
        if (dragon.isSitting()) return;
        if (dragon.getControllingPlayer() != null) return;

        // look towards owner
        dragon.getLookHelper().setLookPositionWithEntity(owner, 120, 90);

        // update every 10 ticks only from here
        if (--updateTicks > 0) return;
        updateTicks = 10;
        
        // move only but don't teleport if leashed
        if (dragon.getLeashed()) return;

        // finish task if it can move to the owner
        if (nav.tryMoveToEntityLiving(owner, speed)) return;
        
        if (!this.dragon.isSitting()) {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;

                if (!dragon.getNavigator().tryMoveToEntityLiving(this.owner, this.speed)) {
                    if (!this.dragon.getLeashed() && !this.dragon.isRiding()) {
                        if (this.dragon.getDistanceSq(this.owner) >= 144.0D) {
                            int i = MathHelper.floor(this.owner.posX) - 2;
                            int j = MathHelper.floor(this.owner.posZ) - 2;
                            int k = MathHelper.floor(this.owner.getEntityBoundingBox().minY);

                            for (int l = 0; l <= 4; ++l) {
                                for (int i1 = 0; i1 <= 4; ++i1) {
                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1)) {
                                        dragon.setLocationAndAngles((double) ((float) (i + l) + 0.5F), (double) k, (double) ((float) (j + i1) + 0.5F), this.dragon.rotationYaw, this.dragon.rotationPitch);
                                        dragon.getNavigator().clearPath();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }         //        if(dragon.getDistanceSqToEntity(owner) > maxDist * maxDist) dragon.comeToPlayerFlying(owner.getPosition(), (EntityLivingBase) owner);
    }

    protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_) {
        BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        return iblockstate.getBlockFaceShape(this.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(dragon) && this.world.isAirBlock(blockpos.up()) && this.world.isAirBlock(blockpos.up(2));
    }
}
