package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageDragonBreath extends AbstractMessage<MessageDragonBreath> {

	private int dragonId;
	public boolean isBreathing;
	

	public MessageDragonBreath(int dragonId, boolean isBreathing) {
		this.dragonId = dragonId;
		this.isBreathing = isBreathing;
	}

	public MessageDragonBreath() {}

	@Override
	public void fromBytes(ByteBuf buf) {
		dragonId = buf.readInt();
		isBreathing = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dragonId);
		buf.writeBoolean(isBreathing);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientReceived(Minecraft client, MessageDragonBreath message, EntityPlayer player, MessageContext messageContext) {
	
	}

	@Override
	public void onServerReceived(MinecraftServer server, MessageDragonBreath message, EntityPlayer player, MessageContext messageContext) {
		Entity entity = player.world.getEntityByID(message.dragonId);
		if (entity instanceof EntityTameableDragon) {
			EntityTameableDragon dragon = (EntityTameableDragon) entity;
			if (message.isBreathing) {
				dragon.setUsingBreathWeapon(true);
			} else {
				dragon.setUsingBreathWeapon(false);
			}
		} 
	}
}
