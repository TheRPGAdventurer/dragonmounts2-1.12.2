package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.util.MiscPlayerProperties;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventLiving {


    @SubscribeEvent
    public void onEntityFall(LivingFallEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer){
            MiscPlayerProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(event.getEntityLiving(), MiscPlayerProperties.class);
            if(properties.hasDismountedDragon){
                event.setDamageMultiplier(0);
                properties.hasDismountedDragon = false;
            }
        }
    }
}
