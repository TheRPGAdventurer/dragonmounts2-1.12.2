package com.TheRPGAdventurer.ROTD.server.network;

import java.util.BitSet;
import java.util.logging.LogManager;

import org.apache.logging.log4j.Logger;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageDragonControl extends AbstractMessage<MessageDragonControl> {

    private final BitSet bits = new BitSet(Byte.SIZE);
    private int previous;
    private static final Logger L = DMUtils.getLogger();
    
    public BitSet getFlags() {
        return bits;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        fromInteger(buf.readUnsignedByte());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(toInteger());
    }
    
    public void fromInteger(int value) { // ? BitSet already has inbuilt to do this? leave as is...
        bits.clear();
        for (int i = 0; i < bits.size(); i++) {
            if ((value & (1 << i)) != 0) {
                bits.set(i);
            }
        }
    }
    
    public int toInteger() {    // ? BitSet already has inbuilt to do this?
        int value = 0;
        for (int i = 0; i < bits.length(); i++) {
            value += bits.get(i) ? (1 << i) : 0;
        }
        return value;
    }

    public boolean hasChanged() {
        int current = toInteger();
        boolean changed = previous != current;
        previous = current;
        return changed;
    }

	@Override
	public void onClientReceived(Minecraft arg0, MessageDragonControl arg1, EntityPlayer arg2, MessageContext arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerReceived(MinecraftServer arg0, MessageDragonControl message, EntityPlayer arg2, MessageContext ctx) {
		if (ctx.side != Side.SERVER) {
            L.warn("DragonControlMessage received on wrong side:" + ctx.side);
            return;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that WorldServer exists.
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
        if (sendingPlayer == null) {
            L.warn("EntityPlayerMP was null when DragonControlMessage was received");
            return;
        }

        // This code creates a new task which will be executed by the server during the next tick,
        //  for example see MinecraftServer.updateTimeLightAndEntities(), just under section
        //      this.theProfiler.startSection("jobs");
        //  In this case, the task is to call messageHandlerOnServer.processMessage(message, sendingPlayer)
        final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
        playerWorldServer.addScheduledTask(new Runnable() {
            public void run() {
                processMessage(message, sendingPlayer);
            }
        });

        return;
    }

	
    // This message is called from the Server thread.
    void processMessage(MessageDragonControl message, EntityPlayerMP sendingPlayer) {
        if (sendingPlayer.getRidingEntity() instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon)sendingPlayer.getRidingEntity();
            dragon.setControlFlags(message.getFlags());
        }
    }

}
