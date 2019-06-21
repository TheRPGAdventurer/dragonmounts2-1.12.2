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

public class MessageDragonWhistle extends AbstractMessage<MessageDragonWhistle> {

    public UUID dragonId;
    public byte controlState;
    public boolean firesupport;
    EntityTameableDragon dragon;

    public MessageDragonWhistle(UUID dragonId, byte controlState, boolean firesupport) {
        this.dragonId = dragonId;
        this.controlState = controlState;
        this.firesupport = firesupport;
    }

    public MessageDragonWhistle() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        dragonId = packetBuf.readUniqueId();
        controlState = buf.readByte();
        firesupport = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        packetBuf.writeUniqueId(dragonId);
        buf.writeByte(controlState);
        buf.writeBoolean(firesupport);

    }

    /**
     * Play Sound on the client only; dont let anyone else hear!
     * <p>
     * Doesnt seem to work in {@code onClientRecieved()}...
     *
     * @param player
     */
    @SideOnly(Side.CLIENT)
    private void clientWhistleSound(EntityPlayer player) {
        player.world.playSound(null, player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 4, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {
    }

    @Override
    public void onServerReceived(MinecraftServer server, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {
        clientWhistleSound(player);
        if (!player.world.isRemote) {
            Entity entity = server.getEntityFromUuid(dragonId);
            EntityTameableDragon dragon = (EntityTameableDragon) entity;
            if (entity != null) {
                if (entity instanceof EntityTameableDragon && dragon.isOwner(player)) {
                    dragon.setWhistleState(message.controlState);
                    if(message.firesupport) dragon.setfiresupport(!dragon.firesupport());
                }
            } else player.sendStatusMessage(new TextComponentTranslation("whistle.msg.fail"), true);
        }
    }
}