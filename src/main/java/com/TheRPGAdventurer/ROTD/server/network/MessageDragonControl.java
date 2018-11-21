package com.TheRPGAdventurer.ROTD.server.network;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonControl extends AbstractMessage<MessageDragonControl> {

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClientReceived(Minecraft arg0, MessageDragonControl arg1, EntityPlayer arg2, MessageContext arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerReceived(MinecraftServer arg0, MessageDragonControl arg1, EntityPlayer arg2,
			MessageContext arg3) {
		// TODO Auto-generated method stub
		
	}

}
