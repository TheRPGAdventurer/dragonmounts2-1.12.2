package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
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
        this.setCreativeTab(DragonMounts.TAB);
        this.maxStackSize = 1;
        this.type = type;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(type.color + StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()));
    }

    public void setDragonNBT(EntityTameableDragon dragon, ItemStack stack) {
        NBTTagCompound nbt;
        if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        } else {
            nbt = new NBTTagCompound();
        }

        stack.setTagCompound(nbt);
        nbt.setUniqueId("DMessenceDragonId", dragon.getUniqueID());
        dragon.writeEntityToNBT(stack.getTagCompound());

        if (dragon.hasCustomName()) {
            stack.setStackDisplayName(dragon.getCustomNameTag());
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        EntityTameableDragon dragon = new EntityTameableDragon(world);

        dragon.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());

        if (stack.getTagCompound() != null) {
            dragon.readEntityFromNBT(stack.getTagCompound());
        }

        dragon.setBreedType(breed);
        dragon.setUniqueId(stack.getTagCompound().getUniqueId("DMessenceDragonId"));
        stack.getTagCompound().setBoolean("Released", true);
        if (!world.isRemote) {
            world.spawnEntity(dragon);
        }

        stack = new ItemStack(ModItems.AmuletEmpty);
        world.playSound(player, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1, 0.77f);

        return super.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
    }
}
