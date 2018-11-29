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
		} 
		return false;
	}
	
	@Override
	public void updateTask() {		
		if(dragon.isCircling()) {
			if(!followAboveThePlayer(dragon.getOwner().getPosition())) { 
				followAboveThePlayer(dragon.getOwner().getPosition());				
			}
		}		
	}
}
