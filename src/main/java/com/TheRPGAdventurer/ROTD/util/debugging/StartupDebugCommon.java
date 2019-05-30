package com.TheRPGAdventurer.ROTD.util.debugging;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
  public static ItemTestRunner itemTestRunner;  // this holds the unique instance of your block

  public static void preInitCommon()
  {
    // each instance of your item should have a name that is unique within your mod.  use lower case.
    itemTestRunner = (ItemTestRunner)(new ItemTestRunner().setUnlocalizedName("test_runner"));
    itemTestRunner.setRegistryName("test_runner");
    ForgeRegistries.ITEMS.register(itemTestRunner);
  }

  public static void initCommon()
  {
  }

  public static void postInitCommon()
  {
  }
}
