package com.TheRPGAdventurer.ROTD.server.entity.ai.air;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.ai.EntityAIDragonBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

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
		if(dragon.isFlyingAround()) {
			
			double radius = 12;
			float neg = dragon.getRNG().nextBoolean() ? 1 : -1;
			float renderYawOffset = dragon.renderYawOffset;
			float angle = (0.01745329251F * renderYawOffset) + 3.15F + (dragon.getRNG().nextFloat() * neg);
			
			double extraX = (double) (radius * MathHelper.sin((float) (Math.PI + angle)));
			double extraZ = (double) (radius * MathHelper.cos(angle));
			BlockPos radialPos = new BlockPos(dragon.posX + extraX, 0, dragon.posZ + extraZ);
			BlockPos ground = dragon.world.getHeight(radialPos);
   Random random = dragon.getRNG();
   double d0 = dragon.posX + (random.nextFloat() * 2.5F - 1.0F) * 17.0F;
   double d1 = ground.getY() + 40; 
   double d2 = dragon.posZ + (random.nextFloat() * 2.5F - 1.0F) * 17.0F;
   dragon.getMoveHelper().setMoveTo(d0, d1, d2, 5.0D);
		}
 }
}
