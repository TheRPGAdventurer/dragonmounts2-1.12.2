package com.TheRPGAdventurer.ROTD.entity.breath.sound;

import com.TheRPGAdventurer.ROTD.DragonMounts;

/**
 * User: The Grey Ghost
 * Date: 17/04/2014
 * Contains (some of the) sound effect names used for the dragon
 */
public enum SoundEffectNames {
	
  ADULT_BREATHE_FIRE_START("mob.dragon.breathweapon.fire.adultbreathefirestart"),
  ADULT_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.fire.adultbreathefireloop"),
  ADULT_BREATHE_FIRE_STOP("mob.dragon.breathweapon.fire.adultbreathefirestop"),
  JUVENILE_BREATHE_FIRE_START("mob.dragon.breathweapon.fire.juvenilebreathefirestart"),
  JUVENILE_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.fire.juvenilebreathefireloop"),
  JUVENILE_BREATHE_FIRE_STOP("mob.dragon.breathweapon.fire.juvenilebreathefirestop"),
  HATCHLING_BREATHE_FIRE_START("mob.dragon.breathweapon.fire.hatchlingbreathefirestart"),
  HATCHLING_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.fire.hatchlingbreathefireloop"),
  HATCHLING_BREATHE_FIRE_STOP("mob.dragon.breathweapon.fire.hatchlingbreathefirestop"),
  
  ADULT_BREATHE_ICE_START("mob.dragon.breathweapon.ice.adultbreatheicestart"),
  ADULT_BREATHE_ICE_LOOP("mob.dragon.breathweapon.ice.adultbreatheiceloop"),
  ADULT_BREATHE_ICE_STOP("mob.dragon.breathweapon.ice.adultbreatheicestop"),

  ADULT_BREATHE_AETHER_START("mob.dragon.breathweapon.air.adultbreatheairstart"),
  ADULT_BREATHE_AETHER_LOOP("mob.dragon.breathweapon.air.adultbreatheairloop"),
  ADULT_BREATHE_AETHER_STOP("mob.dragon.breathweapon.air.adultbreatheairstop");

  public final String getJsonName() {return DragonMounts.MODID + ":" + jsonName;}

  private SoundEffectNames(String i_jsonName) {
    jsonName = i_jsonName;
  }
  private final String jsonName;
}
