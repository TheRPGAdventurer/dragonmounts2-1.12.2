package com.TheRPGAdventurer.ROTD.entity.breath.weapons;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breath.BreathAffectedBlock;
import com.TheRPGAdventurer.ROTD.entity.breath.BreathAffectedEntity;
import com.TheRPGAdventurer.ROTD.util.math.MathX;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by TGG on 7/12/2015.
 */
public class BreathWeaponAether extends BreathWeapon {
    public BreathWeaponAether(EntityTameableDragon i_dragon) {
        super(i_dragon);
        initialiseStatics();
    }

    @Override
    public BreathAffectedBlock affectBlock(World world, Vec3i blockPosition, BreathAffectedBlock currentHitDensity) {
        checkNotNull(world);
        checkNotNull(blockPosition);
        checkNotNull(currentHitDensity);

        BlockPos blockPos=new BlockPos(blockPosition);
        IBlockState iBlockState=world.getBlockState(blockPos);
        Block block=iBlockState.getBlock();

        Random rand=new Random();

        // effects- which occur after the block has been exposed for sufficient time
        // soft blocks such as sand, leaves, grass, flowers, plants, etc get blown away (destroyed)
        // blows away snow but not ice
        // shatters panes, but not glass
        // extinguish torches
        // causes fire to spread rapidly - NO, this looks stupid, so delete it

        if (block==null) return currentHitDensity;
        Material material=block.getMaterial(iBlockState);
        if (material==null) return currentHitDensity;

        if (materialDisintegrateTime.containsKey(material)) {
            Integer disintegrationTime=materialDisintegrateTime.get(material);
            if (disintegrationTime!=null && currentHitDensity.getMaxHitDensity() > disintegrationTime) {
                final boolean DROP_BLOCK=true;
                world.destroyBlock(blockPos, DROP_BLOCK);
                return new BreathAffectedBlock();
            }
            return currentHitDensity;
        }

        if (material==Material.FIRE) {
            final float THRESHOLD_FIRE_SPREAD=1;
            final float MAX_FIRE_DENSITY=10;
            final int MAX_PATH_LENGTH=4;
            double density=currentHitDensity.getMaxHitDensity();
            if (density > THRESHOLD_FIRE_SPREAD) {
                int pathLength=MathHelper.floor(MAX_PATH_LENGTH / MAX_FIRE_DENSITY * density);
                if (pathLength > MAX_PATH_LENGTH) {
                    pathLength=MAX_PATH_LENGTH;
                }
                //        spreadFire(world, blockPos, pathLength);    // removed because it didn't work well
            }
            return currentHitDensity;
        }

        if (block==Blocks.TORCH) {
            final float THRESHOLD_FIRE_EXTINGUISH=1;
            if (currentHitDensity.getMaxHitDensity() > THRESHOLD_FIRE_EXTINGUISH) {
                final boolean DROP_BLOCK=true;
                world.destroyBlock(blockPos, DROP_BLOCK);
                return new BreathAffectedBlock();
            }
            return currentHitDensity;
        }

        if (block==Blocks.GLASS_PANE || block==Blocks.STAINED_GLASS_PANE) {
            final float THRESHOLD_SMASH_PANE=1;
            if (currentHitDensity.getMaxHitDensity() > THRESHOLD_SMASH_PANE) {
                final boolean DROP_BLOCK=true;
                world.destroyBlock(blockPos, DROP_BLOCK);
                return new BreathAffectedBlock();
            }
            return currentHitDensity;
        }

        return currentHitDensity;
    }

    @Override
    public BreathAffectedEntity affectEntity(World world, Integer entityID, BreathAffectedEntity currentHitDensity) {
        // 1) extinguish fire on entity
        // 2) pushes entity in the direction of the air, with upward thrust added
        checkNotNull(world);
        checkNotNull(entityID);
        checkNotNull(currentHitDensity);

        if (entityID==dragon.getEntityId()) return null;

        Entity entity=world.getEntityByID(entityID);
        if (entity==null || !(entity instanceof EntityLivingBase) || entity.isDead) {
            return null;
        }

        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer=(EntityPlayer) entity;
            if (entityPlayer.getRidingEntity() == dragon) {
                return null;
            }
        }

        if (entity.isBurning()) {
            entity.extinguish();
        }

