package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.weapons;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathAffectedBlock;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathAffectedEntity;
import com.TheRPGAdventurer.ROTD.util.math.MathX;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
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
        float hitDensity=currentHitDensity.getHitDensity();
        if (entityID==dragon.getEntityId()) return null;

        Entity entity=world.getEntityByID(entityID);
        if (entity==null || !(entity instanceof EntityLivingBase) || entity.isDead) {
            return null;
        }

        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer=(EntityPlayer) entity;
            if (entityPlayer.getRidingEntity()==dragon) {
                return null;
            }
        }

        if (entity.isBurning()) {
            entity.extinguish();
        }


        final float DAMAGE_PER_HIT_DENSITY=FIRE_DAMAGE * hitDensity;

        //    System.out.format("Old entity motion:[%.2f, %.2f, %.2f]\n", entity.motionX, entity.motionY, entity.motionZ);
        // push in the direction of the wind, but add a vertical upthrust as well
        final double FORCE_MULTIPLIER=0.05;
        final double VERTICAL_FORCE_MULTIPLIER=0.05;
        float airForce=currentHitDensity.getHitDensity();
        Vec3d airForceDirection=currentHitDensity.getHitDensityDirection();
        Vec3d airMotion=MathX.multiply(airForceDirection, FORCE_MULTIPLIER);

        final double WT_ENTITY=0.05;
        final double WT_AIR=1 - WT_ENTITY;
        ((EntityLivingBase) entity).knockBack(entity, 0.8F, dragon.posX - entity.posX, dragon.posZ - entity.posZ);
        entity.attackEntityFrom(DamageSource.causeMobDamage(dragon), DAMAGE_PER_HIT_DENSITY);
        triggerDamageExceptions(entity, DAMAGE_PER_HIT_DENSITY,entityID, currentHitDensity);
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

    private static Map<Material, Integer> materialDisintegrateTime=Maps.newHashMap();  // lazy initialisation

    private void initialiseStatics() {
        if (!materialDisintegrateTime.isEmpty()) return;
        final int INSTANT=0;
        final int MODERATE=10;
        final int SLOW=100;
        
        materialDisintegrateTime.put(Material.LEAVES, INSTANT);
        materialDisintegrateTime.put(Material.PLANTS, INSTANT);
        materialDisintegrateTime.put(Material.FIRE, INSTANT);
        materialDisintegrateTime.put(Material.VINE, SLOW);
        materialDisintegrateTime.put(Material.WEB, SLOW);
        materialDisintegrateTime.put(Material.GOURD, SLOW);
        materialDisintegrateTime.put(Material.SPONGE, SLOW);
        materialDisintegrateTime.put(Material.SAND, SLOW);
        materialDisintegrateTime.put(Material.SNOW, SLOW);
        materialDisintegrateTime.put(Material.CRAFTED_SNOW, SLOW);
        materialDisintegrateTime.put(Material.CACTUS, SLOW);
    }

}
