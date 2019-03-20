package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemDragonAmulet extends Item {

    EnumItemBreedTypes type;
    EnumDragonBreed breed;
    ItemStack stack;
    public EntityAreaEffectCloud entityareaeffectcloud;

    public ItemDragonAmulet(@Nullable EnumItemBreedTypes type, @Nullable EnumDragonBreed breed) {
        String name = type.toString().toLowerCase() + "_dragon_amulet";
        this.type = type;
        this.breed = breed;
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name));
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(DragonMounts.TAB);
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                if (entityIn == null) {
                    return 0.0F;
                } else {
                    ItemStack itemstack = entityIn.getActiveItemStack();
                    return !itemstack.isEmpty() && itemstack.getItem() instanceof ItemDragonAmulet ? (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        this.stack = stack;
        stack.setTagCompound(new NBTTagCompound());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//        EntityTameableDragon dragon = new EntityTameableDragon((worldIn));
//        String stage = stack.getTagCompound().getString("stageString");
//        tooltip.add(type.color + StatCollector.translateToLocal(stage));
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        } else if (stack.getTagCompound().getBoolean("Released")) {
            stack.shrink(1);
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).inventory.setInventorySlotContents(itemSlot, new ItemStack(ModItems.AmuletEmpty));
            }
        }
    }

//    public EnumAction getItemUseAction(ItemStack stack) {
//        return EnumAction.BLOCK;
//    }

    /**
     * Thanks again Lex!
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        EntityTameableDragon dragon = new EntityTameableDragon(worldIn);

        dragon.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());

        if (stack.getTagCompound() != null) {
            dragon.readEntityFromNBT(stack.getTagCompound());
        }

        if (dragon.hasCustomName()) {
            stack.setStackDisplayName(dragon.getCustomNameTag());
        }

        dragon.setBreedType(breed);
        stack.getTagCompound().setBoolean("Released", true);
        if (!worldIn.isRemote && dragon.isTamedFor(player)) {
            worldIn.spawnEntity(dragon);
        }

        stack = new ItemStack(ModItems.AmuletEmpty);
        worldIn.playSound(player, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1, 0.77f);

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);

    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player, @javax.annotation.Nullable EnumHand hand) {
        IBlockState iblockstate = worldIn.getBlockState(target);

        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, target, iblockstate, stack, hand);
        if (hook != 0) return hook > 0;

        if (iblockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) iblockstate.getBlock();

            if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
                if (!worldIn.isRemote) {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate)) {
                        igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
                    }

//                    stack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target) {
        if (worldIn instanceof net.minecraft.world.WorldServer)
            return applyBonemeal(stack, worldIn, target, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.world.WorldServer) worldIn), null);
        return false;
    }

    public void applyEnchant(World world, BlockPos blockPos) {
        EntityDragon dragon1 = new EntityDragon(world);
        Random rand = new Random();
        if (!world.isRemote) {
            entityareaeffectcloud = new EntityAreaEffectCloud(world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            entityareaeffectcloud.setOwner(dragon1);
            entityareaeffectcloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
            entityareaeffectcloud.setRadius(1.6F);
            entityareaeffectcloud.setDuration(750);
            entityareaeffectcloud.setRadiusPerTick((1.0F - entityareaeffectcloud.getRadius()) / (float) entityareaeffectcloud.getDuration());
            entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.WITHER, 150, 1));

            entityareaeffectcloud.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            int i = rand.nextInt(10000);
            if (i < 10) {
                world.spawnEntity(entityareaeffectcloud);
            }
        }
    }

    public void getAmuletAbilities(World world, BlockPos pos, ItemStack stack) {
        switch (breed) {
            case AETHER:
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                break;
            case FIRE:
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                break;
            case FOREST:
                applyBonemeal(stack, world, pos);
                break;
            case SYLPHID:
                applyBonemeal(stack, world, pos);
                break;
            case ICE:
                applyBonemeal(stack, world, pos);
                break;
            case END:
                applyEnchant(world, pos);
                break;
            case NETHER:
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                break;
            case SKELETON:
                applyBonemeal(stack, world, pos);
                break;
            case WITHER:
                applyBonemeal(stack, world, pos);
                break;
            case ENCHANT:
                applyEnchant(world, pos);
                break;
            case SUNLIGHT:
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                break;
            case STORM:
                break;
            case ZOMBIE:
                applyBonemeal(stack, world, pos);
                break;
            case TERRA:
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                break;
            case MOONLIGHT:
                applyBonemeal(stack, world, pos);
                break;
        }
    }

}
