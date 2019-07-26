package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.inits.ModSounds;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class MessageDragonFireSupport implements IMessage {

    public UUID dragonId;

    public MessageDragonFireSupport() {
    }

    public MessageDragonFireSupport(UUID dragonId) {
        this.dragonId = dragonId;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        dragonId = packetBuf.readUniqueId();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        packetBuf.writeUniqueId(dragonId);

    }

    public static class MessageDragonFireSupportHandler implements IMessageHandler<MessageDragonFireSupport, IMessage> {

        @Override
        public IMessage onMessage(MessageDragonFireSupport message, MessageContext ctx) {
            EntityPlayer player = (ctx.side.isClient() ? Minecraft.getMinecraft().player : ctx.getServerHandler().player);
            MinecraftServer server = player.getServer();
            if (!player.world.isRemote) {
                Entity entity = server.getEntityFromUuid(message.dragonId);
                EntityTameableDragon dragon = (EntityTameableDragon) entity;
                if (entity != null) {
                    if (entity instanceof EntityTameableDragon && dragon.isOwner(player)) {
                        dragon.setfiresupport(!dragon.firesupport());
                    }

                    player.world.playSound(null, player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 1, 1);
                } else player.sendStatusMessage(new TextComponentTranslation("whistle.msg.fail"), true);
            }
            return null;
        }
    }
}