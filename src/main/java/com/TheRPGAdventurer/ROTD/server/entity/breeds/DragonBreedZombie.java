package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

public class DragonBreedZombie extends DragonBreed {

	DragonBreedZombie() {
		super("zombie", 0Xb6d035);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEnable(EntityTameableDragon dragon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisable(EntityTameableDragon dragon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeath(EntityTameableDragon dragon) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public boolean isInfertile() {
		return true;
	}

}
