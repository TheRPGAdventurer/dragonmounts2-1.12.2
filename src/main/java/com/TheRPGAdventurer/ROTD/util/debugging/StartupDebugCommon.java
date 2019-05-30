package com.TheRPGAdventurer.ROTD.util.debugging;

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.items.ItemTestRunner;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * User: The Grey Ghost
 * Date: 24/12/2014
 *
 * The Startup classes for this example are called during startup, in the following order:
 *  preInitCommon
 *  preInitClientOnly
 *  initCommon
 *  initClientOnly
 *  postInitCommon
 *  postInitClientOnly
 *  See MinecraftByExample class for more information
 */
public class StartupDebugCommon
{
  public static void preInitCommon()
  {
    if (!DragonMountsConfig.isDebug()) return;
  }

  public static void initCommon()
  {
    if (!DragonMountsConfig.isDebug()) return;
  }

  public static void postInitCommon()
  {
    if (!DragonMountsConfig.isDebug()) return;
  }
}
