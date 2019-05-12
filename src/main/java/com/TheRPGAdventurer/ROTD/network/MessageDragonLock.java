package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonLock extends AbstractMessage<MessageDragonLock> {

    private int dragonId;
//    private boolean lock;

    public MessageDragonLock() {

    }

    public MessageDragonLock(int dragonId) {
        this.dragonId = dragonId;
//        this.lock = lock;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dragonId = buf.readInt();
//        lock = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dragonId);
//        buf.writeBoolean(lock);

    }

    @Override
    public void onClientReceived(Minecraft arg0, MessageDragonLock arg1, EntityPlayer arg2, MessageContext arg3) {


    }

    @Override
    public void onServerReceived(MinecraftServer arg0, MessageDragonLock arg1, EntityPlayer player, MessageContext arg3) {
        Entity entity = player.world.getEntityByID(arg1.dragonId);
        if (entity instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) entity;
//            if (arg1.lock) {
                dragon.setToAllowedOtherPlayers(!dragon.allowedOtherPlayers());
//            }
        }
    }
}
