package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.weapons;

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathAffectedBlock;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathAffectedEntity;
import com.TheRPGAdventurer.ROTD.util.reflection.PrivateAccessor;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by TGG on 5/08/2015.
 * <p>
 * Models the effects of a breathweapon on blocks and entities
 * Usage:
 * 1) Construct with a parent dragon
 * 2) affectBlock() to apply an area of effect to the given block (eg set fire to it)
 * 3) affectEntity() to apply an area of effect to the given entity (eg damage it)
 */
public class BreathWeaponIce extends BreathWeapon implements PrivateAccessor {

    public BreathWeaponIce(EntityTameableDragon i_dragon) {
        super(i_dragon);
    }

    /**
     * if the hitDensity is high enough, manipulate the block (eg set fire to it)
     *
     * @param world
     * @param blockPosition     the world [x,y,z] of the block
     * @param currentHitDensity
     * @return the updated block hit density
     */
    @Override
    public BreathAffectedBlock affectBlock(World world, Vec3i blockPosition, BreathAffectedBlock currentHitDensity) {
        checkNotNull(world);
        checkNotNull(blockPosition);
        checkNotNull(currentHitDensity);

        BlockPos blockPos = new BlockPos(blockPosition);
        IBlockState iBlockState = world.getBlockState(blockPos);
        Block block = iBlockState.getBlock();

        Random rand = new Random();
        BlockPos sideToIgnite = blockPos.offset(EnumFacing.UP);
        if (DragonMountsConfig.canIceBreathBePermanent) {
            world.setBlockState(sideToIgnite, Blocks.SNOW_LAYER.getDefaultState());
        } else if ((world.getBlockState(blockPos).getBlock() == Blocks.WATER || world.getBlockState(blockPos).getBlock() == Blocks.FLOWING_WATER)
                && world.getBlockState(blockPos.up()).getBlock() == Blocks.AIR) {
            if (DragonMountsConfig.canIceBreathBePermanent) {
//                world.mayPlace(Blocks.ICE, blockPos, false, EnumFacing.DOWN, null);
                world.setBlockState(blockPos, Blocks.ICE.getDefaultState(), 1);
            } else if (!DragonMountsConfig.canIceBreathBePermanent) {
//                world.mayPlace(Blocks.FROSTED_ICE, blockPos, false, EnumFacing.DOWN, null);
                world.setBlockState(blockPos, Blocks.FROSTED_ICE.getDefaultState(), 1);
            }
        }

        if (block == Blocks.LAVA) {
            world.setBlockState(blockPos, Blocks.OBSIDIAN.getDefaultState());
        }

        if (block == Blocks.FLOWING_LAVA) {
            world.setBlockState(blockPos, Blocks.COBBLESTONE.getDefaultState());
        }
        if (block == Blocks.FIRE) {
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        }

        return new BreathAffectedBlock();  // reset to zero
    }

    /**
     * if the hitDensity is high enough, manipulate the entity (eg set fire to it, damage it)
     * A dragon can't be damaged by its own breathweapon;
     *
     * @param world
     * @param entityID          the ID of the affected entity
     * @param currentHitDensity the hit density
     * @return the updated hit density; null if the entity is dead, doesn't exist, or otherwise not affected
     */
    @Override
    public BreathAffectedEntity affectEntity(World world, Integer entityID, BreathAffectedEntity currentHitDensity) {
        checkNotNull(world);
        checkNotNull(entityID);
        checkNotNull(currentHitDensity);

        Entity entity = world.getEntityByID(entityID);
        if (entity == null || !(entity instanceof EntityLivingBase) || entity.isDead) {
            return null;
        }

        float hitDensity = currentHitDensity.getHitDensity();
        final float DAMAGE_PER_HIT_DENSITY = ICE_DAMAGE * hitDensity;
        triggerDamageExceptions(entity, DAMAGE_PER_HIT_DENSITY, entityID, currentHitDensity);
        entity.attackEntityFrom(DamageSource.causeMobDamage(dragon), DAMAGE_PER_HIT_DENSITY);
        ((EntityLivingBase) entity).knockBack(entity, 0.1F, dragon.posX - entity.posX, dragon.posZ - entity.posZ);


        if (entity.isBurning()) {
            entity.extinguish();
            entity.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f, 0.0f);
        }

        if((dragon.getControllingPlayer() != null && dragon.getControllingPlayer() != entity) || (dragon.getRidingEntity() != entity && dragon.getRidingEntity() != null)) {
            entity.isWet();
            PotionEffect iceEffect=new PotionEffect(MobEffects.SLOWNESS, 100);
            ((EntityLivingBase) entity).addPotionEffect(iceEffect); // Apply a copy of the PotionEffect to the player
        }

        this.xp(entity);

        return currentHitDensity;
    }
}
