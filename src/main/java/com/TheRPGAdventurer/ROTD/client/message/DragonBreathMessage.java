package com.TheRPGAdventurer.ROTD.client.message;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DragonBreathMessage extends AbstractMessage<DragonBreathMessage> {

	private int dragonId;
	public boolean isBreathing;
	public boolean isBoosting;
	

	public DragonBreathMessage(int dragonId, boolean isBreathing, boolean isBoosting) {
		this.dragonId = dragonId;
		this.isBreathing = isBreathing;
		this.isBoosting = isBoosting;
	}

	public DragonBreathMessage() {}

	@Override
	public void fromBytes(ByteBuf buf) {
		dragonId = buf.readInt();
		isBreathing = buf.readBoolean();
		isBoosting = buf.readBoolean();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dragonId);
		buf.writeBoolean(isBreathing);
		buf.writeBoolean(isBoosting);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientReceived(Minecraft client, DragonBreathMessage message, EntityPlayer player, MessageContext messageContext) {
	
	}

	@Override
	public void onServerReceived(MinecraftServer server, DragonBreathMessage message, EntityPlayer player, MessageContext messageContext) {
		Entity entity = player.world.getEntityByID(message.dragonId);
		if (entity instanceof EntityTameableDragon) {
			EntityTameableDragon dragon = (EntityTameableDragon) entity;
			if (message.isBreathing) {
				dragon.setUsingBreathWeapon(true);
			} else {
				dragon.setUsingBreathWeapon(false);
			}
			
			if(message.isBoosting) {
				dragon.setBoosting(true);
			} else {
				dragon.setBoosting(false);
			}
		} 
	}
}
