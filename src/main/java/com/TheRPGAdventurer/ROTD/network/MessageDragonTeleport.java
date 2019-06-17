package com.TheRPGAdventurer.ROTD.network;

import java.util.UUID;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageDragonTeleport extends AbstractMessage<MessageDragonTeleport> {
	public UUID dragonId;
    public byte controlState;
    EntityTameableDragon dragon;

    public MessageDragonTeleport(UUID dragonId) {
        this.dragonId = dragonId;
    }

    public MessageDragonTeleport() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        dragonId = packetBuf.readUniqueId();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        packetBuf.writeUniqueId(dragonId);

    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, MessageDragonTeleport message, EntityPlayer player, MessageContext messageContext) {}

    @Override
    public void onServerReceived(MinecraftServer server, MessageDragonTeleport message, EntityPlayer player, MessageContext messageContext) {
        World world = player.world;
        if (world.isRemote) return;
        Entity entity = server.getEntityFromUuid(dragonId);
        if (entity != null && entity instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) entity;
            
            //Get Blockpos by raytracing from player for dragon teleport
            float f = 1.0F;
            float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
            float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
            double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
            double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + 1.62D - (double)player.getEyeHeight();
            double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
            Vec3d vec3d = new Vec3d(d0, d1, d2);
            float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
            float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
            float f5 = -MathHelper.cos(-f1 * 0.017453292F);
            float f6 = MathHelper.sin(-f1 * 0.017453292F);
            float f7 = f4 * f5;
            float f8 = f3 * f5;
            double d3 = 5.0D;
            Vec3d vec31 = vec3d.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
            RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec31, true);
            if (raytraceresult == null) {
                player.sendStatusMessage(new TextComponentTranslation("item.whistle.nullBlockPos"), true);
                return; //suppress null blockpos warnings
            }
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
            	BlockPos rayresult = raytraceresult.getBlockPos();
            	dragon.setPosition(rayresult.getX(), rayresult.getY() + 1, rayresult.getZ());
                world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 1, 1);
                dragon.setnothing(true);
            }
        }
    }
}