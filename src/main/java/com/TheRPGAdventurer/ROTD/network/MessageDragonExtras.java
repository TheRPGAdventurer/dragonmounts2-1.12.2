package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonExtras implements IMessage {

    public boolean isHoverCancel;
    public boolean isFollowYaw;
    public boolean locky;
    public boolean isBoosting;
    public boolean down;
    private int dragonId;

    public MessageDragonExtras() {
    }

    public MessageDragonExtras(int dragonId, boolean isHoverCancel, boolean isFollowYaw, boolean locky, boolean isBoosting, boolean down) {
        this.dragonId = dragonId;
        this.isHoverCancel = isHoverCancel;
        this.isFollowYaw = isFollowYaw;
        this.locky = locky;
        this.isBoosting = isBoosting;
        this.down = down;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        dragonId = buf.readInt();
        isHoverCancel = buf.readBoolean();
        isFollowYaw = buf.readBoolean();
        locky = buf.readBoolean();
        isBoosting = buf.readBoolean();
        down = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dragonId);
        buf.writeBoolean(isHoverCancel);
        buf.writeBoolean(isFollowYaw);
        buf.writeBoolean(locky);
        buf.writeBoolean(isBoosting);
        buf.writeBoolean(down);

    }

    public static class MessageDragonExtrasHandler implements IMessageHandler<MessageDragonExtras, IMessage> {

        @Override
        public IMessage onMessage(MessageDragonExtras message, MessageContext ctx) {
            EntityPlayer player = (ctx.side.isClient() ? Minecraft.getMinecraft().player : ctx.getServerHandler().player);
            Entity entity = player.world.getEntityByID(message.dragonId);
            if (entity instanceof EntityTameableDragon) {
                EntityTameableDragon dragon = (EntityTameableDragon) entity;

                if (message.isHoverCancel) {
                    dragon.setUnHovered(!dragon.isUnHovered());
                    player.sendStatusMessage(new TextComponentTranslation(DMUtils.translateToLocal("msg.dragon.toggleHover") + (dragon.isUnHovered() ? ": On" : ": Off")), false);
                }

                if (message.isFollowYaw) {
                    dragon.setFollowYaw(!dragon.followYaw());
                    player.sendStatusMessage(new TextComponentTranslation(DMUtils.translateToLocal("msg.dragon.toggleFollowYaw") + (dragon.followYaw() ? ": On" : ": Off")), false);
                }

                if (message.locky) {
                    dragon.setYLocked(!dragon.isYLocked());
                    player.sendStatusMessage(new TextComponentTranslation(DMUtils.translateToLocal("msg.dragon.toggleYLock") + (dragon.isYLocked() ? ": On" : ": Off")), false);
                }

                if (message.down) dragon.setGoingDown(true);
                else dragon.setGoingDown(false);

                if (message.isBoosting) dragon.setBoosting(true);
                else dragon.setBoosting(false);
            }
            return null;
        }
    }
}
