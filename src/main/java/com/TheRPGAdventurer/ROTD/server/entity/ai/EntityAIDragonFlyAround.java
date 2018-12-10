package com.TheRPGAdventurer.ROTD.server.entity.ai;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIDragonFlyAround extends EntityAIDragonBase {
	private World theWorld;

	public EntityAIDragonFlyAround(EntityTameableDragon dragon) {
		super(dragon);
		this.theWorld = dragon.world;
	}

	public boolean shouldExecute() {
		if (dragon != null) {
			if (!dragon.isFlying() || dragon.onGround) {
				return false;
			}
		//	if (dragon.isSleeping()) {
		//		return false;
		//	}
			if (dragon.isChild()) {
				return false;
			}
			if (dragon.getOwner() != null && dragon.getPassengers().contains(dragon.getOwner())) {
				return false;
			}
			if (dragon.airPosTarget != null && (dragon.isTargetBlocked(new Vec3d(dragon.airPosTarget)))) {
				dragon.airPosTarget = null;
			}

			if (dragon.airPosTarget != null) {
				return false;
			} else {
				Vec3d vec = this.findAirTarget();

				if (vec == null) {
					return false;
				} else {
					dragon.airPosTarget = new BlockPos(vec.x, vec.y, vec.z);
					return true;
				}
			}
		}
		return false;
	}

	public boolean continueExecuting() {
	//	StoneEntityProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(dragon, StoneEntityProperties.class);
		if (!dragon.isFlying()) {
			return false;
		}
	//	if (dragon.isSleeping()) {
	//		return false;
	//	}
		if (dragon.isChild()) {
			return false;
		}
	//	if (properties != null && properties.isStone) {
	//		return false;
	//	}
		return dragon.airPosTarget != null;
	}

	public Vec3d findAirTarget() {
		return new Vec3d(getNearbyAirTarget());
	}

	public BlockPos getNearbyAirTarget() {
		if (dragon.getAttackTarget() == null) {
			BlockPos view = dragon.getBlockInView(dragon);
			if (view != null && dragon.world.getBlockState(view).getMaterial() == Material.AIR) {
				return view;
			}
		} else {
			return new BlockPos((int) dragon.getAttackTarget().posX, (int) dragon.getAttackTarget().posY, (int) dragon.getAttackTarget().posZ);
		}
		return dragon.getPosition();
	}

}