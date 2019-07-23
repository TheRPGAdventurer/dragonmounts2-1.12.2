package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIDragonWhistle extends EntityAIDragonBase {

    public EntityAIDragonWhistle(EntityTameableDragon dragon) {
        super(dragon);
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        return dragon.getOwner() != null && dragon.getControllingPlayer() == null && !dragon.nowhistlecommands();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return dragon.getControllingPlayer() == null && !dragon.nowhistlecommands();
    }

    @Override
    public void updateTask() {
        ItemStack whistle = dragon.getControllingWhistle();
        dragon.setnowhistlecommands(whistle != null && whistle.getTagCompound() != null && !whistle.getTagCompound().getUniqueId(DragonMounts.MODID + "dragon").equals(dragon.getUniqueID()) && whistle.hasTagCompound()
                || whistle == null);

        if (dragon.firesupport() && dragon.getOwner() != null && dragon.isUsingBreathWeapon()) {
            Vec3d dragonEyePos = dragon.getPositionVector().addVector(0, dragon.getEyeHeight(), 0);
            Vec3d lookDirection = dragon.getOwner().getLook(1.0F);
            Vec3d endOfLook = dragonEyePos.addVector(lookDirection.x, lookDirection.y, lookDirection.z);
            dragon.getLookHelper().setLookPosition(endOfLook.x, endOfLook.y, endOfLook.z,
                    90, 120);
            if (dragon.getOwner() instanceof EntityPlayer)
                dragon.updateIntendedRideRotation((EntityPlayer) dragon.getOwner());
        }
        dragon.setnowhistlecommands(dragon.getControllingPlayer() != null);
        if(dragon.getControllingPlayer()!=null) dragon.setfiresupport(false);
    }

    public boolean followPlayerFlying(BlockPos midPoint) {
        double x = midPoint.getX() + 12;
        double y = midPoint.getY() + 25;
        double z = midPoint.getZ() - 12;
        dragon.setBoosting(dragon.getDistance(dragon.getOwner()) > 55);

        return tryMoveToBlockPos(new BlockPos(x, y, z), 1);
    }


    public boolean circleTarget1(BlockPos midPoint) {
        Vec3d vec1 = dragon.getPositionVector().subtract(midPoint.getX(), midPoint.getY(), midPoint.getZ());
        Vec3d vec2 = new Vec3d(0, 0, 1);

        double a = Math.acos((vec1.dotProduct(vec2)) / (vec1.lengthVector() * vec2.lengthVector()));
        double r = 30;
        double x = midPoint.getX() + r * Math.cos(a * dragon.ticksExisted * 4);
        double y = midPoint.getY() + 30;
        double z = midPoint.getZ() + r * Math.sin(a * dragon.ticksExisted * 4);
        dragon.setBoosting(dragon.getDistance(dragon.getOwner()) > 77);

        return tryMoveToBlockPos(new BlockPos(x, y, z), 1);
    }

    @Override
    public void startExecuting() {
        //Commands Requiring Flight - if any is true, start flying
        if (!dragon.isFlying() && (dragon.circle() || dragon.follow())) {
            dragon.liftOff();
        }

        if (dragon.circle() && !this.circleTarget1(dragon.getOwner().getPosition()))
            this.circleTarget1(dragon.getOwner().getPosition());
        if (dragon.follow() && !this.followPlayerFlying(dragon.getOwner().getPosition()))
            this.followPlayerFlying(dragon.getOwner().getPosition());

    }
}