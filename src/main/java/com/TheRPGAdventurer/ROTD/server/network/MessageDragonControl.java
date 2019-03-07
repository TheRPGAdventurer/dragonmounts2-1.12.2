package com.TheRPGAdventurer.ROTD.server.network;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.MoverType;
import sun.plugin2.message.Message;

public class MessageDragonControl extends AbstractMessage<MessageDragonControl> {

    public int dragonId;
    public double x;
    public double y;
    public double z;

    public MessageDragonControl() {

    }

    public MessageDragonControl(int dragonId, double posX, double posY, double posZ) {
        this.dragonId = dragonId;
        this.x = posX;
        this.y = posY;
        this.z = posZ;
    }

    @Override
    public void onClientReceived(Minecraft minecraft, MessageDragonControl messageDragonControl, EntityPlayer entityPlayer, MessageContext messageContext) {

    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageDragonControl message, EntityPlayer player, MessageContext messageContext) {
        Entity entity = player.world.getEntityByID(message.dragonId);
        if (entity != null && entity instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) entity;
          //  if (dragon.isOwner(player)) {
          //      dragon.setControlState(message.controlState);
          //  }
            dragon.getMoveHelper().setMoveTo(message.x, message.y, message.z, 1.2);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.dragonId = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dragonId);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }
}
