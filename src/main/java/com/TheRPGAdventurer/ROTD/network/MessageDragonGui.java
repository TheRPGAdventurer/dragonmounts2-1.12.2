package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonGui extends AbstractMessage<MessageDragonGui> {

    public int dragonId;
    private boolean sit;
    private boolean lock;

    public MessageDragonGui(int dragonId, boolean sit, boolean lock) {
        this.dragonId=dragonId;
        this.lock=lock;
        this.sit=sit;
    }

    public MessageDragonGui() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        lock=buf.readBoolean();
        sit=buf.readBoolean();
        dragonId=buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dragonId);
        buf.writeBoolean(lock);
        buf.writeBoolean(sit);
    }

    @Override
    public void onClientReceived(Minecraft minecraft, MessageDragonGui message, EntityPlayer arg2, MessageContext context) {
    }

    @Override
    public void onServerReceived(MinecraftServer minecraft, MessageDragonGui message, EntityPlayer player, MessageContext context) {
        Entity entity=player.world.getEntityByID(message.dragonId);
        if (entity instanceof EntityTameableDragon) {
            EntityTameableDragon dragon=(EntityTameableDragon) entity;
            if (message.sit) {
                dragon.getAISit().setSitting(!dragon.isSitting());
                dragon.getNavigator().clearPathEntity();
            }

            if (message.lock) {
                dragon.setToAllowedOtherPlayers(!dragon.allowedOtherPlayers());
            }
        }
    }
}
