package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.math.MathX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
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
		Vec3d vec = player.rayTrace(10, player.ticksExisted).hitVec;
		Vec3d distToBlock = player.getLook(player.ticksExisted).subtract(vec);

		if(player.getRidingEntity() instanceof EntityTameableDragon) {	
			float scale = MathX.clamp(((EntityTameableDragon) player.getRidingEntity()).getScale(), 0.1f, 1f);
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
				GlStateManager.translate(0F - vec.x, -1.7F - vec.y, -DragonMountsConfig.ThirdPersonZoom * scale - vec.z); 
			}
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) { 
				GlStateManager.translate(0F - vec.x, -1.8F - vec.y, DragonMountsConfig.ThirdPersonZoom * scale - vec.z);
			}
		} else if(player.getRidingEntity() instanceof EntityCarriage) {			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
				GlStateManager.translate(0F - vec.x, -1.8F - vec.y, -10.8 - vec.z); 
			}
			
			if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
				GlStateManager.translate(0F - vec.x, -1.8F - vec.y, 10.8 - vec.z);
			}
		}
	}
}
