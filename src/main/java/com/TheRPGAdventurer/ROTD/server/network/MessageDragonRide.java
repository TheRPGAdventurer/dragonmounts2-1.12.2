package com.TheRPGAdventurer.ROTD.server.network;

import java.util.UUID;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.reflection.PrivateAccessor;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonRide extends AbstractMessage<MessageDragonRide> implements PrivateAccessor {
	
	public double x;
	public double y;
	public double z;
	int uuid;
	
	public MessageDragonRide() {}
	
	public MessageDragonRide(double x, double y, double z, int uuid) {
		this.uuid = uuid;
		this.x = x;
		this.y = y;
		this.z = z;
		
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer packetBuf = new PacketBuffer(buf);
		uuid = buf.readInt();
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer packetBuf = new PacketBuffer(buf);
		buf.writeInt(uuid);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		
	}

	@Override
	public void onClientReceived(Minecraft arg0, MessageDragonRide arg1, EntityPlayer arg2, MessageContext arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerReceived(MinecraftServer server, MessageDragonRide arg1, EntityPlayer player, MessageContext arg3) {
		Entity entity = player.world.getEntityByID(uuid);
		if(entity instanceof EntityTameableDragon) {
			EntityTameableDragon dragon = (EntityTameableDragon) entity;
			// lift off from a jump
		     if (!dragon.isFlying()) {
		        if (entityIsJumping(dragon.getControllingPlayer())) {
		             dragon.liftOff();
		        }
		     } 
		        
		     dragon.getMoveHelper().setMoveTo(x,y,z,1.2);
			
		}
		
	}

}
