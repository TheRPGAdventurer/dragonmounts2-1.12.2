package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DragonEventLiving {

    @SubscribeEvent
    public void onEntityMount(EntityMountEvent evt) {
        if (evt.getEntityBeingMounted() instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) evt.getEntityBeingMounted();
            if (evt.isDismounting() && evt.getEntityMounting() instanceof EntityPlayer && !evt.getEntityMounting().world.isRemote) {
                EntityPlayer player = (EntityPlayer) evt.getEntityMounting();
                if (player.isSneaking()) {
                    player.removePassengers();
                }

                if (dragon.isOwner((EntityPlayer) evt.getEntityMounting())) {
                    dragon.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
                    player.fallDistance = -dragon.height;
                }
            }

        }
    }
}
