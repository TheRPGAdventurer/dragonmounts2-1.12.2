/*
** 2016 March 13
**
** The author disclaims copyright to entity source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.helper;

import static net.minecraft.entity.SharedMonsterAttributes.MOVEMENT_SPEED;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonMoveHelper extends EntityMoveHelper {

    private final EntityTameableDragon dragon;
    private final float YAW_SPEED = 10;
    private Entity ent;
    
    public DragonMoveHelper(EntityTameableDragon dragon) {
        super(dragon);
        this.dragon = dragon;
    }

    @Override
    public void onUpdateMoveHelper() {  
        double d0 = this.posX - this.dragon.posX;
        double d1 = this.posY - this.dragon.posY;
        double d2 = this.posZ - this.dragon.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        
        // original movement behavior if the entity isn't flying
        if (!dragon.isFlying()) {
            super.onUpdateMoveHelper();
            return; 
        } 
        
        Vec3d dragonPos = dragon.getPositionVector();
        Vec3d movePos = new Vec3d(posX, posY, posZ);
        
        // get direction vector by subtracting the current position from the
        // target position and normalizing the result
        Vec3d dir = movePos.subtract(dragonPos).normalize();
        
        // get euclidean distance to target
        double dist = dragonPos.distanceTo(movePos);
        double boost = dragon.boosting() ? 3D : 1;
        double flySpeed = dragon.getEntityAttribute(EntityTameableDragon.MOVEMENT_SPEED_AIR).getAttributeValue() * boost;
        
        // move towards target if it's far away enough
        if (dist > dragon.width) {            
            // update velocity to approach target
            dragon.motionX = dir.x * flySpeed;
            dragon.motionY = dir.y * flySpeed;
            dragon.motionZ = dir.z * flySpeed;
        } else {
            // just slow down and hover at current location
            dragon.motionX *= 0.8;
            dragon.motionY *= 0.8;
            dragon.motionZ *= 0.8;
            
            dragon.motionY += Math.sin(dragon.ticksExisted / 5) * 0.03;
        }
        
        // face entity towards target
        if (dist > 2.5E-7) {
            float newYaw = (float) Math.toDegrees(Math.PI * 2 - Math.atan2(dir.x, dir.z));
            dragon.rotationYaw = limitAngle(dragon.rotationYaw, newYaw, YAW_SPEED);
            entity.setAIMoveSpeed((float)(speed * entity.getEntityAttribute(MOVEMENT_SPEED).getAttributeValue()));
         }                     
        
        // apply movement               
        dragon.move(MoverType.SELF, dragon.motionX, dragon.motionY, dragon.motionZ);
    } 
}
