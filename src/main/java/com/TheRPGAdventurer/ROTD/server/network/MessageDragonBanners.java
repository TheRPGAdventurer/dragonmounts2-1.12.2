package com.TheRPGAdventurer.ROTD.server.network;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonBanners extends AbstractMessage<MessageDragonBanners> {
	
	public int banner1;
	public int banner2;
	public int banner3;
	public int banner4;

	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(banner1);
		
	}

	@Override
	public void onClientReceived(Minecraft arg0, MessageDragonBanners arg1, EntityPlayer arg2, MessageContext arg3) {
		
	}

	@Override
	public void onServerReceived(MinecraftServer arg0, MessageDragonBanners arg1, EntityPlayer arg2,
			MessageContext arg3) {
		
	}

}
