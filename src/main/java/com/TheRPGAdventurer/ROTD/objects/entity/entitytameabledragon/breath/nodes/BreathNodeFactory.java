package com.TheRPGAdventurer.ROTD.entity.breath.nodes;

import com.TheRPGAdventurer.ROTD.entity.breath.DragonBreathMode;

/**
 * Created by TGG on 14/12/2015.
 */
public interface BreathNodeFactory
{
  public BreathNodeP createBreathNode(BreathNodeP.Power i_power, DragonBreathMode dragonBreathMode);
}
