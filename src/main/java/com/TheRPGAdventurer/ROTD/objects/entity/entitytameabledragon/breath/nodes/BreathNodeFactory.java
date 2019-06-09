package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.nodes;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.DragonBreathMode;

/**
 * Created by TGG on 14/12/2015.
 */
public interface BreathNodeFactory
{
  public BreathNodeP createBreathNode(BreathNodeP.Power i_power, DragonBreathMode dragonBreathMode);
}
