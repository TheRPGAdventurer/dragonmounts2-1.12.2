package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.sound;

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

  ADULT_BREATHE_WATER_START("mob.dragon.breathweapon.water.breathewaterstart"),
  ADULT_BREATHE_WATER_LOOP("mob.dragon.breathweapon.water.breathewaterloop"),
  ADULT_BREATHE_WATER_STOP("mob.dragon.breathweapon.water.breathewaterstop"),
  
  ADULT_BREATHE_AIR_START("mob.dragon.breathweapon.air.breatheairstart"),
  ADULT_BREATHE_AIR_LOOP("mob.dragon.breathweapon.air.breatheairloop"),
  ADULT_BREATHE_AIR_STOP("mob.dragon.breathweapon.air.breatheairstop");

  public final String getJsonName() {return DragonMounts.MODID + ":" + jsonName;}

  private SoundEffectNames(String i_jsonName) {
    jsonName = i_jsonName;
  }
  private final String jsonName;
}
