package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class MessageDragonGui implements IMessage {

    public UUID dragonId;
    /**
     * 1 = sit
     * 2 = lock
     */
    public int state;

    public MessageDragonGui() {
    }

    public MessageDragonGui(UUID dragonId, int state) {
        this.dragonId = dragonId;
        this.state = state;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        dragonId = packetBuf.readUniqueId();
//        lock = buf.readBoolean();
        state = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        packetBuf.writeUniqueId(dragonId);
//        buf.writeBoolean(lock);
        buf.writeInt(state);
    }

    public static class MessageDragonGuiHandler implements IMessageHandler<MessageDragonGui, IMessage> {

        @Override
        public IMessage onMessage(MessageDragonGui message, MessageContext ctx) {
            Entity entity = ctx.getServerHandler().player.world.getMinecraftServer().getEntityFromUuid(message.dragonId);
            if (entity instanceof EntityTameableDragon) {
                EntityTameableDragon dragon = (EntityTameableDragon) entity;
                if (message.state == 1) {
                    dragon.getAISit().setSitting(!dragon.isSitting());
                } else if (message.state == 2) {
                    dragon.setToAllowedOtherPlayers(!dragon.allowedOtherPlayers());
                }
            }

            return null;
        }
    }
}

//    @Override
//    public void onServerReceived(MinecraftServer server, MessageDragonGui message, EntityPlayer player, MessageContext context) {
//        Entity entity = server.getEntityFromUuid(message.dragonId);
//        if (entity instanceof EntityTameableDragon) {
//            EntityTameableDragon dragon = (EntityTameableDragon) entity;
//            if (message.lock) {
//                dragon.setToAllowedOtherPlayers(!dragon.allowedOtherPlayers());
//            } else {
//                if (message.sit) dragon.getAISit().setSitting(!dragon.isSitting());
//            }
//        }
//    }