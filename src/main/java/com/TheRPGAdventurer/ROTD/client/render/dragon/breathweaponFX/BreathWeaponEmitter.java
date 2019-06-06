package com.TheRPGAdventurer.ROTD.client.render.dragon.breathweaponFX;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.effects.*;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Created by TGG on 21/06/2015.
 * Used to spawn breath particles on the client side (in future: will be different for different breath weapons)
 * Usage:
 * Each tick:
 * (1) setBeamEndpoints() to set the current beam origin and destination
 * (2) spawnBreathParticles() to spawn the particles()
 */
public class BreathWeaponEmitter {

  protected Vec3d origin;
  protected Vec3d direction;

  protected Vec3d previousOrigin;
  protected Vec3d previousDirection;
  protected int previousTickCount;

  /**
   * Set the current beam origin and target destination (used to calculate direction).
   * Will smooth out between ticks.
   * @param newOrigin the starting point of the beam (world inates)
   * @param newDestination the target of the beam (world inates)
   */
  public void setBeamEndpoints(Vec3d newOrigin, Vec3d newDestination) {
    origin = newOrigin;
    direction = newDestination.subtract(newOrigin).normalize();
  }

  /**
   * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
   *   the beam stopped for a while (tickCount skipped one or more tick)
   * @param world
   * @param power the strength of the beam
   */
  public void spawnBreathParticles(World world, BreathNode.Power power, int tickCount) {
	  EntityTameableDragon dragon = new EntityTameableDragon(world);
    if (tickCount != previousTickCount + 1) {
      previousDirection = direction;
      previousOrigin = origin;
    } else {
      if (previousDirection == null) previousDirection = direction;
      if (previousOrigin == null) previousOrigin = origin;
    }
    final int PARTICLES_PER_TICK = 4;
    for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
      float partialTickHeadStart = i / (float)PARTICLES_PER_TICK;
      Vec3d interpDirection = interpolateVec(previousDirection, direction, partialTickHeadStart);
      Vec3d interpOrigin = interpolateVec(previousOrigin, origin, partialTickHeadStart);
      FlameBreathFX flameBreathFX = FlameBreathFX.createFlameBreathFX(world,
              interpOrigin.x, interpOrigin.y, interpOrigin.z,
              interpDirection.x, interpDirection.y, interpDirection.z, power, partialTickHeadStart, dragon);
      
      Minecraft.getMinecraft().world.spawnEntity(flameBreathFX);
    }
    previousDirection = direction;
    previousOrigin = origin;
    previousTickCount = tickCount;
  }
  
  /**
   * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
   *   the beam stopped for a while (tickCount skipped one or more tick)
   * @param world
   * @param power the strength of the beam
   */
  public void spawnBreathParticlesforEnderDragon(World world, BreathNode.Power power, int tickCounter) {
	  EntityTameableDragon dragon = new EntityTameableDragon(world);
    if (tickCounter != previousTickCount + 1) {
      previousDirection = direction;
      previousOrigin = origin;
    } else {
      if (previousDirection == null) previousDirection = direction;
      if (previousOrigin == null) previousOrigin = origin;
    }
    final int PARTICLES_PER_TICK = 4;
    for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
      float partialTickHeadStart = i / (float)PARTICLES_PER_TICK;
      Vec3d interpDirection = interpolateVec(previousDirection, direction, partialTickHeadStart);
      Vec3d interpOrigin = interpolateVec(previousOrigin, origin, partialTickHeadStart);
      EnderBreathFX enderBreathFX = EnderBreathFX.createEnderBreathFX(world,
    		  interpOrigin.x, interpOrigin.y, interpOrigin.z,
    		  interpDirection.x, interpDirection.y, interpDirection.z, power, partialTickHeadStart, dragon) ;
      
      Minecraft.getMinecraft().world.spawnEntity(enderBreathFX);
    }
    previousDirection = direction;
    previousOrigin = origin;
    previousTickCount = tickCounter;
  }

  /**
   * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
   *   the beam stopped for a while (tickCount skipped one or more tick)
   * @param world
   * @param power the strength of the beam
   */
  public void spawnBreathParticlesforNetherDragon(World world, BreathNode.Power power, int tickCounter) {
	  EntityTameableDragon dragon = new EntityTameableDragon(world);
    if (tickCounter != previousTickCount + 1) {
      previousDirection = direction;
      previousOrigin = origin;
    } else {
      if (previousDirection == null) previousDirection = direction;
      if (previousOrigin == null) previousOrigin = origin;
    }
    final int PARTICLES_PER_TICK = 4;
    for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
      float partialTickHeadStart = i / (float)PARTICLES_PER_TICK;
      Vec3d interpDirection = interpolateVec(previousDirection, direction, partialTickHeadStart);
      Vec3d interpOrigin = interpolateVec(previousOrigin, origin, partialTickHeadStart);
      NetherBreathFX netherBreathFX = NetherBreathFX.createNetherBreathFX(world,
    		  interpOrigin.x, interpOrigin.y, interpOrigin.z,
    		  interpDirection.x, interpDirection.y, interpDirection.z, power, partialTickHeadStart, dragon) ;
      
      Minecraft.getMinecraft().world.spawnEntity(netherBreathFX);
    }
    previousDirection = direction;
    previousOrigin = origin;
    previousTickCount = tickCounter;
  }
  
  /**
   * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
   *   the beam stopped for a while (tickCount skipped one or more tick)
   * @param world
   * @param power the strength of the beam
   */
  public void spawnBreathParticlesforWaterDragon(World world, BreathNode.Power power, int tickCounter) {
	  EntityTameableDragon dragon = new EntityTameableDragon(world);
    if (tickCounter != previousTickCount + 1) {
      previousDirection = direction;
      previousOrigin = origin;
    } else {
      if (previousDirection == null) previousDirection = direction;
      if (previousOrigin == null) previousOrigin = origin;
    }
    final int PARTICLES_PER_TICK = 4;
    for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
      float partialTickHeadStart = i / (float)PARTICLES_PER_TICK;
      Vec3d interpDirection = interpolateVec(previousDirection, direction, partialTickHeadStart);
      Vec3d interpOrigin = interpolateVec(previousOrigin, origin, partialTickHeadStart);
      HydroBreathFX hydroBreathFX = HydroBreathFX.createHydroBreathFX(world,
    		  interpOrigin.x, interpOrigin.y, interpOrigin.z,
    		  interpDirection.x, interpDirection.y, interpDirection.z, power, partialTickHeadStart, dragon) ;
      
      Minecraft.getMinecraft().world.spawnEntity(hydroBreathFX);
    }
    previousDirection = direction;
    previousOrigin = origin;
    previousTickCount = tickCounter;
  }
  
  /**
   * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
   *   the beam stopped for a while (tickCount skipped one or more tick)
   * @param world
   * @param power the strength of the beam
   */
  public void spawnBreathParticlesforWitherDragon(World world, BreathNode.Power power, int tickCounter) {
	  EntityTameableDragon dragon = new EntityTameableDragon(world);
    if (tickCounter != previousTickCount + 1) {
      previousDirection = direction;
      previousOrigin = origin;
    } else {
      if (previousDirection == null) previousDirection = direction;
      if (previousOrigin == null) previousOrigin = origin;
    }
    final int PARTICLES_PER_TICK = 4;
    for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
      float partialTickHeadStart = i / (float)PARTICLES_PER_TICK;
      Vec3d interpDirection = interpolateVec(previousDirection, direction, partialTickHeadStart);
      Vec3d interpOrigin = interpolateVec(previousOrigin, origin, partialTickHeadStart);
      WitherBreathFX witherBreathFX = WitherBreathFX.createWitherBreathFX(world,
    		  interpOrigin.x, interpOrigin.y, interpOrigin.z,
    		  interpDirection.x, interpDirection.y, interpDirection.z, power, partialTickHeadStart, dragon) ;
      
      Minecraft.getMinecraft().world.spawnEntity(witherBreathFX);
    }
    previousDirection = direction;
    previousOrigin = origin;
    previousTickCount = tickCounter;
  }
  
  /**
   * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
   *   the beam stopped for a while (tickCount skipped one or more tick)
   * @param world
   * @param power the strength of the beam
   */
  public void spawnBreathParticlesforIceDragon(World world, BreathNode.Power power, int tickCounter) {
	  EntityTameableDragon dragon = new EntityTameableDragon(world);
    if (tickCounter != previousTickCount + 1) {
      previousDirection = direction;
      previousOrigin = origin;
    } else {
      if (previousDirection == null) previousDirection = direction;
      if (previousOrigin == null) previousOrigin = origin;
    }
    final int PARTICLES_PER_TICK = 4;
    for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
      float partialTickHeadStart = i / (float)PARTICLES_PER_TICK;
      Vec3d interpDirection = interpolateVec(previousDirection, direction, partialTickHeadStart);
      Vec3d interpOrigin = interpolateVec(previousOrigin, origin, partialTickHeadStart);
      IceBreathFX iceBreathFX = IceBreathFX.createIceBreathFX(world, 
    		  interpOrigin.x, interpOrigin.y, interpOrigin.z,
    		  interpDirection.x, interpDirection.y, interpDirection.z, power, partialTickHeadStart, dragon) ;
      
      Minecraft.getMinecraft().world.spawnEntity(iceBreathFX);
    }
    previousDirection = direction;
    previousOrigin = origin;
    previousTickCount = tickCounter;
  }

  /**
   * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
   *   the beam stopped for a while (tickCount skipped one or more tick)
   * @param world
   * @param power the strength of the beam
   */
  public void spawnBreathParticlesforAetherDragon(World world, BreathNode.Power power, int tickCounter) {
	  EntityTameableDragon dragon = new EntityTameableDragon(world);
    if (tickCounter != previousTickCount + 1) {
      previousDirection = direction;
      previousOrigin = origin;
    } else {
      if (previousDirection == null) previousDirection = direction;
      if (previousOrigin == null) previousOrigin = origin;
    }
    final int PARTICLES_PER_TICK = 4;
    for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
      float partialTickHeadStart = i / (float)PARTICLES_PER_TICK;
      Vec3d interpDirection = interpolateVec(previousDirection, direction, partialTickHeadStart);
      Vec3d interpOrigin = interpolateVec(previousOrigin, origin, partialTickHeadStart);
      AetherBreathFX aetherBreathFX = AetherBreathFX.createAetherBreathFX(world, interpOrigin.x, interpOrigin.y, interpOrigin.z,
    		  interpDirection.x, interpDirection.y, interpDirection.z,
              power, partialTickHeadStart, dragon);

      Minecraft.getMinecraft().world.spawnEntity(aetherBreathFX);
    }
    previousDirection = direction;
    previousOrigin = origin;
    previousTickCount = tickCounter;
  }
  /**
   * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
   *   the beam stopped for a while (tickCount skipped one or more tick)
   * @param world
   * @param power the strength of the beam
   */
  public void spawnBreathParticlesforPoisonDragon(World world, BreathNode.Power power, int tickCounter) {
    EntityTameableDragon dragon = new EntityTameableDragon(world);
    if (tickCounter != previousTickCount + 1) {
      previousDirection = direction;
      previousOrigin = origin;
    } else {
      if (previousDirection == null) previousDirection = direction;
      if (previousOrigin == null) previousOrigin = origin;
    }
    final int PARTICLES_PER_TICK = 4;
    for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
      float partialTickHeadStart = i / (float)PARTICLES_PER_TICK;
      Vec3d interpDirection = interpolateVec(previousDirection, direction, partialTickHeadStart);
      Vec3d interpOrigin = interpolateVec(previousOrigin, origin, partialTickHeadStart);
      PoisonBreathFX iceBreathFX = PoisonBreathFX.createPoisonBreathFX(world,
              interpOrigin.x, interpOrigin.y, interpOrigin.z,
              interpDirection.x, interpDirection.y, interpDirection.z, power, partialTickHeadStart, dragon) ;

      Minecraft.getMinecraft().world.spawnEntity(iceBreathFX);
    }
    previousDirection = direction;
    previousOrigin = origin;
    previousTickCount = tickCounter;
  }


  /**
   * interpolate from vector 1 to vector 2 using fraction
   * @param vector1
   * @param vector2
   * @param fraction 0 - 1; 0 = vector1, 1 = vector2
   * @return interpolated vector
   */
  protected Vec3d interpolateVec(Vec3d vector1, Vec3d vector2, float fraction) {
    return new Vec3d(vector1.x * (1-fraction) + vector2.x * fraction,
                    vector1.y * (1-fraction) + vector2.y * fraction,
                    vector1.z * (1-fraction) + vector2.z * fraction
                    );
  }

}
