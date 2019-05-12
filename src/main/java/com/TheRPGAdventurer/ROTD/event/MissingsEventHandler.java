package com.TheRPGAdventurer.ROTD.event;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class MissingsEventHandler {
	
	
    @SubscribeEvent
    public void missingMapping(RegistryEvent.MissingMappings<Item> event) {
        for (MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
            if (entry.key.toString().equals("dragonmounts:ender_dragonscales"))  {
                ResourceLocation newTotem = new ResourceLocation("dragonmounts:ender_dragonscales1");
                entry.remap(ForgeRegistries.ITEMS.getValue(newTotem));
            }
        }
    }

}
