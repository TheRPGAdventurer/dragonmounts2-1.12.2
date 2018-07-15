package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.client.initialization.ModKeys;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.network.DragonBreathMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class DragonBreathHelper {
	
	public boolean triggerHeld = false;
	private static DragonBreathHelper instance = null;
	private SimpleNetworkWrapper network;
	
    private DragonBreathHelper(SimpleNetworkWrapper i_network) {
		network = i_network;		    
	}
	
	static public DragonBreathHelper createSingleton(SimpleNetworkWrapper i_network) {
	    instance = new DragonBreathHelper(i_network);
		return instance;
	}

	static public DragonBreathHelper getInstance() {
		 return instance;
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent evt) {
		if (evt.phase != ClientTickEvent.Phase.START) return;
	    EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;
	    if (entityPlayerSP == null) return;
	    
	    boolean oldTriggerHeld = triggerHeld;
	    
	    if(!(entityPlayerSP.getRidingEntity() instanceof EntityTameableDragon)) {
	    	triggerHeld = false;
	    } else {
	    	triggerHeld = ModKeys.KEY_BREATH.isKeyDown();
	    }
	    
	    DragonBreathMessage message = null;
	    if(triggerHeld) {
	    	message = DragonBreathMessage.createTargetMessage();
	    } else {
	    	message = DragonBreathMessage.createUntargetMessage();
	    }
	}
}
