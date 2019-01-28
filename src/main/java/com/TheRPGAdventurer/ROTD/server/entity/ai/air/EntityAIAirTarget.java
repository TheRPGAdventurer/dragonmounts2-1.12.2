package com.TheRPGAdventurer.ROTD.server.entity.ai.air;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.ai.EntityAIDragonBase;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIAirTarget extends EntityAIDragonBase {
	
	private com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon dragon;

	public EntityAIAirTarget(EntityTameableDragon dragon) {
		super(dragon);
	}

	public boolean shouldExecute() {
		if (dragon != null) {
			if (!dragon.isFlying() && dragon.onGround) {
				return false;
			}
		//	if (dragon.isSleeping()) {
		//		return false;
		//	}
			
			if (dragon.isHatchling()) {
				return false;
			}
			if (dragon.getControllingPlayer() != null) {
				return false;
			}
			if (dragon.airTarget != null && (dragon.isTargetBlocked(new Vec3d(dragon.airTarget)))) {
				dragon.airTarget = null;
			}

			if (dragon.airTarget != null) {
				return false;
			} else {
				BlockPos airTarget = getNearbyAirTarget();

				if (dragon.airTarget != null) {
					return false;
				} else {
					Vec3d vec = this.findAirTarget();

					if (vec == null) {
						return false;
					} else {
						dragon.airTarget = new BlockPos(vec.x, vec.y, vec.z);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean continueExecuting() {
		if (!dragon.isFlying()) {
			return false;
		}
	//	if (dragon.isSleeping()) {
////			return false;
	//	}
		if (!dragon.isHatchling()) {
			return false;
		}
		return dragon.airTarget != null;
	}
	
	public Vec3d findAirTarget() {
		return new Vec3d(getNearbyAirTarget());
	}

	public BlockPos getNearbyAirTarget() {
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