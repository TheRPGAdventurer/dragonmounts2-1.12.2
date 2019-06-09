package com.TheRPGAdventurer.ROTD.client.render.dragon.breathweaponFX;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.DragonBreathMode;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.nodes.BreathNodeP;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by TGG on 21/06/2015.
 * Used to spawn breath particles on the client side
 * Usage:
 * Each tick:
 * (1) setBeamEndpoints() to set the current beam origin and destination
 * (2) spawnBreathParticles() to spawn the particles()
 */
public abstract class BreathWeaponFXEmitter
{

  protected Vec3d origin;
  protected Vec3d direction;

  protected Vec3d previousOrigin;
  protected Vec3d previousDirection;
  protected int previousTickCount;

  /**
   * Set the current beam origin and target destination (used to calculate direction).
   * Will smooth out between ticks.
   * @param newOrigin the starting point of the beam (world coordinates)
   * @param newDestination the target of the beam (world coordinates)
   */
  public void setBeamEndpoints(Vec3d newOrigin, Vec3d newDestination)
  {
    origin = newOrigin;
    direction = newDestination.subtract(newOrigin).normalize();
  }

  /**
   * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
   *   the beam stopped for a while (tickCount skipped one or more tick)
   * @param world
   * @param power the strength of the beam
   * @param tickCount
   */
  abstract public void spawnBreathParticles(World world, BreathNodeP.Power power, int tickCount);

  public void changeBreathMode(DragonBreathMode newDragonBreathMode)
  {
    dragonBreathMode = newDragonBreathMode;
    for (BreathFX breathFX : spawnedBreathFX) {
      breathFX.updateBreathMode(dragonBreathMode);
    }
  }


  /**
   * Spawn a number of EntityFX, interpolating between the direction at the previous tick and the direction of the current tick
   * Useful for breath weapons consisting of many particles, such as Fire.
   * @param world
   * @param power
   * @param particlesPerTick number of particles to spawn
   * @param tickCount running count of the number of ticks the game has performed
   */
  protected void spawnMultipleWithSmoothedDirection(World world, BreathNodeP.Power power, int particlesPerTick, int tickCount)
  {
    // create a list of NodeLineSegments from the motion path of the BreathNodes
    Iterator<BreathFX> it = spawnedBreathFX.iterator();
    boolean foundLive = false;
    while (it.hasNext() && !foundLive) {
      BreathFX entity = it.next();
      if (!entity.isAlive()) {
        it.remove();
      } else {
        foundLive = true;
      }
    }
    final int MAX_SPAWNED_SIZE = 1000;
    if (spawnedBreathFX.size() > MAX_SPAWNED_SIZE) {  // prevent leak in case EntityFX is never set to dead for some reason
      spawnedBreathFX.clear();
    }

    Random random = new Random();
    if (tickCount != previousTickCount + 1) {
      previousDirection = direction;
      previousOrigin = origin;
    } else {
      if (previousDirection == null) previousDirection = direction;
      if (previousOrigin == null) previousOrigin = origin;
    }
    for (int i = 0; i < particlesPerTick; ++i) {
      float partialTickHeadStart = (i + random.nextFloat()) / (float)particlesPerTick;   // random is for jitter to prevent aliasing
      Vec3d interpDirection = interpolateVec(previousDirection, direction, partialTickHeadStart);
      Vec3d interpOrigin = interpolateVec(previousOrigin, origin, partialTickHeadStart);
      BreathFX breathFX = createSingleParticle(world, interpOrigin, interpDirection, power, tickCount, partialTickHeadStart);
      Minecraft.getMinecraft().effectRenderer.addEffect(breathFX);
      spawnedBreathFX.add(breathFX);
    }
    previousDirection = direction;
    previousOrigin = origin;
    previousTickCount = tickCount;
  }


  protected abstract BreathFX createSingleParticle(World world, Vec3d origin, Vec3d direction, BreathNodeP.Power power,
                                                   int tickCount, float partialTickHeadStart);

  /**
   * interpolate from vector 1 to vector 2 using fraction
   * @param vector1
   * @param vector2
   * @param fraction 0 - 1; 0 = vector1, 1 = vector2
   * @return interpolated vector
   */
  protected Vec3d interpolateVec(Vec3d vector1, Vec3d vector2, float fraction)
  {
    return new Vec3d(vector1.x * (1-fraction) + vector2.x * fraction,
                    vector1.y * (1-fraction) + vector2.y * fraction,
                    vector1.z * (1-fraction) + vector2.z * fraction
                    );
  }

  protected DragonBreathMode dragonBreathMode = DragonBreathMode.DEFAULT;


  private ArrayList<BreathFX> spawnedBreathFX = new ArrayList<BreathFX>();

}
