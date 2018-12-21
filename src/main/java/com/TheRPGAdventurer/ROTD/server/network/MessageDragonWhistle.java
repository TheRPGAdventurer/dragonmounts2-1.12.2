package com.TheRPGAdventurer.ROTD.server.network;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageDragonWhistle extends AbstractMessage<MessageDragonWhistle> {

	public int dragonId;
	public byte controlState;
	public int armor_type;

	public MessageDragonWhistle(int dragonId, byte controlState) {
		this.dragonId = dragonId;
		this.controlState = controlState;
	}

	public MessageDragonWhistle() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		dragonId = buf.readInt();
		controlState = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dragonId);
		buf.writeByte(controlState);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientReceived(Minecraft client, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {
	}

	@Override
	public void onServerReceived(MinecraftServer server, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {
		Entity entity = player.world.getEntityByID(message.dragonId);
		if (entity != null && entity instanceof EntityTameableDragon) {
			EntityTameableDragon dragon = (EntityTameableDragon) entity;
			if (dragon.isOwner(player)) {
				dragon.setWhistleState(message.controlState);
				DMUtils.getLogger().info("Current State at " + dragon.getUniqueID().toString() + "" + dragon.getWhistleState());
			}			
		} 
	}
}