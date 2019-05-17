package com.TheRPGAdventurer.ROTD.entity.breath.sound;

import com.TheRPGAdventurer.ROTD.DragonMounts;

/**
 * User: The Grey Ghost
 * Date: 17/04/2014
 * Contains (some of the) sound effect names used for the dragon
 */
public enum SoundEffectNames {
	
  ADULT_BREATHE_FIRE_START("mob.dragon.breathweapon.dragon.adultbreathefirestart"),
  ADULT_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.dragon.adultbreathefireloop"),
  ADULT_BREATHE_FIRE_STOP("mob.dragon.breathweapon.dragon.adultbreathefirestop"),
  JUVENILE_BREATHE_FIRE_START("mob.dragon.breathweapon.dragon.juvenilebreathefirestart"),
  JUVENILE_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.dragon.juvenilebreathefireloop"),
  JUVENILE_BREATHE_FIRE_STOP("mob.dragon.breathweapon.dragon.juvenilebreathefirestop"),
  HATCHLING_BREATHE_FIRE_START("mob.dragon.breathweapon.dragon.hatchlingbreathefirestart"),
  HATCHLING_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.dragon.hatchlingbreathefireloop"),
  HATCHLING_BREATHE_FIRE_STOP("mob.dragon.breathweapon.dragon.hatchlingbreathefirestop"),
  
  ADULT_BREATHE_ICE_START("mob.dragon.breathweapon.dragon.adultbreatheicestart"),
  ADULT_BREATHE_ICE_LOOP("mob.dragon.breathweapon.dragon.adultbreatheiceloop"),
  ADULT_BREATHE_ICE_STOP("mob.dragon.breathweapon.dragon.adultbreatheicestop");

  public final String getJsonName() {return DragonMounts.MODID + ":" + jsonName;}

  private SoundEffectNames(String i_jsonName) {
    jsonName = i_jsonName;
  }
  private final String jsonName;
}
