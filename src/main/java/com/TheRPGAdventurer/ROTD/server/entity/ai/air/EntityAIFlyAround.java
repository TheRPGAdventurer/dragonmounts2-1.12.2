package com.TheRPGAdventurer.ROTD.server.entity.ai.air;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.ai.EntityAIDragonBase;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;

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
		
//		if(dragon.isTamed()) {
//			if(dragon.hasHomePosition) {
	//		  return true;
	//		}
//		} 
		
		
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
   Random random = dragon.getRNG();
   double d0 = dragon.posX + (random.nextFloat() * 2.0F - 1.0F) * 23.0F;
   double d1 = dragon.posY + (random.nextFloat() * 2.0F - 1.0F) * 12.0F;
   double d2 = dragon.posZ + (random.nextFloat() * 2.0F - 1.0F) * 23.0F;
   dragon.getMoveHelper().setMoveTo(d0, d1, d2, 5.0D);
		}
 }
	
}
