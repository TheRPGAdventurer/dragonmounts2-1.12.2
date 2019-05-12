package com.TheRPGAdventurer.ROTD.entity.ai.air;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.ai.EntityAIDragonBase;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIAirPoint extends EntityAIDragonBase {
	
	private com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon dragon;

	public EntityAIAirPoint(EntityTameableDragon dragon) {
		super(dragon);
	}

	public boolean shouldExecute() {
		if (dragon != null) {
	//		if (!dragon.isFlying()) {
	//			return false;
	//		}
	//		if (dragon.isSleeping()) {
	//			return false;
	//		}
			if (dragon.isChild()) {
				return false;
			}
			if (dragon.getOwner() != null && dragon.getPassengers().contains(dragon.getOwner())) {
				return false;
			}
			if (dragon.airPoint != null && (dragon.isTargetBlocked(new Vec3d(dragon.airPoint)))) {
				dragon.airPoint = null;
			}

			if (dragon.airPoint != null) {
				return false;
			} else {
				Vec3d vec = this.findAirPoint();

				if (vec == null) {
					return false;
				} else {
					dragon.airPoint = new BlockPos(vec.x, vec.y, vec.z);
					return true;
				}
			}
		}
		return false;
	}

	public boolean continueExecuting() {
	//	if (!dragon.isFlying()) {
	//		return false;
//		}
	////	if (dragon.isSleeping()) {
	///		return false;
	//	}
		if (dragon.isChild()) {
			return false;
		}
		return dragon.airPoint != null;
	}
	
	public Vec3d findAirPoint() {
		return new Vec3d(getAirPoint());
	}

	public BlockPos getAirPoint() {
		if (dragon.getAttackTarget() == null) {
			BlockPos pos = DMUtils.getBlockInView(dragon);
			if (pos != null && dragon.world.getBlockState(pos).getMaterial() == Material.AIR) {
				return pos;
			}
		} //else {
			//return new BlockPos((int) dragon.getAttackTarget().posX, (int) dragon.getAttackTarget().posY, (int) dragon.getAttackTarget().posZ);
//		}
		return dragon.getPosition();
	}


}