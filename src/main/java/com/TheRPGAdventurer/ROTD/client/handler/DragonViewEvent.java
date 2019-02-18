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
     *
     * @param event
     */
    @SubscribeEvent
    public void thirdPersonCameraFix(EntityViewRenderEvent.CameraSetup event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int currentView = DragonMounts.proxy.getDragon3rdPersonView();
        if (player.getRidingEntity() instanceof EntityTameableDragon || player.getRidingEntity() instanceof EntityCarriage) {
            float scale = MathX.clamp(((EntityTameableDragon) player.getRidingEntity()).getScale(), 0.1f, 1f);
            double partialTicks = event.getRenderPartialTicks();

            double d3 = DragonMountsConfig.ThirdPersonZoom;

            float f = player.getEyeHeight();
            double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) partialTicks;
            double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) partialTicks + (double) f;
            double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) partialTicks;

            float f1 = player.rotationYaw;
            float f2 = player.rotationPitch;

            if (this.mc.gameSettings.thirdPersonView == 2) {
                f2 += 180.0F;
            }

            double d4 = (double) (-MathHelper.sin(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F)) * d3;
            double d5 = (double) (MathHelper.cos(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F)) * d3;
            double d6 = (double) (-MathHelper.sin(f2 * 0.017453292F)) * d3;

            for (int i = 0; i < 8; ++i) {
                float f3 = (float) ((i & 1) * 2 - 1);
                float f4 = (float) ((i >> 1 & 1) * 2 - 1);
                float f5 = (float) ((i >> 2 & 1) * 2 - 1);
                f3 = f3 * 0.1F;
                f4 = f4 * 0.1F;
                f5 = f5 * 0.1F;
                RayTraceResult raytraceresult = this.mc.world.rayTraceBlocks(
                        new Vec3d(d0 + (double) f3, d1 + (double) f4, d2 + (double) f5),
                        new Vec3d(d0 - d4 + (double) f3 + (double) f5, d1 - d6 + (double) f4, d2 - d5 + (double) f5));

                if (raytraceresult != null) {
                    double d7 = raytraceresult.hitVec.distanceTo(new Vec3d(d0, d1, d2));

                    if (d7 < d3) {
                        d3 = d7;
                    }
                }
            }


            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) { //
                GlStateManager.translate(0, -0.8, 0);
            }

            if (Minecraft.getMinecraft().gameSettings.thirdPersonView > 0) {//x = 4.7F
                if (currentView == 0) {
                    GlStateManager.translate(0F, -2.6F, -d3);
                } else if (currentView == 1) {
                    GlStateManager.translate(4.7F, -2.6F, -d3);
                } else if (currentView == 2) {
                    GlStateManager.translate(-4.7F, -2.6F, -d3);
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
