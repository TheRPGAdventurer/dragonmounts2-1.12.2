package com.TheRPGAdventurer.ROTD.server.entity.ai;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

public class EntityAIDragonFlyAround extends EntityAIDragonBase {

	public EntityAIDragonFlyAround(EntityTameableDragon dragon) {
		super(dragon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean shouldExecute() {
		if(dragon.isTamed()) {
			return false;
		}
		return doesWantToLand();
	}
	
	@Override
	public void updateTask() {
		// TODO Auto-generated method stub
		super.updateTask();
	}

}
