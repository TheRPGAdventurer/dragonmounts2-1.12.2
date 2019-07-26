/*
 ** 2012 March 18
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.util.math.MathX;
import com.TheRPGAdventurer.ROTD.util.reflection.PrivateAccessor;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;

/**
 * Abstract "AI" for player-controlled movements.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class EntityAIDragonPlayerControl extends EntityAIDragonBase implements PrivateAccessor {

    public EntityAIDragonPlayerControl(EntityTameableDragon dragon) {
        super(dragon);
        setMutexBits(0xffffffff);
    }

    @Override
    public boolean shouldExecute() {
        rider = dragon.getControllingPlayer();
        return rider != null;
    }

    @Override
    public void startExecuting() {
        dragon.getNavigator().clearPath();
    }

    @Override
    public void updateTask() {
        Vec3d wp = rider.getLook(1.0F);

        double x = dragon.posX;
        double y = dragon.posY;
        double z = dragon.posZ;

        if (dragon.getBreedType() == EnumDragonBreed.SYLPHID) {
            PotionEffect watereffect = new PotionEffect(MobEffects.WATER_BREATHING, 200);
            if (!rider.isPotionActive(watereffect.getPotion()) && rider.isInWater()) { // If the Potion isn't currently active,
                rider.addPotionEffect(watereffect); // Apply a copy of the PotionEffect to the player
            }
        }

        // if we're breathing at a target, look at it
        if ((dragon.isUsingBreathWeapon() && dragon.getBreed().canUseBreathWeapon())) {
            Vec3d dragonEyePos = dragon.getPositionVector().addVector(0, dragon.getEyeHeight(), 0);
            Vec3d lookDirection = rider.getLook(1.0F);
            Vec3d endOfLook = dragonEyePos.addVector(lookDirection.x, lookDirection.y, lookDirection.z);
            dragon.getLookHelper().setLookPosition(endOfLook.x, endOfLook.y, endOfLook.z,
                    90, 120);
            dragon.updateIntendedRideRotation(rider);
        }

        if (dragon.followYaw() && dragon.moveStrafing == 0) {
            dragon.updateIntendedRideRotation(rider);
        }

        if (dragon.isServerWorld()) {
            // control direction with movement keys
            if (rider.moveStrafing != 0 || rider.moveForward != 0) {
                if (rider.moveForward < 0) {
                    wp = wp.rotateYaw(MathX.PI_F);
                } else if (rider.moveStrafing > 0) {
                    wp = wp.rotateYaw(MathX.PI_F * 0.5f);
                } else if (rider.moveStrafing < 0) {
                    wp = wp.rotateYaw(MathX.PI_F * -0.5f);
                }

                x += wp.x * 10;
                if (!dragon.isYLocked()) y += wp.y * 10;
                z += wp.z * 10;
            }

//         lift off from a jump
            if (entityIsJumping(rider)) {
                if (!dragon.isFlying()) {
                    dragon.liftOff();
                } else {
                    y += 10;
                }
            } else if (dragon.isGoingDown()) {
                y -= 10;
            }

            dragon.getMoveHelper().setMoveTo(x, y, z, 1.2);
        }
    }
}