package com.TheRPGAdventurer.ROTD.server.network;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageDragonExtras extends AbstractMessage<MessageDragonExtras> {

    private int dragonId;
    public boolean isHoverCancel;
    public boolean isFollowYaw;


    public MessageDragonExtras(int dragonId, boolean isHoverCancel, boolean isFollowYaw) {
        this.dragonId = dragonId;
        this.isHoverCancel = isHoverCancel;
        this.isFollowYaw = isFollowYaw;
    }

    public MessageDragonExtras() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        dragonId = buf.readInt();
        isHoverCancel = buf.readBoolean();
        isFollowYaw = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dragonId);
        buf.writeBoolean(isHoverCancel);
        buf.writeBoolean(isFollowYaw);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, MessageDragonExtras message, EntityPlayer player, MessageContext messageContext) {

    }

    @Override
    public void onServerReceived(MinecraftServer server, MessageDragonExtras message, EntityPlayer player, MessageContext messageContext) {
        Entity entity = player.world.getEntityByID(message.dragonId);
        if (entity instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) entity;
            if (message.isHoverCancel) {
                dragon.setUnHovered(true);
            } else {
                dragon.setUnHovered(false);
            }

            if(message.isFollowYaw) {
                dragon.setFollowYaw(true);
            } else {
                dragon.setFollowYaw(false);
            }
        }
    }
}