        //    System.out.format("Old entity motion:[%.2f, %.2f, %.2f]\n", entity.motionX, entity.motionY, entity.motionZ);
        // push in the direction of the wind, but add a vertical upthrust as well
        final double FORCE_MULTIPLIER=0.05;
        final double VERTICAL_FORCE_MULTIPLIER=0.05;
        float airForce=currentHitDensity.getHitDensity();
//        Vec3d airForceDirection=currentHitDensity.getHitDensityDirection();
//        Vec3d airMotion=MathX.multiply(airForceDirection, FORCE_MULTIPLIER);

        final double WT_ENTITY=0.5;
        final double WT_AIR=1 - WT_ENTITY;
        entity.motionX=WT_ENTITY * entity.motionX + WT_AIR * airMotion.x;
        entity.motionZ=WT_ENTITY * entity.motionZ + WT_AIR * airMotion.z;

        final double UPFORCE_THRESHOLD=1.0;
        if (airForce > UPFORCE_THRESHOLD) {
            final double GRAVITY_OFFSET=-0.08;
            Vec3d up=new Vec3d(0, 1, 0);
            Vec3d upMotion=MathX.multiply(up, VERTICAL_FORCE_MULTIPLIER * airForce);
            //      System.out.format("upMotion:%s\n", upMotion);
            entity.motionY=WT_ENTITY * (entity.motionY - GRAVITY_OFFSET) + WT_AIR * upMotion.y;
        }

        //    System.out.format("airMotion:%s\n", airMotion);
        //    System.out.format("New entity motion:[%.2f, %.2f, %.2f]\n", entity.motionX, entity.motionY, entity.motionZ);

        final int DELAY_UNTIL_DECAY=5;
        final float DECAY_PERCENTAGE_PER_TICK=10.0F;
//        currentHitDensity.setDecayParameters(DECAY_PERCENTAGE_PER_TICK, DELAY_UNTIL_DECAY);

        return currentHitDensity;
    }

    //  /** flood fill from the starting block for the indicated number of blocks, setting fire to blocks
    //   * @param world
    //   * @param blockPos
    //   * @param maxPathLength maximum path length to flood fill to [0 - 10]
    //   */
    //  private void spreadFire(World world, BlockPos blockPos, int maxPathLength)
    //  {
    //    checkArgument(maxPathLength >= 0 && maxPathLength <= 10);
    //    HashSet<BlockPos> blocksToSearchFrom = new HashSet<BlockPos>();
    //    HashSet<BlockPos> blocksSearched = new HashSet<BlockPos>();
    //    HashSet<BlockPos> blocksToIgnite = new HashSet<BlockPos>();
    //
    //    blocksToSearchFrom.add(blockPos);
    //    for (int pathLength = 0; pathLength < maxPathLength; ++pathLength) {
    //      HashSet<BlockPos> blocksToSearchFromNext = new HashSet<BlockPos>();
    //
    //      for (BlockPos whichBlock : blocksToSearchFrom) {
    //        for (EnumFacing whichDirection : EnumFacing.VALUES) {
    //          BlockPos adjacent = whichBlock.offset(whichDirection);
    //          if (!blocksSearched.contains(adjacent)) {
    //            blocksSearched.add(adjacent);
    //
    //            IBlockState adjacentBlockState = world.getBlockState(adjacent);
    //            Material material = adjacentBlockState.getBlock().getMaterial();
    //            if (material == Material.air) {
    //              blocksToSearchFromNext.add(adjacent);
    //              blocksToIgnite.add(adjacent);
    //            }
    //          }
    //        }
    //      }
    //    }
    //
    //    for (BlockPos blockPosIgnite : blocksToIgnite) {
    //      world.setBlockState(blockPosIgnite, Blocks.fire.getDefaultState());
    //    }
    //  }

    private static Map<Material, Integer> materialDisintegrateTime=Maps.newHashMap();  // lazy initialisation

    private void initialiseStatics() {
        if (!materialDisintegrateTime.isEmpty()) return;
        final int INSTANT=0;
        final int MODERATE=10;
        final int SLOW=50;
        materialDisintegrateTime.put(Material.LEAVES, INSTANT);
        materialDisintegrateTime.put(Material.PLANTS, INSTANT);
        materialDisintegrateTime.put(Material.VINE, INSTANT);
        materialDisintegrateTime.put(Material.WEB, INSTANT);
        materialDisintegrateTime.put(Material.GOURD, INSTANT);
        materialDisintegrateTime.put(Material.SPONGE, MODERATE);
        materialDisintegrateTime.put(Material.SAND, MODERATE);
        materialDisintegrateTime.put(Material.SNOW, MODERATE);
        materialDisintegrateTime.put(Material.CRAFTED_SNOW, MODERATE);
        materialDisintegrateTime.put(Material.CACTUS, MODERATE);
    }

}
