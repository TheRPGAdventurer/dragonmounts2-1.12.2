package com.TheRPGAdventurer.ROTD.client.render.dragon.breathweaponFX;

import com.TheRPGAdventurer.ROTD.entity.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.entity.breath.DragonBreathMode;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

/** EntityFX used to refer to all BreathFX types
 * Created by TGG on 6/03/2016.
 */
public class BreathFX extends Particle {
  public BreathFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn,
                  double zSpeedIn) {
    super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
  }

  public void updateBreathMode(DragonBreathMode dragonBreathMode)
  {
    breathNode.changeBreathMode(dragonBreathMode);
  }

  protected BreathNode breathNode;
}
