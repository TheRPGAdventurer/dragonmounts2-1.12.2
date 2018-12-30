package com.TheRPGAdventurer.ROTD.server.network;

import java.util.UUID;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageDragonWhistle extends AbstractMessage<MessageDragonWhistle> {

	public UUID dragonId;
	public byte controlState;
	public int armor_type;

	public MessageDragonWhistle(UUID dragonId, byte controlState) {
		this.dragonId = dragonId;
		this.controlState = controlState;
	}

	public MessageDragonWhistle() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer packetBuf = (PacketBuffer) buf;
		dragonId = packetBuf.readUniqueId();
		controlState = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer packetBuf = (PacketBuffer) buf;
		packetBuf.writeUniqueId(dragonId);
		buf.writeByte(controlState);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientReceived(Minecraft client, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {
	}

	@Override
	public void onServerReceived(MinecraftServer server, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {
		World world = player.world; 
		if(world instanceof WorldServer) {
		  WorldServer worldServer = (WorldServer) world;
	      Entity entity = worldServer.getEntityFromUuid(dragonId);
		  if (entity != null && entity instanceof EntityTameableDragon) {
			  EntityTameableDragon dragon = (EntityTameableDragon) entity;
			  if (dragon.isOwner(player)) {
				  dragon.setWhistleState(message.controlState);
				  DMUtils.getLogger().info("Current State at " + dragon.getBreed().toString() + "" + dragon.getWhistleState());
			   }
			}			
		} 
	}
}