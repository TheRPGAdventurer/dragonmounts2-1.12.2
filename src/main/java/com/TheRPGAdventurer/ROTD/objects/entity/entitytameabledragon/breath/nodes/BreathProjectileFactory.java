package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.nodes;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.DragonBreathHelperP;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.nodes.BreathNodeP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Created by TGG on 14/03/2016.
 * Used to generate a discrete projectile (eg the Nether dragon which fires fireballs)
 * Create a copy of the factory per dragon - the factory has a 'cooldown' to regulate how often projectiles are fired
 * 1) spawnProjectile(...) spawns a projectile entity, if the factory is ready.  Returns true if spawned.
 * 2) updateTick(...) every tick, to keep the factory synchronised (eg cooldown timer)
 */
public interface BreathProjectileFactory {
  boolean spawnProjectile(World world, EntityTameableDragon dragon, Vec3d origin, Vec3d target,
                          BreathNodeP.Power i_power);
  void updateTick(DragonBreathHelperP.BreathState breathState);
}
