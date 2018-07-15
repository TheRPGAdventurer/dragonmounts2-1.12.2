package com.TheRPGAdventurer.ROTD.server.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class DragonBreathMessage implements IMessage {
	
	public boolean breathing;
	public boolean packetIsValid;
    private static boolean printedError = false;
	
	/**
	 * Creates a message saying 'nothing is targeted'
	 * @return the message for sending
	 */
	public static DragonBreathMessage createUntargetMessage() {
		DragonBreathMessage retval = new DragonBreathMessage();
	    retval.breathing = false;
	    retval.packetIsValid = true;
	    return retval;
	}

	/** Creates a message specifying the current target
	  * @param i_target the target
	  * @return the message for sending
	  */
	public static DragonBreathMessage createTargetMessage() {
		DragonBreathMessage retval = new DragonBreathMessage();
	   retval.breathing = true;
	   retval.packetIsValid = true;
	   return retval;
	}

	  @Override
	  public void fromBytes(ByteBuf buf) {
	    packetIsValid = false;
	    try {
	      breathing = buf.readBoolean();
	 //     if (breathing) {
	 //       target = BreathWeaponTarget.fromBytes(buf);
	 //     }
	    } catch (IndexOutOfBoundsException ioe) {
	      if (printedError) return;
	      printedError = true;
	      System.err.println("Exception while reading DragonTargetMessage: " + ioe);
	      return;
	    } catch (IllegalArgumentException ioe) {
	      if (printedError) return;
	      printedError = true;
	      System.err.println("Exception while reading DragonTargetMessage: " + ioe);
	      return;
	    }
	    packetIsValid = true;
	  }


	  @Override
	  public void toBytes(ByteBuf buf) {
	    buf.writeBoolean(breathing);
	    if (!breathing) return;
	  }
	  
	    // create a new message (used by SimpleNetworkWrapper)
	  public DragonBreathMessage() {
	     packetIsValid = false;
	  }

	  public boolean isPacketIsValid() {
	    return packetIsValid;
	  }

	  public boolean isBreathing() {
	    return breathing;
	  }

}
