package com.TheRPGAdventurer.ROTD.client.event;

import org.lwjgl.opengl.GL11;

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.math.MathX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
			float scale = MathX.clamp(((EntityTameableDragon) player.getRidingEntity()).getScale(), 0.1f, 1f);
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
				GlStateManager.translate(0F , -1.7F, -10.8 * scale); 
			}
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
				GlStateManager.translate(0F , -0.8F, 10.8 * scale);
			}
		} else if(player.getRidingEntity() instanceof EntityCarriage) {			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
				GlStateManager.translate(0F , -0.8F, -10.8); 
			}
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
				GlStateManager.translate(0F , -0.8F, 10.8);
			}
		}
	}
}
