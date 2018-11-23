package com.TheRPGAdventurer.ROTD.client.event;

import org.lwjgl.opengl.GL11;

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DragonViewEvent {
	
	/**
	 * Credit to AlexThe666 : iceandfire
	 * @param event
	 */
	@SubscribeEvent
	public void thirdPersonCameraFix(EntityViewRenderEvent.CameraSetup event) {
		EntityPlayer player = Minecraft.getMinecraft().player;

		if(player.getRidingEntity() instanceof EntityTameableDragon) {	
			float scale = ((EntityTameableDragon) player.getRidingEntity()).getScale();
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
				GL11.glTranslated(0F , -0.5F, -DragonMountsConfig.ThirdPerson2Zoom * scale); // 0.4, -2F
			}
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
				GL11.glTranslated(0F , -0.5F, DragonMountsConfig.ThirdPerson2Zoom * scale);
			}
		} else if(player.getRidingEntity() instanceof EntityCarriage) {
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
				GL11.glTranslated(0F , -0.5F, -DragonMountsConfig.ThirdPerson2Zoom); // 0.4, -2F
			}
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
				GL11.glTranslated(0F , -0.5F, DragonMountsConfig.ThirdPerson2Zoom);
			}
		}
	}
}
