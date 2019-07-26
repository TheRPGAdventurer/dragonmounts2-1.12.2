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

public class MessageDragonWhistle implements IMessage {

    public UUID dragonId;
    public byte controlState;

    public MessageDragonWhistle(UUID dragonId, byte controlState) {
        this.dragonId = dragonId;
        this.controlState = controlState;
    }

    public MessageDragonWhistle() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        dragonId = packetBuf.readUniqueId();
        controlState = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        packetBuf.writeUniqueId(dragonId);
        buf.writeByte(controlState);

    }

    public static class MessageDragonWhistleHandler implements IMessageHandler<MessageDragonWhistle, IMessage> {
        @Override
        public IMessage onMessage(MessageDragonWhistle message, MessageContext ctx) {
            EntityPlayer player = (ctx.side.isClient() ? Minecraft.getMinecraft().player : ctx.getServerHandler().player);
            MinecraftServer server = player.getServer();
            Entity entity = server.getEntityFromUuid(message.dragonId);
            if (player.world.isRemote) return null;
            if (entity instanceof EntityTameableDragon) {
                EntityTameableDragon dragon = (EntityTameableDragon) entity;
                dragon.setSitting(false);
                dragon.setWhistleState(message.controlState);
                player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            } else {
                player.sendStatusMessage(new TextComponentTranslation("whistle.msg.fail"), true);
            }
            return null;
        }
    }
}
