package com.TheRPGAdventurer.ROTD.client.items;

import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

        if (dragon.hasCustomName()) {
            stack.setStackDisplayName(dragon.getCustomNameTag());
        }

        if (stack.getTagCompound() != null) {
            nbt.setUniqueId("DMessenceDragonId", dragon.getUniqueID());
            nbt.setUniqueId("DMessenceOwnerId", dragon.getOwnerId());
            nbt.setInteger("DMstage", dragon.getStageInt());
            nbt.setBoolean("DMgender", dragon.isMale());
        }
    }

    public EntityTameableDragon spawnEntityTameableDragon(World world, EntityPlayer player, ItemStack stack, double x, double y, double z) {
        NBTTagCompound nbt;
        if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        } else {
            nbt = new NBTTagCompound();
        }

        stack.setTagCompound(nbt);

        EntityTameableDragon dragon = new EntityTameableDragon(world);

        try {

            if (nbt.hasUniqueId("DMessenceDragonId")) {
                dragon.setUniqueId(nbt.getUniqueId("DMessenceDragonId"));
                if (nbt.hasUniqueId("DMessenceOwnerId"))
                    dragon.tamedFor(world.getPlayerEntityByUUID(nbt.getUniqueId("DMessenceOwnerId")), true);
                dragon.setBreedType(breed);
                dragon.setMale(nbt.getBoolean("DMgender"));
                dragon.setLifeStageInt(nbt.getInteger("DMstage"));

                dragon.rotationYawHead = dragon.rotationYaw;
                dragon.renderYawOffset = dragon.rotationYaw;

                dragon.setPosition(x, y, z);
                dragon.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);

                world.playSound(x, y, z, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1, 1, false);
                world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x + dragon.getRNG().nextInt(5), y + dragon.getRNG().nextInt(5), z + dragon.getRNG().nextInt(5), 1, 1, 1, 0);

                return dragon;
            } else {
                DMUtils.getLogger().error("no NBT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        } else if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
            return EnumActionResult.FAIL;
        } else {
            IBlockState state = world.getBlockState(pos);

            pos = pos.offset(side);
            double yOffset = 0.0D;

            if (side == EnumFacing.UP && state.getBlock() instanceof BlockFence) {
                yOffset = 0.5D;
            }

            EntityTameableDragon dragon = this.spawnEntityTameableDragon(world, player, stack, pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);

            if (dragon != null) {
                if (player == dragon.getOwner()) {
                    if (stack.hasDisplayName()) {
                        dragon.setCustomNameTag(stack.getDisplayName());
                    }
                    //    no just no!
                    if (!player.capabilities.isCreativeMode) {
                        stack.shrink(1);
                    }
                } else {
                    player.sendStatusMessage(new TextComponentTranslation("item.whistle.notOwned", new Object[0]), true);
                }

                world.spawnEntity(dragon);
            }
        }
        return EnumActionResult.SUCCESS;
    }
}
