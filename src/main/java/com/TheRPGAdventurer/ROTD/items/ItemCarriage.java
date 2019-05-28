package com.TheRPGAdventurer.ROTD.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.entity.entitycarriage.EntityCarriage;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import com.TheRPGAdventurer.ROTD.util.StatCollector;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCarriage extends Item implements IHasModel {

    EntityCarriage.Type type;

    public ItemCarriage(String name, EntityCarriage.Type type) {
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name + type.getName()));
        this.setUnlocalizedName(name + type.getName());
        this.type=type;
        this.setMaxDamage(1);
        this.setMaxStackSize(16);
        this.setCreativeTab(DragonMounts.mainTab);

        ModItems.ITEMS.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbt=stack.getTagCompound();
        tooltip.add(TextFormatting.GREEN + StatCollector.translateToLocal("item.carriage.info"));
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack=playerIn.getHeldItem(handIn);
        float f=1.0F;
        float f1=playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * 1.0F;
        float f2=playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * 1.0F;
        double d0=playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * 1.0D;
        double d1=playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * 1.0D + (double) playerIn.getEyeHeight();
        double d2=playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * 1.0D;
        Vec3d vec3d=new Vec3d(d0, d1, d2);
        float f3=MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4=MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5=-MathHelper.cos(-f1 * 0.017453292F);
        float f6=MathHelper.sin(-f1 * 0.017453292F);
        float f7=f4 * f5;
        float f8=f3 * f5;
        double d3=5.0D;
        Vec3d vec3d1=vec3d.addVector((double) f7 * 5.0D, (double) f6 * 5.0D, (double) f8 * 5.0D);
        RayTraceResult raytraceresult=worldIn.rayTraceBlocks(vec3d, vec3d1, true);

        if (raytraceresult==null) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        } else {
            Vec3d vec3d2=playerIn.getLook(1.0F);
            boolean flag=false;
            List<Entity> list=worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().expand(vec3d2.x * 5.0D, vec3d2.y * 5.0D, vec3d2.z * 5.0D).grow(1.0D));

            for (int i=0; i < list.size(); ++i) {
                Entity entity=list.get(i);

                if (entity.canBeCollidedWith()) {
                    AxisAlignedBB axisalignedbb=entity.getEntityBoundingBox().grow((double) entity.getCollisionBorderSize());

                    if (axisalignedbb.contains(vec3d)) {
                        flag=true;
                    }
                }
            }

            if (flag) {
                return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
            } else if (raytraceresult.typeOfHit!=RayTraceResult.Type.BLOCK) {
                return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
            } else {
                Block block=worldIn.getBlockState(raytraceresult.getBlockPos()).getBlock();
                boolean flag1=block==Blocks.WATER || block==Blocks.FLOWING_WATER;
                EntityCarriage entitycarriage=new EntityCarriage(worldIn, raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
                entitycarriage.setType(this.type);
                entitycarriage.rotationYaw=playerIn.rotationYaw;

                if (!worldIn.getCollisionBoxes(entitycarriage, entitycarriage.getEntityBoundingBox().grow(-0.1D)).isEmpty()) {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                } else {
                    if (!worldIn.isRemote) {
                        worldIn.spawnEntity(entitycarriage);
                    }

                    if (!playerIn.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    playerIn.addStat(StatList.getObjectUseStats(this));
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                }
            }
        }
    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
    }
}