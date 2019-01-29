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
 
 if(dragon.world.provider.getDimensionType() == DimensionType.NETHER) {
 	return false;
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
  if(dragon.airPoint != null && dragon.airPoint.getY() > 128){
  	 dragon.airPoint = new BlockPos(dragon.airPoint.getX(), 128, dragon.airPoint.getZ());
  } 
  
		if(dragon.isFlyingAround() && dragon.airPoint != null && dragon.isTargetInAir() 
				&& dragon.getDistanceSquared(new Vec3d(dragon.airPoint.getX(), dragon.posY, dragon.airPoint.getZ())) > 3) {
			
			double radius = 12;
			float neg = dragon.getRNG().nextBoolean() ? 1 : -1;
			float renderYawOffset = dragon.renderYawOffset;
			float angle = (0.01745329251F * renderYawOffset) + 3.15F + (dragon.getRNG().nextFloat() * neg);
			
			double extraX = (double) (radius * MathHelper.sin((float) (Math.PI + angle)));
			double extraZ = (double) (radius * MathHelper.cos(angle));
			BlockPos radialPos = new BlockPos(dragon.posX + extraX, 0, dragon.posZ + extraZ);
			BlockPos ground = dragon.world.getHeight(radialPos);
//   Random random = dragon.getRNG();
   double d0 = dragon.posX + (random.nextFloat() * 2.0F - 1.0F) * 20.0F;
   double d1 = ground.getY() + 60; 
   double d2 = dragon.posZ + (random.nextFloat() * 2.0F - 1.0F) * 20.0F;
   dragon.getMoveHelper().setMoveTo(d0, d1, d2, 5.0D);
		}
 }
}
