package com.TheRPGAdventurer.ROTD.server.network;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonGui extends AbstractMessage<MessageDragonGui> {

    private int dragonId;
    private boolean sit;
    private boolean lock;

    public MessageDragonGui(int dragonId) {

    }

    public MessageDragonGui(int dragonId, boolean sit, boolean lock) {
        this.dragonId = dragonId;
        this.sit = sit;
        this.lock = lock;
    }

    public MessageDragonGui() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dragonId = buf.readInt();
        sit = buf.readBoolean();
        lock = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dragonId);
        buf.writeBoolean(sit);
        buf.writeBoolean(lock);

    }

    @Override
    public void onClientReceived(Minecraft arg0, MessageDragonGui arg1, EntityPlayer arg2, MessageContext arg3) {


    }

    @Override
    public void onServerReceived(MinecraftServer arg0, MessageDragonGui arg1, EntityPlayer player, MessageContext arg3) {
        Entity entity = player.world.getEntityByID(dragonId);
        if (entity instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) entity;
            if (arg1.lock) {
                dragon.setToAllowedOtherPlayers(!dragon.allowedOtherPlayers());
            }

            if (arg1.sit) {
                dragon.setSitting(!dragon.isSitting());
            }
        }
    }
}
