package com.TheRPGAdventurer.ROTD.network.gui;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonGuiLock extends AbstractMessage<MessageDragonGuiLock> {

    public int dragonId;

    public MessageDragonGuiLock(int dragonId) {
        this.dragonId=dragonId;
    }

    public MessageDragonGuiLock() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dragonId=buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dragonId);
    }

    @Override
    public void onClientReceived(Minecraft minecraft, MessageDragonGuiLock message, EntityPlayer arg2, MessageContext context) {
    }

    @Override
    public void onServerReceived(MinecraftServer minecraft, MessageDragonGuiLock message, EntityPlayer player, MessageContext context) {
        Entity entity=player.world.getEntityByID(message.dragonId);
        if (entity instanceof EntityTameableDragon) {
            EntityTameableDragon dragon=(EntityTameableDragon) entity;
            dragon.setToAllowedOtherPlayers(!dragon.allowedOtherPlayers());
        }
    }
}
