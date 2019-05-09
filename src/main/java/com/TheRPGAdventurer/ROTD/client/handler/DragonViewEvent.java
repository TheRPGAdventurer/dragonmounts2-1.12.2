package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.initialization.ModKeys;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
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
                    GlStateManager.translate(0F, -2.6F, -DragonMountsConfig.ThirdPersonZoom);
                } else if (currentView == 1) {
                    GlStateManager.translate(-4.7F, -3.6F, -DragonMountsConfig.ThirdPersonZoom);
                } else if (currentView == 2) {
                    GlStateManager.translate(4.7F, -3.6F, -DragonMountsConfig.ThirdPersonZoom);
                }
            }

            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                if (currentView == 0) {
                    GlStateManager.translate(0F, -2.6F, DragonMountsConfig.ThirdPersonZoom);
                } else if (currentView == 1) {
                    GlStateManager.translate(-4.7F, -3.6F, DragonMountsConfig.ThirdPersonZoom);
                } else if (currentView == 2) {
                    GlStateManager.translate(4.7F, -3.6F, DragonMountsConfig.ThirdPersonZoom);
                }
            }
        } else if (player.getRidingEntity() instanceof EntityCarriage) {
            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
                GlStateManager.translate(0F, -0.9F, 0);
            }

            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
                if (currentView == 0) {
                    GlStateManager.translate(0F, -2.6F, -DragonMountsConfig.ThirdPersonZoom);
                } else if (currentView == 1) {
                    GlStateManager.translate(4.7F, -3.6F, -DragonMountsConfig.ThirdPersonZoom);
                } else if (currentView == 2) {
                    GlStateManager.translate(-4.7F, -3.6F, -DragonMountsConfig.ThirdPersonZoom);
                }
            }

            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                if (currentView == 0) {
                    GlStateManager.translate(0F, -2.6F, DragonMountsConfig.ThirdPersonZoom);
                } else if (currentView == 1) {
                    GlStateManager.translate(4.7F, -3.6F, DragonMountsConfig.ThirdPersonZoom);
                } else if (currentView == 2) {
                    GlStateManager.translate(-4.7F, -3.6F, DragonMountsConfig.ThirdPersonZoom);
                }
            }
        }
    }

//    @SubscribeEvent
//    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
//        if (event.getEntityLiving() instanceof EntityPlayer) {
//            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
//            if (player.world.isRemote) {
//                if (ModKeys.dragon_change_view.isPressed()) {
//                    int currentView = DragonMounts.proxy.getDragon3rdPersonView();
//                    if (currentView + 1 > 2) {
//                        currentView = 0;
//                    } else {
//                        currentView++;
//                    }
//
//                    DragonMounts.proxy.setDragon3rdPersonView(currentView);
//
//                } else if (ModKeys.FOLLOW_YAW.isPressed()) {
//                    DragonMounts.proxy.setDragonFollowYaw(!DragonMounts.proxy.getDragonFollowYaw());
//                } else if (ModKeys.KEY_HOVERCANCEL.isPressed()) {
//                	DragonMounts.proxy.setDragonHover(!DragonMounts.proxy.getDragonHover());
//                }
//            }
//        }
//    }

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

                } else if (ModKeys.FOLLOW_YAW.isPressed()) {
//                    DragonMounts.proxy.setDragonFollowYaw(!DragonMounts.proxy.getDragonFollowYaw());
                    dragon.setFollowYaw(!dragon.followYaw());
                } else if (ModKeys.KEY_HOVERCANCEL.isPressed()) {
//                    DragonMounts.proxy.setDragonHover(!DragonMounts.proxy.getDragonHover());
                    dragon.setUnHovered(!dragon.isUnHovered());
                } else if (ModKeys.KEY_LOCKEDY.isPressed()) {
//                    DragonMounts.proxy.setDragonHover(!DragonMounts.proxy.getDragonHover());
                    dragon.setYLocked(!dragon.isYLocked());
                }
            }
//            }
        }
    }
}