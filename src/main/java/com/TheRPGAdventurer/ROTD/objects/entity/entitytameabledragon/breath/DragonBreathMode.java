package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath;

import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import scala.Int;

/** The dragon breath mode - eg for forest; whether the breath is burning or not.  Not used by most breath types.
 * Created by TGG on 5/03/2016.
 */
public class DragonBreathMode {

  public static DragonBreathMode FOREST_NOT_BURNING = new DragonBreathMode(0);
  public static DragonBreathMode FOREST_BURNING = new DragonBreathMode(1);
  public static DragonBreathMode DEFAULT = new DragonBreathMode(0);

  public static DragonBreathMode createFromDataParameter(EntityDataManager entityDataManager,
                                                         DataParameter<Integer> dataParamBreathWeaponMode)
  {
    Integer mode = entityDataManager.get(dataParamBreathWeaponMode);
    return new DragonBreathMode(mode);
  }

  public void writeToDataWatcher(EntityDataManager entityDataManager,
                                 DataParameter<Integer> dataParamBreathWeaponMod)
  {
    entityDataManager.set(dataParamBreathWeaponMod, breathMode);
  }

  @Override
  public boolean equals(Object comparison)
  {
    if (!(comparison instanceof DragonBreathMode)) return false;
    DragonBreathMode comparisonDBM = (DragonBreathMode)comparison;
    return this.breathMode == comparisonDBM.breathMode;
  }

  @Override
  public int hashCode()
  {
    return breathMode;
  }

//  public int getIntValue() {return breathMode;}

  private DragonBreathMode(int i_breathModeInt)
  {
    breathMode = i_breathModeInt;
  }

  private int breathMode;
}
