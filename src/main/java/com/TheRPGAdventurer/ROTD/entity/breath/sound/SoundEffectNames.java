package com.TheRPGAdventurer.ROTD.entity.breath.sound;

import com.TheRPGAdventurer.ROTD.DragonMounts;

/**
 * User: The Grey Ghost
 * Date: 17/04/2014
 * Contains (some of the) sound effect names used for the dragon
 */
public enum SoundEffectNames {

  ADULT_BREATHE_FIRE_START("mob.enderdragon.breathweapon.fire.adultbreathefirestart", 2.0),
  ADULT_BREATHE_FIRE_LOOP("mob.enderdragon.breathweapon.fire.adultbreathefireloop", 5.336),
  ADULT_BREATHE_FIRE_STOP("mob.enderdragon.breathweapon.fire.adultbreathefirestop", 1.0),
  JUVENILE_BREATHE_FIRE_START("mob.enderdragon.breathweapon.fire.juvenilebreathefirestart", 2.0),
  JUVENILE_BREATHE_FIRE_LOOP("mob.enderdragon.breathweapon.fire.juvenilebreathefireloop", 5.336),
  JUVENILE_BREATHE_FIRE_STOP("mob.enderdragon.breathweapon.fire.juvenilebreathefirestop", 1.0),
  HATCHLING_BREATHE_FIRE_START("mob.enderdragon.breathweapon.fire.hatchlingbreathefirestart", 2.0),
  HATCHLING_BREATHE_FIRE_LOOP("mob.enderdragon.breathweapon.fire.hatchlingbreathefireloop", 5.336),
  HATCHLING_BREATHE_FIRE_STOP("mob.enderdragon.breathweapon.fire.hatchlingbreathefirestop", 1.0),


  ADULT_BREATHE_ICE_START("mob.dragon.breathweapon.adultbreatheicestart", 1.2),
  ADULT_BREATHE_ICE_LOOP("mob.dragon.breathweapon.adultbreatheiceloop", 7.6),
  ADULT_BREATHE_ICE_STOP("mob.dragon.breathweapon.adultbreatheicestop", 1.31),

  BREATHE_AIR_START("mob.enderdragon.breathweapon.air.breatheairstart", 1.5),
  BREATHE_AIR_LOOP("mob.enderdragon.breathweapon.air.breatheairloop", 7.407),
  BREATHE_AIR_STOP("mob.enderdragon.breathweapon.air.breatheairstop", 1.317);


  public final String getJsonName() {return DragonMounts.MODID + ":" + jsonName;}
  public final double getDurationInSeconds() {return durationInSeconds;}
  public final int getDurationInTicks() {return (int)(durationInSeconds * 20.0);}

  /**
   * Information about the sound effect
   * @param i_jsonName
   * @param i_durationInSeconds the duration of the sound effect (0 = unused) - in practice, this is the duration
   *                            before the cross-fade to the next sound starts.  For looping sounds no effect
   */
  private SoundEffectNames(String i_jsonName, double i_durationInSeconds) {
    jsonName = i_jsonName;
    durationInSeconds = i_durationInSeconds;
  }
  private final String jsonName;
  private final double durationInSeconds;
  }
