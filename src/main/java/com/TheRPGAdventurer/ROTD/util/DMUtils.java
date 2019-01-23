package com.TheRPGAdventurer.ROTD.util;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DMUtils {
	
	private static Logger logger;
	
	public static Logger getLogger() {
		if(logger == null) {
			logger = LogManager.getFormatterLogger(DragonMounts.MODID);
		}
		return logger;
	}
	
	/**
	 * taken from stackoverflow
	 * @param rnd
	 * @param min
	 * @param max
	 * @param exclude
	 * @return
	 */
	public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
	    int random = start + rnd.nextInt(end - start + 1 - exclude.length);
	    for (int ex : exclude) {
	        if (random < ex) {
	            break;
	        }
	        random++;
	    }
	    return random;
	}
	
	/**
	 * taken from stackoverflow
	 * @param rnd
	 * @param min
	 * @param max
	 * @param exclude
	 * @return
	 */
	public static int getRandomWithExclusionstatic(Random rnd, int start, int end, int... exclude) {
	    int random = start + rnd.nextInt(end - start + 1 - exclude.length);
	    for (int ex : exclude) {
	        if (random < ex) {
	            break;
	        }
	        random++;
	    }
	    return random;
	}
	
	public static BlockPos getBlockInView(EntityTameableDragon dragon) {
		float radius = 0.75F * (0.7F * 1.5f) * - 7 - dragon.getRNG().nextInt((int) ((dragon.getScale() + 2) * 6));
		float neg = dragon.getRNG().nextBoolean() ? 1 : -1;
		float renderYawOffset = dragon.renderYawOffset;
		if(dragon.hasHomePosition && dragon.homePos != null){
			BlockPos dragonPos = new BlockPos(dragon);
			BlockPos ground = dragon.world.getHeight(dragonPos);
			int distFromGround = (int) dragon.posY - ground.getY();
			for(int i = 0; i < 10; i++){
				BlockPos pos = new BlockPos(dragon.homePos.getX() + dragon.getRNG().nextInt(40) - 40, (distFromGround > 16 ? (int) Math.min(128, dragon.posY + dragon.getRNG().nextInt(16) - 8) : (int) dragon.posY + dragon.getRNG().nextInt(16) + 1), (dragon.homePos.getZ() + dragon.getRNG().nextInt(40 * 2) - 40));
				if (!dragon.isTargetBlocked(new Vec3d(pos)) && dragon.getDistanceSqToCenter(pos) > 6) {
					return pos;
				}
			}
		}
		float angle = (0.01745329251F * renderYawOffset) + 3.15F + (dragon.getRNG().nextFloat() * neg);

		double extraX = (double) (radius * MathHelper.sin((float) (Math.PI + angle)));
		double extraZ = (double) (radius * MathHelper.cos(angle));
		BlockPos radialPos = new BlockPos(dragon.posX + extraX, 0, dragon.posZ + extraZ);
		BlockPos ground = dragon.world.getHeight(radialPos);
		int distFromGround = (int) dragon.posY - ground.getY();
		BlockPos newPos = radialPos.up(distFromGround > 16 ? (int) Math.min(128, dragon.posY + dragon.getRNG().nextInt(16) - 8) : (int) dragon.posY + dragon.getRNG().nextInt(16) + 1);
		BlockPos pos = dragon.doesWantToLand() ? ground : newPos;
		if (!dragon.isTargetBlocked(new Vec3d(newPos)) && dragon.getDistanceSqToCenter(newPos) > 6) {
			return newPos;
		}
		return null;
	}
	
}
