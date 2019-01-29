package com.TheRPGAdventurer.ROTD.server.entity.ai.air;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.ai.EntityAIDragonBase;

import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;

public class EntityAIFlyAround extends EntityAIDragonBase {

	public EntityAIFlyAround(EntityTameableDragon dragon) {
		super(dragon);
	}

	@Override
	public boolean shouldExecute() {
  if (dragon.getAttackingEntity() != null && dragon.getAttackTarget() != null) {
   return false;
 }
 if (!dragon.getMoveHelper().isUpdating()) {
   return true;
 }
		
		if(!dragon.isFlyingAround()) {
			return false;
		} 
	//else
		
		if(dragon.isTamed()) {
			if(dragon.hasHomePosition) {
			  return true;
			}
		} 
		
		
		EntityMoveHelper entitymovehelper = dragon.getMoveHelper();
		
  double d0 = entitymovehelper.getX() - dragon.posX;
  double d1 = entitymovehelper.getY() - dragon.posY;
  double d2 = entitymovehelper.getZ() - dragon.posZ;
  double d3 = d0 * d0 + d1 * d1 + d2 * d2;
  return (d3 < 1.0D) || (d3 > 3600.0D);
	}
	
	@Override
 public void startExecuting() {
 // if (dragon.isFlying() && dragon.getAttackTarget() == null) {
 //  dragon.flyAround();
 // }//else if (dragon.getAttackTarget() != null) {
 //   dragon.flyTowardsTarget();
 // }
	}
}