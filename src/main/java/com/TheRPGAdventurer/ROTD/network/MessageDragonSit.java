package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class MessageDragonSit implements IMessage {

    public UUID dragonId;

    public MessageDragonSit() {

    }

    public MessageDragonSit(UUID dragonId) {
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

    public static class MessageDragonSitHandler implements IMessageHandler<MessageDragonSit, IMessage> {
        @Override
        public IMessage onMessage(MessageDragonSit message, MessageContext ctx) {
            EntityPlayer player = (ctx.side.isClient() ? Minecraft.getMinecraft().player : ctx.getServerHandler().player);
            MinecraftServer server = player.getServer();
            //        player.world.playSound(null, player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 1, 1);
            if (!player.world.isRemote) {
                Entity entity = server.getEntityFromUuid(message.dragonId);
                if (entity instanceof EntityTameableDragon) {
                    EntityTameableDragon dragon = (EntityTameableDragon) entity;
                    dragon.getAISit().setSitting(!dragon.isSitting());
                    dragon.getNavigator().clearPath();
                    dragon.setnowhistlecommands(true);
                } else player.sendStatusMessage(new TextComponentTranslation("whistle.msg.fail"), true);
            }
            return null;
        }
    }
}