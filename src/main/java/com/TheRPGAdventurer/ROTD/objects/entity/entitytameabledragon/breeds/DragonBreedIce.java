/*
 ** 2013 October 24
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.DragonLifeStage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.BiomeDictionary;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonBreedIce extends DragonBreed {

    private static final Block FOOTPRINT = Blocks.SNOW_LAYER;
    private static final float FOOTPRINT_CHANCE = 0.01f;

    public DragonBreedIce() {
        super("ice", 0x00f2ff);

        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);

        setHabitatBlock(Blocks.SNOW);
        setHabitatBlock(Blocks.SNOW_LAYER);
        setHabitatBlock(Blocks.ICE);
        setHabitatBlock(Blocks.PACKED_ICE);
        setHabitatBlock(Blocks.FROSTED_ICE);

        setHabitatBiome(Biomes.FROZEN_OCEAN);
        setHabitatBiome(Biomes.FROZEN_RIVER);
        setHabitatBiome(Biomes.ICE_MOUNTAINS);
        setHabitatBiome(Biomes.ICE_PLAINS);
    }

    public static void freezeNearby(EntityLivingBase living, World worldIn, BlockPos pos, int level) {
        if (living.onGround) {
            float f = (float) Math.min(16, 2 + level);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

            for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add((double) (-f), -1.0D, (double) (-f)), pos.add((double) f, -1.0D, (double) f))) {
                if (blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double) (f * f)) {
                    blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                    IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getMaterial() == Material.AIR) {
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);

                        if (iblockstate1.getMaterial() == Material.WATER && (iblockstate1.getBlock() == net.minecraft.init.Blocks.WATER || iblockstate1.getBlock() == net.minecraft.init.Blocks.FLOWING_WATER) && iblockstate1.getValue(BlockLiquid.LEVEL).intValue() == 0 && worldIn.mayPlace(Blocks.FROSTED_ICE, blockpos$mutableblockpos1, false, EnumFacing.DOWN, null)) {
                            worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.FROSTED_ICE.getDefaultState());
                            worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), Blocks.FROSTED_ICE, MathHelper.getInt(living.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onUpdate(EntityTameableDragon dragon) {
        // place some snow footprints where the dragon walks
        if (!dragon.isFlying()) {
            World world = dragon.world;
            for (int i = 0; i < 4; i++) {
                if (world.rand.nextFloat() < FOOTPRINT_CHANCE) {
                    continue;
                }

                double bx = dragon.posX + (i % 2 * 2 - 1) * 0.25;
                double by = dragon.posY + 0.5;
                double bz = dragon.posZ + (i / 2 % 2 * 2 - 1) * 0.25;

                BlockPos blockPos = new BlockPos(bx, by, bz);
                // from EntitySnowman.onLivingUpdate, with slight tweaks
                if (world.getBlockState(blockPos).getMaterial() == Material.AIR && world.rand.nextFloat() < FOOTPRINT_CHANCE && world.getBiomeForCoordsBody(blockPos).getTemperature(blockPos) < 0.1F && FOOTPRINT.canPlaceBlockAt(world, blockPos)) {
                    world.setBlockState(blockPos, FOOTPRINT.getDefaultState());
                }
            }
        }
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {

    }

    @Override
    public void onDisable(EntityTameableDragon dragon) {

    }

    @Override
    public void onDeath(EntityTameableDragon dragon) {

    }

    @Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getBreathAffectedAreaIce().continueBreathing(world, origin, endOfLook, power, dragon);
        dragon.getBreathHelper().getBreathAffectedAreaIce().updateTick(world);
    }

    @Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforIceDragon(world, power, tickCounter);
    }

    private void doParticles(EntityTameableDragon dragon) {
        if (!dragon.isEgg() && !dragon.isBaby()) {
            float s = dragon.getScale() * 1.2f;
            double x = dragon.posX + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;
            double y = dragon.posY + (rand.nextDouble() - 0.5) * dragon.height * s;
            double z = dragon.posZ + (rand.nextDouble() - 0.5) * (dragon.width - 0.65) * s;

            dragon.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x, y, z, 0, 0, 0);
        }
    }

    @Override
    public void onLivingUpdate(EntityTameableDragon dragon) {
        if (dragon.isOverWater()) {
            freezeNearby(dragon, dragon.world, new BlockPos(dragon), 1);
        }

        World world = dragon.world;
        if (world instanceof WorldServer && !dragon.isDead && !dragon.isEgg()) {
            boolean isSnowy = BiomeDictionary.hasType(world.getBiome(dragon.getPosition()), BiomeDictionary.Type.SNOWY);
            if (isSnowy && dragon.posY > dragon.world.getHeight() * 1.25) doParticles(dragon);
        }
    }

    @Override
    public EnumParticleTypes getSneezeParticle() {
        return null;
    }

    public SoundEffectNames[] getBreathWeaponSoundEffects(DragonLifeStage stage) {
        final SoundEffectNames soundEffectNames[] = {SoundEffectNames.ADULT_BREATHE_ICE_START, SoundEffectNames.ADULT_BREATHE_ICE_LOOP, SoundEffectNames.ADULT_BREATHE_ICE_STOP};

        return soundEffectNames;

    }

}
