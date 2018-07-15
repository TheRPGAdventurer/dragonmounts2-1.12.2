package com.TheRPGAdventurer.ROTD.server.entity.ai.targeting;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.ai.EntityAIDragonBase;

import net.minecraft.entity.EntityLivingBase;

public class EntityAIBreathAttack extends EntityAIDragonBase {
	
	EntityLivingBase target = dragon.getAttackTarget();

	public EntityAIBreathAttack(EntityTameableDragon dragon) {
		super(dragon);
		
	}

	@Override
	public boolean shouldExecute() {
		return target != null;
	}
	
	@Override
	public void updateTask() {
		dragon.setBreathing(true);
		dragon.getLookHelper().setLookPositionWithEntity(target, dragon.getHeadYawSpeed(), dragon.getHeadPitchSpeed());
		
	}

}
