package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.initialization.EnumItemBreedTypes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDragonEssence extends Item {

    public EnumItemBreedTypes type;
    public EnumDragonBreed breed;

    public ItemDragonEssence(EnumItemBreedTypes type, EnumDragonBreed breed) {
        this.breed = breed;
        this.setUnlocalizedName(type.toString().toLowerCase() + "_dragon_essence");
        this.setRegistryName(type.toString().toLowerCase() + "_dragon_essence");
//        this.setCreativeTab(DragonMounts.TAB);
        this.maxStackSize = 1;
        this.type = type;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(type.color + StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        stack.setTagCompound(new NBTTagCompound());
    }

    public void setDragonNBT(EntityTameableDragon dragon, ItemStack stack) {
        NBTTagCompound nbt;
        if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        } else {
            nbt = new NBTTagCompound();
        }

        stack.setTagCompound(nbt);

        if (dragon.hasCustomName()) {
            stack.setStackDisplayName(dragon.getCustomNameTag());
        }

        dragon.writeEntityToNBT(nbt);
    }

    /**
     * Thanks again Lex!
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        ItemStack stack = player.getHeldItem(hand);
        EntityTameableDragon dragon = new EntityTameableDragon(worldIn);

        dragon.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());


        if (stack.hasDisplayName()) {
            dragon.setCustomNameTag(stack.getDisplayName());
        }

        if (stack.getTagCompound() != null) {
            dragon.readEntityFromNBT(stack.getTagCompound());
        }
        stack.shrink(1);
        dragon.setBreedType(breed);
//        if (stack.getTagCompound() != null) {
//            stack.getTagCompound().setBoolean("Released", true);
            if (dragon.isTamedFor(player)) {
                if (!worldIn.isRemote) {
                    worldIn.spawnEntity(dragon);
                }
            } else {
                player.sendStatusMessage(new TextComponentTranslation("item.whistle.notOwned"), true);
            }
//        }

        stack = new ItemStack(this);
        dragon.world.playSound(x, y, z, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1, 1, false);
        dragon.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x + dragon.getRNG().nextInt(5), y + dragon.getRNG().nextInt(5), z + dragon.getRNG().nextInt(5), 1, 1, 1, 0);

        return super.onItemUse(player, worldIn, pos, hand, facing, x, y, z);

    }
}