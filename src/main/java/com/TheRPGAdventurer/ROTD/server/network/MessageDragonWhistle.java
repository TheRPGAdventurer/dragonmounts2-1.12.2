package com.TheRPGAdventurer.ROTD.server.network;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonWhistle extends AbstractMessage<MessageDragonWhistle> {
	
    private final BitSet bits = new BitSet(Byte.SIZE);
    private int previous;
	
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
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		
	}

	@Override
	public void onClientReceived(Minecraft arg0, MessageDragonWhistle arg1, EntityPlayer arg2, MessageContext arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerReceived(MinecraftServer arg0, MessageDragonWhistle arg1, EntityPlayer arg2,
			MessageContext arg3) {
		// TODO Auto-generated method stub
		
	}

}
