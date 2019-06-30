/*
 ** 2012 August 23
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper;

import com.TheRPGAdventurer.ROTD.util.math.MathX;
import net.minecraft.util.math.MathHelper;
import scala.Int;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Enum for dragon life stages. Used as aliases for the age value of dragons.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public enum DragonLifeStage {

  //order, duration of stage in hours of minecraft time, scaleAtStartOfStage, scaleAtEndOfStage
  EGG         (0, 36, 0.25f, 0.25f),
  HATCHLING   (1, 48, 0.04f, 0.09f),
  INFANT      (2, 12, 0.09f, 0.33f),
  PREJUVENILE (3, 18, 0.33f, 0.50f),
  JUVENILE    (4, 60, 0.50f, 1.00f),
  ADULT       (5,  0, 1.00f, 1.00f);        // scale of the final stage should be 1.00F to avoid breaking other code

//  desired durations (30 Jun 2019)
//  egg = 30 minutes
//          hatchling = 45 minutes
//          infant = 10 minutes (just filler)
//  prejuvenile = 15 minutes (just filler)
//  juvenile = 50 minutes
//          adult = 1 hour

  //
  private final int order;
  private final int durationTicks; // -1 means infinite
  private final float startScale;
  private final float finalScale;

  /**
   * Which life stage is the dragon in?
   * @param order = the sort order of this stage (0 = first, 1 = second, etc)
   * @param minecraftTimeHours = the duration of this stage in game time hours (each hour game time is 50 seconds in real life)
   * @param scaleAtEndOfStage size of this stage relative to the final scale (adult)
   */
  DragonLifeStage(int order, int minecraftTimeHours, float scaleAtStartOfStage, float scaleAtEndOfStage) {
    this.order = order;
    final int TICKS_PER_MINECRAFT_HOUR = 20 * 60 * 20 / 24;  // 20 (ticks/real life second) * 60 (seconds / min)
                                                             //   * 20 (real life minutes per minecraft day) / 24 (hours/day)
    this.durationTicks = minecraftTimeHours * TICKS_PER_MINECRAFT_HOUR;
    this.startScale = scaleAtStartOfStage;
    this.finalScale = scaleAtEndOfStage;
  }

  /** true if we're in the egg, false otherwise
   * @return
   */
  public boolean isEgg() {
    return this == EGG;
  }

  /**
   * does this stage act like a minecraft baby?
   * @return
   */
  public boolean isBaby() {
    return this == HATCHLING || this == INFANT;
  }

  public boolean isHatchling() {
    return  this == HATCHLING;
  }

  /**
   * is the dragon fully grown?
   * @return
   */
  public boolean isFullyGrown() {
    return this == ADULT;
  }

  /**
   * is this dragon large enough to breath?
   * @return
   */
  public boolean isOldEnoughToBreathe() {
    return this.order >= PREJUVENILE.order;
  }


  /**
   * get the current life stage based on the dragon's age
   * @param ticksSinceCreation number of ticks since the egg was created
   * @return
   */
  public static DragonLifeStage getLifeStageFromTickCount(int ticksSinceCreation) {
    ticksSinceCreation = clipTickCountToValid(ticksSinceCreation);

    DragonLifeStage stageFound = sortedStages.get(sortedStages.firstKey());
    for (DragonLifeStage dragonLifeStage : sortedStages.values()) {
      if (ticksSinceCreation < stageInfo.get(dragonLifeStage).startTicks) {
        return stageFound;
      }
      stageFound = dragonLifeStage;
    }
    return stageFound;
  }

  public static float getStageProgressFromTickCount(int ticksSinceCreation) {
    ticksSinceCreation = clipTickCountToValid(ticksSinceCreation);
    DragonLifeStage lifeStage = getLifeStageFromTickCount(ticksSinceCreation);
    if (lifeStage.durationTicks == 0) return 1.0F;

    int lifeStageTicks = ticksSinceCreation - stageInfo.get(lifeStage).startTicks;
    return lifeStageTicks / (float)lifeStage.durationTicks;
  }

  public static float getScaleFromTickCount(int ticksSinceCreation) {
    DragonLifeStage lifeStage = getLifeStageFromTickCount(ticksSinceCreation);
    StageInfo si = stageInfo.get(lifeStage);
    int timeInThisStage = ticksSinceCreation - si.startTicks;
    if (lifeStage.durationTicks == 0) {
      return lifeStage.startScale;
    }
    float fractionOfStage = timeInThisStage / (float)lifeStage.durationTicks;

    return MathX.lerp(lifeStage.startScale, lifeStage.finalScale, fractionOfStage);
  }

  /**
   * what is the tick count corresponding to the start of this stage?
   * @return
   */
  public int getStartTickCount()
  {
    return stageInfo.get(this).startTicks;
  }

  public static int clipTickCountToValid(int ticksSinceCreation) {
    return MathHelper.clamp(ticksSinceCreation, minimumValidTickValue, maximumValidTickValue);
  }

  private static HashMap<DragonLifeStage, StageInfo> stageInfo;
  private static SortedMap<Integer, DragonLifeStage> sortedStages = new TreeMap<>();
  private static int minimumValidTickValue;
  private static int maximumValidTickValue;

  static class StageInfo {
    int startTicks;
    int endTicks;
  }

  // set up various helper structures
  static { // guaranteed to run only after all enums have been created
    for (DragonLifeStage dragonLifeStage : DragonLifeStage.values()) {
      sortedStages.put(dragonLifeStage.order, dragonLifeStage);
    }

    int startTickCumulative = 0;
    stageInfo = new HashMap<DragonLifeStage, StageInfo>(sortedStages.size());

    minimumValidTickValue = startTickCumulative;
    for (DragonLifeStage dragonLifeStage : sortedStages.values()) {
      StageInfo si = new StageInfo();
      si.startTicks = startTickCumulative;
      startTickCumulative += dragonLifeStage.durationTicks;
      si.endTicks = startTickCumulative;
      stageInfo.put(dragonLifeStage, si);
    }
    maximumValidTickValue = startTickCumulative;
  }

}
