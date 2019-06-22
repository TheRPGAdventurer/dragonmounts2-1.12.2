package com.TheRPGAdventurer.ROTD.event;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.inits.ModKeys;
import com.TheRPGAdventurer.ROTD.objects.entity.entitycarriage.EntityCarriage;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DragonViewEvent {
    /**
     * Credit to AlexThe666 : iceandfire
     *
     * @param event
     */
    @SubscribeEvent
    public void thirdPersonCameraFix(EntityViewRenderEvent.CameraSetup event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int currentView = DragonMounts.proxy.getDragon3rdPersonView();

        if (player.getRidingEntity() instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) player.getRidingEntity();
            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
                GlStateManager.translate(0F, -0.6F * dragon.getScale(), 0);
            }

            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
                if (currentView == 0) {
                    GlStateManager.translate(0F, -1.3F * dragon.getScale(), -DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                } else if (currentView == 1) {
                    GlStateManager.translate(-4.7F, -0.08F * dragon.getScale(), -DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                } else if (currentView == 2) {
                    GlStateManager.translate(4.7F, -0.08F * dragon.getScale(), -DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                }
            }

            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                if (currentView == 0) {
                    GlStateManager.translate(0F, -1.3F * dragon.getScale(), DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                } else if (currentView == 1) {
                    GlStateManager.translate(-4.7F, -0.08F * dragon.getScale(), DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                } else if (currentView == 2) {
                    GlStateManager.translate(4.7F, -0.08F * dragon.getScale(), DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                }
            }
        } else if (player.getRidingEntity() instanceof EntityCarriage) {
            EntityCarriage carriage = (EntityCarriage) player.getRidingEntity();
            if (carriage.getRidingEntity() instanceof EntityTameableDragon) {
                EntityTameableDragon dragon = (EntityTameableDragon) carriage.getRidingEntity();
                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
                    GlStateManager.translate(0F, -0.9F, 0);
                }

                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
                    if (currentView == 0) {
                        GlStateManager.translate(0F, -1.3F * dragon.getScale(), -DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                    } else if (currentView == 1) {
                        GlStateManager.translate(4.7F, -0.08F * dragon.getScale(), -DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                    } else if (currentView == 2) {
                        GlStateManager.translate(-4.7F, -0.08F * dragon.getScale(), -DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                    }
                }

                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                    if (currentView == 0) {
                        GlStateManager.translate(0F, -1.3F * dragon.getScale(), DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                    } else if (currentView == 1) {
                        GlStateManager.translate(4.7F, -0.08F * dragon.getScale(), DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                    } else if (currentView == 2) {
                        GlStateManager.translate(-4.7F, -0.08F * dragon.getScale(), DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
                    }
                }
            } else {
                GlStateManager.translate(0F, -0.5F, -5);
            }
        }
    }

    @SubscribeEvent
    public void rideDragonGameOverlay(RenderGameOverlayEvent.Post event) {
//        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
//        new GuiDragonRide();
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (player.getRidingEntity() instanceof EntityTameableDragon) {
                EntityTameableDragon dragon = (EntityTameableDragon) player.getRidingEntity();
                //            if (player.world.isRemote) {
                if (ModKeys.dragon_change_view.isPressed()) {
                    int currentView = DragonMounts.proxy.getDragon3rdPersonView();
                    if (currentView + 1 > 2) {
                        currentView = 0;
                    } else {
                        currentView++;
                    }

                    DragonMounts.proxy.setDragon3rdPersonView(currentView);

                }
            }
        }
    }
}