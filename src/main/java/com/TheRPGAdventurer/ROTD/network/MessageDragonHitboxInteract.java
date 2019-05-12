package com.TheRPGAdventurer.ROTD.network;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonHitboxInteract extends AbstractMessage<MessageDragonHitboxInteract> {
	
	public int entityID;
	public float damage;
	
	public MessageDragonHitboxInteract(int entityID, float damage) {
		this.entityID = entityID;
		this.damage = damage;
	}
	
	public MessageDragonHitboxInteract() {}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
		damage = buf.readFloat();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeFloat(damage);
		
	}

	@Override
	public void onClientReceived(Minecraft client, MessageDragonHitboxInteract message, EntityPlayer player, MessageContext messageContext) {
		Entity entity = player.world.getEntityByID(message.entityID);
		if(entity != null && entity instanceof EntityLivingBase) {
			EntityLivingBase base = (EntityLivingBase) entity;
			if(message.damage > 0F) {
			   base.attackEntityFrom(DamageSource.causeMobDamage(player), damage);
			} else {
				base.processInitialInteract(player, player.getActiveHand());
			}
		}
	}

	@Override
	public void onServerReceived(MinecraftServer server, MessageDragonHitboxInteract message, EntityPlayer player, MessageContext messageContext) {
		Entity entity = player.world.getEntityByID(message.entityID);
		if(entity != null && entity instanceof EntityLivingBase) {
			EntityLivingBase base = (EntityLivingBase) entity;
			if(message.damage > 0F) {
			   base.attackEntityFrom(DamageSource.causeMobDamage(player), damage);
			} else {
				base.processInitialInteract(player, player.getActiveHand());
			}
		}
	}
}
