package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.client.initialization.ModKeys;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.math.MathX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DragonViewEvent {

    Minecraft mc = Minecraft.getMinecraft();

    /**
     * Credit to AlexThe666 : iceandfire
     * @param event
     */
    @SubscribeEvent
    public void thirdPersonCameraFix(EntityViewRenderEvent.CameraSetup event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int currentView = DragonMounts.proxy.getDragon3rdPersonView();

        if(player.getRidingEntity() instanceof EntityTameableDragon) {
            if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
                GlStateManager.translate(0F, -0.9F, 0);
            }

            if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
                if(currentView == 0) {
                    GlStateManager.translate(0F, -2.6F, -DragonMountsConfig.ThirdPersonZoom);
                } else if(currentView == 1) {
                    GlStateManager.translate(-4.7F, -3.6F, -DragonMountsConfig.ThirdPersonZoom);
                } else if(currentView == 2) {
                    GlStateManager.translate(4.7F, -3.6F, -DragonMountsConfig.ThirdPersonZoom);
                }
            }

            if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                if(currentView == 0) {
                    GlStateManager.translate(0F , -2.6F , DragonMountsConfig.ThirdPersonZoom);
                } else if(currentView == 1) {
                    GlStateManager.translate(-4.7F , -3.6F , DragonMountsConfig.ThirdPersonZoom);
                }else if(currentView == 2) {
                    GlStateManager.translate(4.7F , -3.6F , DragonMountsConfig.ThirdPersonZoom);
                }
            }
        } else if(player.getRidingEntity() instanceof EntityCarriage) {
            if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
                GlStateManager.translate(0F, -0.9F, 0);
            }

            if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
                if(currentView == 0) {
                    GlStateManager.translate(0F, -2.6F, -DragonMountsConfig.ThirdPersonZoom);
                } else if(currentView == 1) {
                    GlStateManager.translate(-4.7F, -3.6F, -DragonMountsConfig.ThirdPersonZoom);
                } else if(currentView == 2) {
                    GlStateManager.translate(4.7F, -3.6F, -DragonMountsConfig.ThirdPersonZoom);
                }
            }

            if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                if(currentView == 0) {
                    GlStateManager.translate(0F , -2.6F , DragonMountsConfig.ThirdPersonZoom);
                } else if(currentView == 1) {
                    GlStateManager.translate(-4.7F , -3.6F , DragonMountsConfig.ThirdPersonZoom);
                }else if(currentView == 2) {
                    GlStateManager.translate(4.7F , -3.6F , DragonMountsConfig.ThirdPersonZoom);
                }
            }
        }
    }

    /**
     * Thanks for the code AlexThe666
     * @param event
     */
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (player.world.isRemote && ModKeys.dragon_change_view.isPressed()) {
                int currentView = DragonMounts.proxy.getDragon3rdPersonView();
                if(currentView + 1 > 2){
                    currentView = 0;
                } else{
                    currentView++;
                }
                DragonMounts.proxy.setDragon3rdPersonView(currentView);
            }
        }
    }
}