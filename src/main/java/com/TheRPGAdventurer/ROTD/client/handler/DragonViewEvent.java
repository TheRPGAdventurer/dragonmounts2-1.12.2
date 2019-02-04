package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.client.initialization.ModKeys;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.math.MathX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DragonViewEvent {
	
	/**
	 * Credit to AlexThe666 : iceandfire
	 * @param event
	 */
	@SubscribeEvent
	public void thirdPersonCameraFix(EntityViewRenderEvent.CameraSetup event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		int currentView = DragonMounts.proxy.getDragon3rdPersonView();

		if(player.getRidingEntity() instanceof EntityTameableDragon) {	
			float scale = MathX.clamp(((EntityTameableDragon) player.getRidingEntity()).getScale(), 0.1f, 1f);
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
				if(currentView == 1) {
				  GlStateManager.translate(0F, -1.6F, -DragonMountsConfig.ThirdPersonZoom); 				
				} else if(currentView == 2) {
					GlStateManager.translate(-3.7F, -1.6F, -DragonMountsConfig.ThirdPersonZoom); 				
				} else if(currentView == 3) {
					GlStateManager.translate(3.7F, -1.6F, -DragonMountsConfig.ThirdPersonZoom); 				
				}
			}
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) { 
				if(currentView == 1) {
				GlStateManager.translate(0F , -1.6F , DragonMountsConfig.ThirdPersonZoom);
				} else if(currentView == 2) {
					GlStateManager.translate(-3.7F , -1.6F , DragonMountsConfig.ThirdPersonZoom);
				}else if(currentView == 3) {
					GlStateManager.translate(3.7F , -1.6F , DragonMountsConfig.ThirdPersonZoom);
				}
			}
		} else if(player.getRidingEntity() instanceof EntityCarriage) {			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
				if(currentView == 1) {
				  GlStateManager.translate(0F, -1.6F, -DragonMountsConfig.ThirdPersonZoom); 				
				} else if(currentView == 2) {
					GlStateManager.translate(-3.7F, -1.6F, -DragonMountsConfig.ThirdPersonZoom); 				
				} else if(currentView == 3) {
					GlStateManager.translate(3.7F, -1.6F, -DragonMountsConfig.ThirdPersonZoom); 				
				}
			}
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) { 
				if(currentView == 1) {
				GlStateManager.translate(0F , -1.6F, DragonMountsConfig.ThirdPersonZoom);
				} else if(currentView == 2) {
					GlStateManager.translate(-3.7F, -1.6F, DragonMountsConfig.ThirdPersonZoom);
				}else if(currentView == 3) {
					GlStateManager.translate(3.7F, -1.6F, DragonMountsConfig.ThirdPersonZoom);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.world.isRemote && ModKeys.dragon_change_view.isPressed()) {
				int currentView = DragonMounts.proxy.getDragon3rdPersonView();
				if(currentView + 1 > 3){
					currentView = 1;
				}else{
					currentView++;
				}
				DragonMounts.proxy.setDragon3rdPersonView(currentView);
			}
		}
	}
}
