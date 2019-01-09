package com.TheRPGAdventurer.ROTD.server.network;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonControl extends AbstractMessage<MessageDragonControl> {

    private boolean hoverCancel;

    public MessageDragonControl(boolean hoverCancel) {
        this.hoverCancel = hoverCancel;
    }

    public MessageDragonControl() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        hoverCancel = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(hoverCancel);

    }

    @Override
    public void onClientReceived(Minecraft minecraft, MessageDragonControl messageDragonControl, EntityPlayer entityPlayer, MessageContext messageContext) {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageDragonControl messageDragonControl, EntityPlayer entityPlayer, MessageContext messageContext) {
        if(entityPlayer.getRidingEntity() instanceof  EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) entityPlayer.getRidingEntity();
            if(messageDragonControl.hoverCancel) {
                dragon.setUnHovered(true);
            } else {
                dragon.setUnHovered(false);
            }
        }
    }
}
