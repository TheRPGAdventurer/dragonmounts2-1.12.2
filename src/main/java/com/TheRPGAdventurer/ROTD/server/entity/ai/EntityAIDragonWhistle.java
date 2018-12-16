package com.TheRPGAdventurer.ROTD.server.entity.ai;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

public class EntityAIDragonWhistle extends EntityAIDragonBase {

	public EntityAIDragonWhistle(EntityTameableDragon dragon) {
		super(dragon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean shouldExecute() {
		if(dragon.getOwner() == null) {
			return false;
		} else if(dragon.isSitting()) {
			return false;
		}
		return false;
	}
	
	@Override
	public void updateTask() {		
//		if(dragon.isCircling()) {
//			dragon.setFlying(false);
		//	if(!dragon.isFlying()) {
		//		dragon.setFlying(true);
		//	}
		//	if(!circleEntity(dragon.getOwner(), 12f, 40f,  (float)dragon.getEntityAttribute(EntityTameableDragon.MOVEMENT_SPEED_AIR).getAttributeValue(),  true,  2,  2)) { 
		//		circleEntity(dragon.getOwner(), 12f, 40f,  (float)dragon.getEntityAttribute(EntityTameableDragon.MOVEMENT_SPEED_AIR).getAttributeValue(),  true,  2,  2);
	    //    }
//		}		
	}
}
