package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.weapons;

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathAffectedBlock;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathAffectedEntity;
import com.TheRPGAdventurer.ROTD.util.math.MathX;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by TGG on 5/08/2015.
 * <p>
 * Models the effects of a breathweapon on blocks and entities
 * Usage:
 * 1) Construct with a parent dragon
 * 2) affectBlock() to apply an area of effect to the given block (eg set fire to it)
 * 3) affectEntity() to apply an area of effect to the given entity (eg damage it)
 * <p>
 * Currently does fire only.  Intended to be subclassed later on for different weapon types.
 */
public class BreathWeapon {

    protected EntityTameableDragon dragon;

    protected float FIRE_DAMAGE = 1.5F;
    protected float ENDER_DAMAGE = 1.6F;
    protected float HYDRO_DAMAGE = 1.4F;
    protected float ICE_DAMAGE = 1.5F;
    protected float NETHER_DAMAGE = 1.6F;
    protected float POISON_DAMAGE = 1.3F;
    protected float WITHER_DAMAGE = 1.3F;

    public BreathWeapon(EntityTameableDragon i_dragon) {
        dragon = i_dragon;
    }

    /**
     * Used this to be compatible for Biomes O Plenty, BOP Author made a switch statement on his/her blocks
     * Instead of programming the blocks one by one. I dunno if that was allowed
     */
    public int processFlammability(Block block, World world, BlockPos sideToIgnite, EnumFacing facing) {
        int flammability = 0;
        try {
            return flammability = block.getFlammability(world, sideToIgnite, facing);
        } catch (IllegalArgumentException e) {
            return flammability = 3;
        }
    }

    /**
     * if the hitDensity is high enough, manipulate the block (eg set fire to it)
     *
     * @param world
     * @param blockPosition     the world [x,y,z] of the block
     * @param currentHitDensity
     * @return the updated block hit density
     */
    public BreathAffectedBlock affectBlock(World world, Vec3i blockPosition,
                                           BreathAffectedBlock currentHitDensity) {
        checkNotNull(world);
        checkNotNull(blockPosition);
        checkNotNull(currentHitDensity);

        BlockPos pos = new BlockPos(blockPosition);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        Random rand = new Random();

        // Flammable blocks: set fire to them once they have been exposed enough.  After sufficient exposure, destroy the
        //   block (otherwise -if it's raining, the burning block will keep going out)
        // Non-flammable blocks:
        // 1) liquids (except lava) evaporate
        // 2) If the block can be smelted (eg sand), then convert the block to the smelted version
        // 3) If the block can't be smelted then convert to lava

        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos sideToIgnite = pos.offset(facing);
            if (processFlammability(block, world, sideToIgnite, facing) > 0) {
                int flammability = processFlammability(block, world, sideToIgnite, facing);
                float thresholdForIgnition = convertFlammabilityToHitDensityThreshold(flammability);
                float thresholdForDestruction = thresholdForIgnition * 10;
                float densityOfThisFace = currentHitDensity.getHitDensity(facing);
                if (densityOfThisFace >= thresholdForIgnition && world.isAirBlock(sideToIgnite) && thresholdForIgnition != 0 && DragonMountsConfig.canFireBreathAffectBlocks) {
                    final float MIN_PITCH = 0.8F;
                    final float MAX_PITCH = 1.2F;
                    final float VOLUME = 1.0F;
                    world.playSound(sideToIgnite.getX() + 0.5, sideToIgnite.getY() + 0.5, sideToIgnite.getZ() + 0.5,
                            SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, VOLUME, MIN_PITCH + rand.nextFloat() * (MAX_PITCH - MIN_PITCH), false);
                    burnBlocks(sideToIgnite, rand, 22, world);

                    //    if (densityOfThisFace >= thresholdForDestruction && state.getBlockHardness(world, pos) != -1 && DragonMountsConfig.canFireBreathAffectBlocks) {
                    //   world.setBlockToAir(pos);
                }
            }
        }


