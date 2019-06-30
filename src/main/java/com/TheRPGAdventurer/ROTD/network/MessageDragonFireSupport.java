package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.inits.ModSounds;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

public class MessageDragonFireSupport extends AbstractMessage<MessageDragonFireSupport> {

    public UUID dragonId;
    EntityTameableDragon dragon;

    public MessageDragonFireSupport(UUID dragonId) {
        this.dragonId = dragonId;
    }

    public MessageDragonFireSupport() {
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



    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, MessageDragonFireSupport message, EntityPlayer player, MessageContext messageContext) {
    }

    @Override
    public void onServerReceived(MinecraftServer server, MessageDragonFireSupport message, EntityPlayer player, MessageContext messageContext) {
        if (!player.world.isRemote) {
            Entity entity = server.getEntityFromUuid(dragonId);
            EntityTameableDragon dragon = (EntityTameableDragon) entity;
            if (entity != null) {
                if (entity instanceof EntityTameableDragon && dragon.isOwner(player)) {
                     dragon.setfiresupport(!dragon.firesupport());
                }

                player.world.playSound(null, player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 1, 1);
            } else player.sendStatusMessage(new TextComponentTranslation("whistle.msg.fail"), true);
        }
    }
}