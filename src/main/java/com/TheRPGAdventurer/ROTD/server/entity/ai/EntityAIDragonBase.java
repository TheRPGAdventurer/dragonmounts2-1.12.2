/*
** 2016 MÃ¤rz 15
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.ai;

import static net.minecraft.entity.SharedMonsterAttributes.FOLLOW_RANGE;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public abstract class EntityAIDragonBase extends EntityAIBase {
    
    protected EntityTameableDragon dragon;
    protected World world;
    protected Random random;

    public EntityAIDragonBase(EntityTameableDragon dragon) {
        this.dragon = dragon;
        this.world = dragon.world;
        this.random = dragon.getRNG(); 
    }
    
    protected boolean tryMoveToBlockPos(BlockPos pos, double speed) {
        return dragon.getNavigator().tryMoveToXYZ(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, speed);
    }
    
	protected boolean tryToCircleBlockPos(BlockPos midPoint, double speed) {   	
    //	Vec3d vec1 = dragon.getPositionVector().subtract(midPoint.getX(),midPoint.getY(),midPoint.getZ());
    //	Vec3d vec2 = new Vec3d(0,0,1); // "calculate the angle between these 2 vectors" I hope vec3d is compat with Blockpos Vec3i
    	float a = 0;
    	double r = 12;  // radius is now a constant
    	while(a < Math.PI * 2) {
    		a += 1;
    	   double x = midPoint.getX() + Math.cos(a) * r * dragon.ticksExisted; // x = playerX +﻿ r * cos(a) as stated
           double y = midPoint.getY() + 20; // y = playerY + NUM as stated
           double z = midPoint.getZ() + Math.sin(a) * r * dragon.ticksExisted; //  z = playerZ + r * sin(a) as stated
           return dragon.getNavigator().tryMoveToXYZ(x + 0.5, y + 0.5, z + 0.5, speed);  	// no more adding    	     
        		
    	}
    	//double a = Math.acos((vec1.dotProduct(vec2)) / (vec1.lengthVector() * vec2.lengthVector())); 
		return false;
      }
	
	public boolean circleEntity(Entity target, float height, float radius, float speed, boolean direction, float offset, float moveSpeedMultiplier) {
        int directionInt = direction ? 1 : -1;
        return dragon.getNavigator().tryMoveToXYZ(target.posX + radius * Math.cos(directionInt * dragon.ticksExisted * 0.5 * speed / radius + offset), 
        		 height + target.posY, target.posZ + radius * Math.sin(directionInt * dragon.ticksExisted* 0.5 * speed / radius + offset), 
        		speed * moveSpeedMultiplier);
    }
	
	public boolean followAboveThePlayer(BlockPos playerPos) {
		double x = playerPos.getX();
		double y = playerPos.getY() + 12;
		double z = playerPos.getZ() + 14;
		return dragon.getNavigator().tryMoveToXYZ(x, y, z, 1.1);
	}

    
    protected double getFollowRange() {
        return dragon.getAttributeMap().getAttributeInstance(FOLLOW_RANGE).getAttributeValue();
    }
}
