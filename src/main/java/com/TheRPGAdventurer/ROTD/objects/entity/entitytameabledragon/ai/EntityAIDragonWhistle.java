package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIDragonWhistle extends EntityAIDragonBase {

    public EntityAIDragonWhistle(EntityTameableDragon dragon) {
        super(dragon);
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        return dragon.getOwner() != null && dragon.getControllingPlayer() == null;
    }

    @Override
    public void updateTask() {
        if (dragon.firesupport() && dragon.getOwner() != null && dragon.isUsingBreathWeapon()) {
            dragon.getNavigator().clearPath();
            Vec3d dragonEyePos = dragon.getPositionVector().addVector(0, dragon.getEyeHeight(), 0);
            Vec3d lookDirection = dragon.getOwner().getLook(1.0F);
            Vec3d endOfLook = dragonEyePos.addVector(lookDirection.x, lookDirection.y, lookDirection.z);
            dragon.getLookHelper().setLookPosition(endOfLook.x, endOfLook.y, endOfLook.z,
                    90, 120);
            if (dragon.getOwner() instanceof EntityPlayer)
                dragon.updateIntendedRideRotation((EntityPlayer) dragon.getOwner());
        }
    }

    public boolean followPlayerFlying(EntityLivingBase entityLivingBase) {
        BlockPos midPoint = entityLivingBase.getPosition();
        double x = midPoint.getX() + 0.5 - 12;
        double y = midPoint.getY() + 0.5 + 24;
        double z = midPoint.getZ() + 0.5 - 12;
        return dragon.getNavigator().tryMoveToXYZ(x, y, z, 2);
    }


    public boolean circleTarget1(BlockPos midPoint) {
        if (dragon.getControllingPlayer() != null) {
            return false;
        }

        Vec3d vec1 = dragon.getPositionVector().subtract(midPoint.getX(), midPoint.getY(), midPoint.getZ());
        Vec3d vec2 = new Vec3d(0, 0, 1);

        int directionInt = dragon.getRNG().nextInt(450) == 1 ? 1 : -1;
        double a = Math.acos((vec1.dotProduct(vec2)) / (vec1.lengthVector() * vec2.lengthVector()));
        double r = 0.9 * 30;  // DragonMountsConfig.dragonFlightHeight
        double x = midPoint.getX() + r * Math.cos(directionInt * a * dragon.ticksExisted * 3.5);
        double y = midPoint.getY() + 45 + 0.5; // DragonMountsConfig.dragonFlightHeight
        double z = midPoint.getZ() + r * Math.sin(directionInt * a * dragon.ticksExisted * 3.5);

        return dragon.getNavigator().tryMoveToXYZ(x + 0.5, y + 0.5, z + 0.5, 1);
    }

    @Override
    public void startExecuting() {
        //Commands Requiring Flight - if any is true, start flying
        if (!dragon.isFlying() && (dragon.circle() || dragon.follow())) {
            dragon.setFlying(true);;
            dragon.setSitting(false);
            dragon.liftOff();
        }

        if (dragon.nothing()) {
            dragon.setnothing(true);
        }

        if (dragon.isFlying()) {
            if (dragon.circle() && dragon.getOwner() != null && !this.circleTarget1(dragon.getOwner().getPosition())) {
                this.circleTarget1(dragon.getOwner().getPosition());

            }
            if (dragon.follow() && !this.followPlayerFlying(dragon.getOwner()) && dragon.getOwner() != null) {
                this.followPlayerFlying(dragon.getOwner());

            }
        }
    }
}

//    public boolean circleTarget2(BlockPos target, float height, float radius, float speed, boolean direction, float offset, float moveSpeedMultiplier) {
//        int directionInt = direction ? 1 : -1;
//        return dragon.getNavigator().tryMoveToXYZ(
//                target.getX() + radius * Math.cos(directionInt * dragon.ticksExisted * 0.5 * speed / radius + offset),
//                30 + target.getY(), // DragonMountsConfig.dragonFlightHeight
//                target.getZ() + radius * Math.sin(directionInt * dragon.ticksExisted * 0.5 * speed / radius + offset),
//                speed * moveSpeedMultiplier);
//    }