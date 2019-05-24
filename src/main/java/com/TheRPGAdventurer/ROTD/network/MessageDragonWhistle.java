package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.inits.ModSounds;
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
	EntityTameableDragon dragon;

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

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientReceived(Minecraft client, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {

	}

	@Override
	public void onServerReceived(MinecraftServer server, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {
		player.getEntityWorld().playSound((EntityPlayer) null, player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 4, 1);
		if (!player.world.isRemote) {
			Entity entity = server.getEntityFromUuid(dragonId);
			EntityTameableDragon dragon = (EntityTameableDragon) entity;
			if (entity != null) {
				if (entity instanceof EntityTameableDragon && dragon.isOwner(player)) dragon.setWhistleState(message.controlState);
			} else player.sendStatusMessage(new TextComponentTranslation("whistle.msg.fail"), true);
		}
	}
}