package com.TheRPGAdventurer.ROTD.server.network;

import com.TheRPGAdventurer.ROTD.server.entity.helper.IceEntityProperties;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageFrozen extends AbstractMessage<MessageFrozen> {

	public int entityId;
	public boolean isFrozen;

	public MessageFrozen(int entityId, boolean isStone) {
		this.entityId = entityId;
		this.isFrozen = isStone;
	}

	public MessageFrozen() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityId = buf.readInt();
		isFrozen = buf.readBoolean();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeBoolean(isFrozen);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientReceived(Minecraft client, MessageFrozen message, EntityPlayer player, MessageContext messageContext) {
		Entity entity = player.world.getEntityByID(message.entityId);
		if (entity instanceof EntityLiving) {
			IceEntityProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(entity, IceEntityProperties.class);
			properties.isStone = message.isFrozen;
		}
	}

	@Override
	public void onServerReceived(MinecraftServer server, MessageFrozen message, EntityPlayer player, MessageContext messageContext) {
		Entity entity = player.world.getEntityByID(message.entityId);
		if (entity instanceof EntityLiving) {
			IceEntityProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(entity, IceEntityProperties.class);
			properties.isStone = message.isFrozen;
		}
	}
}