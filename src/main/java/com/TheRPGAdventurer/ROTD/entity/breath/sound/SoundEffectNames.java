package com.TheRPGAdventurer.ROTD.entity.breath.sound;

import com.TheRPGAdventurer.ROTD.DragonMounts;

/**
 * User: The Grey Ghost
 * Date: 17/04/2014
 * Contains (some of the) sound effect names used for the dragon
 */
public enum SoundEffectNames {
	
  ADULT_BREATHE_FIRE_START("mob.dragon.breathweapon.adultbreathefirestart"),
  ADULT_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.adultbreathefireloop"),
  ADULT_BREATHE_FIRE_STOP("mob.dragon.breathweapon.adultbreathefirestop"),
  JUVENILE_BREATHE_FIRE_START("mob.dragon.breathweapon.juvenilebreathefirestart"),
  JUVENILE_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.juvenilebreathefireloop"),
  JUVENILE_BREATHE_FIRE_STOP("mob.dragon.breathweapon.juvenilebreathefirestop"),
  HATCHLING_BREATHE_FIRE_START("mob.dragon.breathweapon.hatchlingbreathefirestart"),
  HATCHLING_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.hatchlingbreathefireloop"),
  HATCHLING_BREATHE_FIRE_STOP("mob.dragon.breathweapon.hatchlingbreathefirestop"),
  
  ADULT_BREATHE_ICE_START("mob.dragon.breathweapon.adultbreatheicestart"),
  ADULT_BREATHE_ICE_LOOP("mob.dragon.breathweapon.adultbreatheiceloop"),
  ADULT_BREATHE_ICE_STOP("mob.dragon.breathweapon.adultbreatheicestop");

  public final String getJsonName() {return DragonMounts.MODID + ":" + jsonName;}

  private SoundEffectNames(String i_jsonName) {
    jsonName = i_jsonName;
  }
  private final String jsonName;
}
