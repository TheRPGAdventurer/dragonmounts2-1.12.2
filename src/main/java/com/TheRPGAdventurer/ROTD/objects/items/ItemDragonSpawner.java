package com.TheRPGAdventurer.ROTD.objects.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.DragonLifeStage;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDragonSpawner extends Item implements IHasModel {

    private EnumDragonBreed breed;
    private EnumItemBreedTypes type;

    public ItemDragonSpawner(EnumItemBreedTypes type, EnumDragonBreed breed, CreativeTabs tAB) {
        String unlocalizedName=type.toString().toLowerCase();
        this.setUnlocalizedName("summon_egg"); //  ItemMonsterPlacer
        this.setRegistryName("summon_" + unlocalizedName);
        this.setMaxStackSize(1);
        this.setCreativeTab(tAB);
        this.breed=breed;
        this.type=type;

        ModItems.ITEMS.add(this);
    }

    public EntityTameableDragon spawnEntityTameableDragon(World world, EntityPlayer player, ItemStack stack, double x, double y, double z) {
        EntityTameableDragon dragon=new EntityTameableDragon(world);
        try {
            if (player.isSneaking()) {
                dragon.getLifeStageHelper().setLifeStage(DragonLifeStage.HATCHLING);
            } else {
                dragon.getLifeStageHelper().setLifeStage(DragonLifeStage.ADULT);
            }

            dragon.setPosition(x + 0.5, y + 0.5, z + 0.5);
            dragon.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
            dragon.rotationYawHead=dragon.rotationYaw;
            dragon.renderYawOffset=dragon.rotationYaw;
            dragon.setBreedType(breed);
            dragon.heal(200);
            return dragon;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack=player.getHeldItem(hand);
        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        } else if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
            return EnumActionResult.PASS;
        } else {
            IBlockState state=world.getBlockState(pos);

            pos=pos.offset(side);
            double yOffset=0.0D;

            if (side==EnumFacing.UP && state.getBlock() instanceof BlockFence) {
                yOffset=0.5D;
            }

            EntityTameableDragon dragon=this.spawnEntityTameableDragon(world, player, stack, pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);

            if (dragon!=null) {
                if (stack.hasDisplayName()) {
                    dragon.setCustomNameTag(stack.getDisplayName());
                }
                //    no just no!
                if (!player.capabilities.isCreativeMode) {
                    stack.shrink(1);
                }

                world.spawnEntity(dragon);
                dragon.playLivingSound();
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(type.color + DMUtils.translateToLocal("dragon." + type.toString().toLowerCase()));
    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
