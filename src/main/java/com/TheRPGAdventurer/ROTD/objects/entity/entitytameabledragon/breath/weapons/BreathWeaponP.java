package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.weapons;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathAffectedBlock;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathAffectedEntity;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.DragonBreathMode;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

/**
 * Created by TGG on 5/08/2015.
 *
 * Models the effects of a breathweapon on blocks and entities
 * Usage:
 * 1) Construct with a parent dragon
 * 2) affectBlock() to apply an area of effect to the given block (eg set fire to it)
 * 3) affectEntity() to apply an area of effect to the given entity (eg damage it)
 *
 * Subclassed with the different breath weapon types
 */
public abstract class BreathWeaponP
{
  public BreathWeaponP(EntityTameableDragon i_dragon)
  {
    dragon = i_dragon;
  }

  /** if the hitDensity is high enough, manipulate the block (eg set fire to it)
   * @param world
   * @param blockPosition  the world [x,y,z] of the block
   * @param currentHitDensity
   * @return the updated block hit density
   */
  abstract public BreathAffectedBlock affectBlock(World world, Vec3i blockPosition,
                                           BreathAffectedBlock currentHitDensity);


  /** if the hitDensity is high enough, manipulate the entity (eg set fire to it, damage it)
   * A dragon can't be damaged by its own breathweapon;
   * If the "orbholder immune" option is on, and the entity is a player holding a dragon orb, ignore damage.
   * @param world
   * @param entityID  the ID of the affected entity
   * @param currentHitDensity the hit density
   * @return the updated hit density; null if entity dead, doesn't exist, or otherwise not affected
   */
  abstract public BreathAffectedEntity affectEntity(World world, Integer entityID, BreathAffectedEntity currentHitDensity);

  /**
   * Update the breath weapon's mode depending on what the dragon is doing.
   */
  public void updateBreathWeaponMode()
  {
    dragon.getBreathHelperP().setBreathMode(DragonBreathMode.DEFAULT);
  }

  // return true if the breath affected area should be reset (wipe) when the dragon breath mode changes
  public boolean shouldResetOnBreathModeChange(DragonBreathMode newDragonBreathMode)
  {
    return false;
  }

  protected EntityTameableDragon dragon;
}
