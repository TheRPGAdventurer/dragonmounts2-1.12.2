package com.TheRPGAdventurer.ROTD.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by TGG on 8/07/2015.
 * Performs a ray trace of the player's line of sight to see what the player is looking at.
 * Similar to the vanilla getMouseOver, which is client side only.
 */
public class RayTraceServer {
  /**
   * Find what the player is looking at (block or entity), up to a maximum range
   * based on code from EntityRenderer.getMouseOver
   * Will not target entities which are tamed by the player
   * @return the block or entity that the player is looking at / targeting with their cursor.  null if no collision
   */
  public static RayTraceResult getMouseOver(World world, EntityPlayer entityPlayerSP, double range) { // int range
    final float PARTIAL_TICK = 1.0F; 
    Vec3d positionEyes = entityPlayerSP.getPositionEyes(PARTIAL_TICK);
    Vec3d lookDirection = entityPlayerSP.getLook(PARTIAL_TICK);
    Vec3d endOfLook = positionEyes.add(lookDirection.x * range,
            lookDirection.y * range, 
            lookDirection.z * range);
    final boolean STOP_ON_LIQUID = true;
    final boolean IGNORE_BOUNDING_BOX = true;
    final boolean RETURN_NULL_IF_NO_COLLIDE = true;
    RayTraceResult targetedBlock = world.rayTraceBlocks(positionEyes, endOfLook,
            STOP_ON_LIQUID, !IGNORE_BOUNDING_BOX,
            !RETURN_NULL_IF_NO_COLLIDE);

    double collisionDistanceSQ = range * range;
    if (targetedBlock != null) {
      collisionDistanceSQ = targetedBlock.hitVec.squareDistanceTo(positionEyes);
      endOfLook = targetedBlock.hitVec;
    }

    final float EXPAND_SEARCH_BOX_BY = 1.0F;
    net.minecraft.util.math.AxisAlignedBB searchBox = entityPlayerSP.getEntityBoundingBox();
    Vec3d endOfLookDelta = endOfLook.subtract(positionEyes);
    searchBox = searchBox.expand(endOfLookDelta.x, endOfLookDelta.y, endOfLookDelta.z); //add
    searchBox = searchBox.grow(EXPAND_SEARCH_BOX_BY);
    List<Entity> nearbyEntities = world.getEntitiesWithinAABBExcludingEntity(
            entityPlayerSP, searchBox);
    Entity closestEntityHit = null;
    double closestEntityDistanceSQ = Double.MAX_VALUE;
    for (Entity entity : nearbyEntities) {
      if (!entity.canBeCollidedWith() || entity == entityPlayerSP.getRidingEntity()) {
        continue;
      }
      if (entity instanceof EntityTameable) {
        EntityTameable tamedEntity = (EntityTameable)entity;
        if (tamedEntity.isOwner(entityPlayerSP)) {
          continue;
        }
      }

      float collisionBorderSize = entity.getCollisionBorderSize();
      net.minecraft.util.math.AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox()
              .grow(collisionBorderSize);
      RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(positionEyes, endOfLook);

      if (axisalignedbb.contains(endOfLook)) {
        double distanceSQ = (movingobjectposition == null) ? positionEyes.squareDistanceTo(endOfLook)
                : positionEyes.squareDistanceTo(movingobjectposition.hitVec);
        if (distanceSQ <= closestEntityDistanceSQ) {
          closestEntityDistanceSQ = distanceSQ;
          closestEntityHit = entity;
        }
      } else if (movingobjectposition != null) {
        double distanceSQ = positionEyes.squareDistanceTo(movingobjectposition.hitVec);
        if (distanceSQ <= closestEntityDistanceSQ) {
          closestEntityDistanceSQ = distanceSQ;
          closestEntityHit = entity;
        }
      }
    }

    if (closestEntityDistanceSQ <= collisionDistanceSQ) {
      assert (closestEntityHit != null);
      return new RayTraceResult(closestEntityHit, closestEntityHit.getPositionVector());
    }
    return targetedBlock;
  }
}
