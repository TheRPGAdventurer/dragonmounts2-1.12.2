package com.TheRPGAdventurer.ROTD.event;

import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.items.IItemDragonOrbColour;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by TGG on 29/05/2019.
 */
public class IItemColorRegistration {
  @SubscribeEvent
  public static void registerItemHandlers(ColorHandlerEvent.Item event)
  {
    event.getItemColors().registerItemColorHandler(new IItemDragonOrbColour(), ModItems.dragon_orb);
  }
}
