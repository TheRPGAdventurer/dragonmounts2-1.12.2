package com.TheRPGAdventurer.ROTD.server.entity.ai;

import java.security.acl.Owner;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonGui;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonWhistle;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityAIDragonWhistle extends EntityAIDragonBase
{
	MessageDragonWhistle message;
	
	public EntityAIDragonWhistle(EntityTameableDragon dragon)
	{
		super(dragon);
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		if(dragon.getOwner() == null) {
			return false;
		} else if(dragon.getControllingPlayer() != null) {
			return false;
		}
		return !dragon.nothing();
	}

	@Override
	public boolean shouldContinueExecuting() {
		return !dragon.isFlying() && dragon.getControllingPlayer() == null && !dragon.getNavigator().noPath() && !dragon.nothing();
	}

	private boolean circleFlag() {
		EntityMoveHelper entitymovehelper = dragon.getMoveHelper();

		double d0 = entitymovehelper.getX() - dragon.posX;
		double d1 = entitymovehelper.getY() - dragon.posY;
		double d2 = entitymovehelper.getZ() - dragon.posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		return (d3 < 1.0D) || (d3 > 3600.0D);
	}

	@Override
	public void startExecuting() {
		//Commands Requiring Flight - if any is true, start flying
		if(!dragon.isFlying() && (dragon.circle() || dragon.follow() || dragon.come()))
		{
			dragon.liftOff();
		}

		if(dragon.isFlying())
		{
			if(dragon.circle() && dragon.getOwner() != null && !dragon.circleTarget1(dragon.getOwner().getPosition()))
			{
				dragon.circleTarget1(dragon.getOwner().getPosition());
				dragon.setSitting(false);
			}
			else if(dragon.follow() && !dragon.followPlayerFlying(dragon.getOwner()) && dragon.getOwner() != null)
			{
				dragon.followPlayerFlying(dragon.getOwner());
				dragon.setSitting(false);
			}
			else if(dragon.come() && !dragon.comeToPlayerFlying(dragon.getOwner().getPosition(), dragon.getOwner()) && dragon.getOwner() != null)
			{
				dragon.comeToPlayerFlying(dragon.getOwner().getPosition(), dragon.getOwner());
				dragon.setSitting(false);
			}
			else if(dragon.homepos())
			{
				BlockPos pos = new BlockPos(dragon);
				dragon.homePos = pos;
				dragon.hasHomePosition = true;
				((EntityPlayer) dragon.getOwner()).sendStatusMessage(new TextComponentTranslation("dragon.command.new_home", dragon.homePos.getX(), dragon.homePos.getY(), dragon.homePos.getZ()), true);
			}
			else if(dragon.nothing())
			{
				return;
			}
			
		}
		else if(dragon.sit())
		{
			dragon.setWhistleState((byte) 0);
			dragon.setSitting(!dragon.isSitting());
//			dragon.getAISit().setSitting(!dragon.isSitting());
			dragon.getNavigator().clearPathEntity();
		}
		else
		{
			dragon.setWhistleState((byte) 0);
		}
		
	}
}
