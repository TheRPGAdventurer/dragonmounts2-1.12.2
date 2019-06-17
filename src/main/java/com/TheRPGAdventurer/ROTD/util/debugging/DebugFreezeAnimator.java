package com.TheRPGAdventurer.ROTD.util.debugging;

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;

import org.lwjgl.input.Keyboard;

/**
 * Created by TGG on 29/06/2015.
 *   Freeze dragon animation and updates for debugging purposes
 *   frozen when the scroll lock key is down (and debug mode is set)
 */
public class DebugFreezeAnimator
{
  public static boolean isFrozen()
  {
	return DragonMountsConfig.isDebug() && Keyboard.isKeyDown(Keyboard.KEY_SCROLL);
  }
}
