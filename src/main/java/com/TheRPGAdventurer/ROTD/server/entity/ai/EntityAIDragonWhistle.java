package com.TheRPGAdventurer.ROTD.server.entity.ai;

import java.security.acl.Owner;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

public class EntityAIDragonWhistle extends EntityAIDragonBase {

	public EntityAIDragonWhistle(EntityTameableDragon dragon) {
		super(dragon);
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if(dragon.getOwner() == null) {
			return false;
		} else if(dragon.getControllingPlayer() != null) {
			return false;
		}
		return !dragon.nothing();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return dragon.isFlying() && dragon.getControllingPlayer() == null && !dragon.getNavigator().noPath();
	}
	
	@Override
	public void startExecuting() {
		if(dragon.circle() || dragon.follow() || dragon.come()) {
			if(!dragon.isFlying()) {
				dragon.liftOff();
			}
		}

		if(dragon.isFlying()) {
			if(dragon.circle() && !dragon.circleTarget1(dragon.getOwner().getPosition()) && dragon.getOwner() != null) {
				dragon.circleTarget1(dragon.getOwner().getPosition());
				DMUtils.getLogger().info("dragon circle is being called");
			} else if(dragon.follow() && !dragon.followPlayerFlying(dragon.getOwner()) && dragon.getOwner() != null) {
				dragon.followPlayerFlying(dragon.getOwner());
			} else if(dragon.come() && !dragon.comeToPlayerFlying(dragon.getOwner().getPosition()) && dragon.getOwner() != null) {
				dragon.comeToPlayerFlying(dragon.getOwner().getPosition());
			}
		}
	}
}