        Block block1 = state.getBlock();
        Item itemFromBlock = Item.getItemFromBlock(block1);
        ItemStack itemStack;
        if (itemFromBlock != null && itemFromBlock.getHasSubtypes()) {
            int metadata = block1.getMetaFromState(state);
            itemStack = new ItemStack(itemFromBlock, 1, metadata);
        } else {
            itemStack = new ItemStack(itemFromBlock);
        }

        ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(itemStack);
        if (smeltingResult != null) {
            Block smeltedResultBlock = Block.getBlockFromItem(smeltingResult.getItem());
            if (smeltedResultBlock != null) {
                IBlockState iBlockStateSmelted = world.getBlockState(pos);
                iBlockStateSmelted = smeltedResultBlock.getStateFromMeta(smeltingResult.getMetadata());
            }
        }
        return new BreathAffectedBlock();  // reset to zero
    }

    protected void burnBlocks(BlockPos sideToIgnite, Random rand, int factor, World world) {
        if (rand.nextInt(2500) < factor)
            world.setBlockState(sideToIgnite, Blocks.FIRE.getDefaultState());
    }

    protected static class BlockBurnProperties {
        public IBlockState burnResult = null;  // null if no effect
        public float threshold;
    }

    /**
     * if sourceBlock can be smelted, return the smelting result as a block
     *
     * @param sourceBlock
     * @return the smelting result, or null if none
     */
    private static boolean getSmeltingResult(IBlockState sourceBlock, World world, BlockPos pos) {
        Block block = sourceBlock.getBlock();
        Item itemFromBlock = Item.getItemFromBlock(block);
        ItemStack itemStack;
        if (itemFromBlock != null && itemFromBlock.getHasSubtypes()) {
            int metadata = block.getMetaFromState(sourceBlock);
            itemStack = new ItemStack(itemFromBlock, 1, metadata);
        } else {
            itemStack = new ItemStack(itemFromBlock);
        }

        ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(itemStack);
        if (smeltingResult != null) {
            Block smeltedResultBlock = Block.getBlockFromItem(smeltingResult.getItem());
            if (smeltedResultBlock != null) {
                IBlockState iBlockStateSmelted = world.getBlockState(pos);
                return iBlockStateSmelted == smeltedResultBlock.getStateFromMeta(smeltingResult.getMetadata());
            }
        }
        return false;
    }

    /**
     * if sourceBlock is a liquid or snow that can be molten or vaporised, return the result as a block
     *
     * @param sourceBlock
     * @return the vaporised result, or null if none
     */
    private static boolean getVaporisedLiquidResult(IBlockState sourceBlock, World world, BlockPos pos) {
        Block block = sourceBlock.getBlock();
        Material material = block.getMaterial(sourceBlock);

        if (material == Material.WATER) {
            return world.setBlockState(pos, Blocks.AIR.getDefaultState());
        } else if (material == Material.SNOW || material == Material.ICE) {
            final int SMALL_LIQUID_AMOUNT = 4;
            return world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState().withProperty(BlockLiquid.LEVEL, SMALL_LIQUID_AMOUNT));
        } else if (material == Material.PACKED_ICE || material == Material.CRAFTED_SNOW) {
            final int LARGE_LIQUID_AMOUNT = 1;
            return world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState().withProperty(BlockLiquid.LEVEL, LARGE_LIQUID_AMOUNT));
        }
        return false;
    }

    /**
     * if sourceBlock is a block that can be melted to lave, return the result as a block
     *
     * @param sourceBlock
     * @return the molten lava result, or null if none
     */
    private static boolean getMoltenLavaResult(IBlockState sourceBlock, World world, BlockPos pos) {
        Block block = sourceBlock.getBlock();
        Material material = block.getMaterial(sourceBlock);

        if (material == Material.SAND || material == Material.CLAY
                || material == Material.GLASS || material == Material.IRON
                || material == Material.GROUND || material == Material.ROCK) {
            final int LARGE_LIQUID_AMOUNT = 1;
            return world.setBlockState(pos, Blocks.LAVA.getDefaultState().withProperty(BlockLiquid.LEVEL, LARGE_LIQUID_AMOUNT));
        }
        return false;
    }

    /**
     * if sourceBlock is a block that isn't flammable but can be scorched / changed, return the result as a block
     *
     * @param sourceBlock
     * @return the scorched result, or null if none
     */
    private static boolean getScorchedResult(IBlockState sourceBlock, World world, BlockPos pos) {
        Block block = sourceBlock.getBlock();
        Material material = block.getMaterial(sourceBlock);

        if (material == Material.GRASS) {
            return world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        }
        return false;
    }

    protected BreathAffectedEntity triggerExceptions(Entity entity, BreathAffectedEntity currentHitDensity) {
        if (entity == dragon.getRidingCarriage() && dragon.getRidingCarriage() != null) {
            if (dragon.getRidingCarriage().getRidingEntity() != null
                    && dragon.getRidingCarriage().getRidingEntity() == entity) {
                return null;
            }
        }
        return currentHitDensity;
    }

    protected BreathAffectedEntity triggerDamageExceptions(Entity entity, float DAMAGE_PER_HIT_DENSITY, Integer entityID, BreathAffectedEntity currentHitDensity) {
        if (entityID == dragon.getEntityId()) return null;

        if (entity == dragon.getRidingCarriage() && dragon.getRidingCarriage() != null) {
            if (dragon.getRidingCarriage().getRidingEntity() != null
                    && entity == dragon.getRidingCarriage().getRidingEntity()) {
                return null;
            }
        }

        if (entity instanceof EntityTameable) {
            EntityTameable entityTameable = (EntityTameable) entity;
            if (entityTameable.isTamed()) {
                return null;
            } else {
                entityTameable.attackEntityFrom(DamageSource.causeMobDamage(dragon), DAMAGE_PER_HIT_DENSITY);
            }
        }

        return currentHitDensity;

    }

    protected BreathAffectedEntity triggerDamageExceptionsForFire(Entity entity, Integer entityID, float DAMAGE_PER_HIT_DENSITY, BreathAffectedEntity currentHitDensity) {
        triggerDamageExceptions(entity, DAMAGE_PER_HIT_DENSITY, entityID, currentHitDensity);
        if (dragon.getControllingPlayer() != null && entity != dragon.getControllingPlayer()) {
            entity.setFire((4));
        } else if (entity instanceof EntityTameable) {
            EntityTameable entityTameable = (EntityTameable) entity;
            if (entityTameable.isTamed()) {
                entityTameable.attackEntityFrom(DamageSource.causeMobDamage(dragon), 0);
            }
        } else if (entity instanceof EntityLivingBase) {
            EntityLivingBase entity1 = (EntityLivingBase) entity;
            if (entity1.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
                return null;
            } else {
                entity1.attackEntityFrom(DamageSource.causeMobDamage(dragon), DAMAGE_PER_HIT_DENSITY);
            }
        } else if (dragon.isBeingRidden()) {
            if (dragon.isPassenger(entity)) return null;
        }

        return currentHitDensity;

    }

    private HashMap<Block, BlockBurnProperties> blockBurnPropertiesCache = new HashMap<Block, BlockBurnProperties>();

    /**
     * if the hitDensity is high enough, manipulate the entity (eg set fire to it, damage it)
     * A dragon can't be damaged by its own breathweapon;
     * If the "orbholder immune" option is on, and the entity is a player holding a dragon orb, ignore damage.
     *
     * @param world
     * @param entityID          the ID of the affected entity
     * @param currentHitDensity the hit density
     * @return the updated hit density; null if entity dead, doesn't exist, or otherwise not affected
     */
    public BreathAffectedEntity affectEntity(World world, Integer entityID, BreathAffectedEntity currentHitDensity) {
        checkNotNull(world);
        checkNotNull(entityID);
        checkNotNull(currentHitDensity);
        
        Entity entity = world.getEntityByID(entityID);
        if (entity == null || !(entity instanceof EntityLivingBase) || entity.isDead) return null;

        final float CATCH_FIRE_THRESHOLD = 1.4F;
        final float BURN_SECONDS_PER_HIT_DENSITY = 1.0F;
        float hitDensity = currentHitDensity.getHitDensity();
        final float DAMAGE_PER_HIT_DENSITY = FIRE_DAMAGE * hitDensity;
        MathX.clamp(hitDensity, 0, 2);

        this.xp(entity);

        triggerDamageExceptionsForFire(entity, entityID, DAMAGE_PER_HIT_DENSITY, currentHitDensity);
        entity.attackEntityFrom(DamageSource.causeMobDamage(dragon), DAMAGE_PER_HIT_DENSITY);

        return currentHitDensity;
    }

    protected void xp(Entity entity) {
//        try {
//            ReflectionHelper.setPrivateValue(EntityLivingBase.class, (EntityLivingBase) entity, 100,
//                    "recentlyHit", "field_70718_bc");
//        } catch (Exception ex) {
//            DMUtils.getLogger().warn("Can't override XP", ex);
//        }
    }

    /**
     * returns the hitDensity threshold for the given block flammability (0 - 300 as per Block.getFlammability)
     *
     * @param flammability
     * @return the hit density threshold above which the block catches fire
     */
    protected float convertFlammabilityToHitDensityThreshold(int flammability) {
        checkArgument(flammability >= 0 && flammability <= 300);
        if (flammability == 0) return Float.MAX_VALUE;
        // typical values for items are 5 (coal, logs), 20 (gates etc), 60 - 100 for leaves & flowers & grass
        // want: leaves & flowers to burn instantly; gates to take ~1 second at full power, coal / logs to take ~3 seconds
        // hitDensity of 1 is approximately 1-2 ticks of full exposure from a single beam, so 3 seconds is ~30

        float threshold = 15.0F / flammability;
        return threshold;
    }

}
