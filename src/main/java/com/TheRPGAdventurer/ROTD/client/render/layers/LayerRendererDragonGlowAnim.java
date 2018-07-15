/*
** 2016 April 07
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.client.render.layers;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_SRC_COLOR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE;

import com.TheRPGAdventurer.ROTD.client.model.DragonModel;
import com.TheRPGAdventurer.ROTD.client.model.DragonModelMode;
import com.TheRPGAdventurer.ROTD.client.render.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.client.renderer.GlStateManager;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class LayerRendererDragonGlowAnim extends LayerRendererDragon {

    public LayerRendererDragonGlowAnim(DragonRenderer renderer, DefaultDragonBreedRenderer breedRenderer, DragonModel model) {
        super(renderer, breedRenderer, model);
    }

    @Override
    public void doRenderLayer(EntityTameableDragon dragon, float moveTime,
            float moveSpeed, float partialTicks, float ticksExisted, float lookYaw,
            float lookPitch, float scale) {
        boolean invisible = dragon.isInvisible();
        GlStateManager.depthMask(!invisible);
        
        renderer.bindTexture(breedRenderer.getGlowAnimTexture());
        
        double ticks = dragon.ticksExisted + partialTicks;
        
        float brightness = (1 + (float)Math.sin(ticks * 0.1f)) * 0.25f + 0.5f;
        GlStateManager.color(brightness, brightness, brightness, 1);
        GlStateManager.blendFunc(GL_SRC_COLOR, GL_ONE);
        
        disableLighting();
        
        model.setMode(DragonModelMode.WINGS_ONLY);
        model.render(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
        
        GlStateManager.matrixMode(GL_TEXTURE);
        GlStateManager.loadIdentity();

        double scrollX = Math.sin(ticks * 0.01) * 0.4;
        double scrollY = Math.cos(ticks * 0.01) * 0.4;
        GlStateManager.translate(scrollX, scrollY, 0);
        GlStateManager.matrixMode(GL_MODELVIEW);
        GlStateManager.enableBlend();
        
        GlStateManager.disableAlpha();
        model.setMode(DragonModelMode.NO_WINGS);
        model.render(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
        GlStateManager.enableAlpha();
        
        enableLighting(dragon.getBrightnessForRender());
        
        GlStateManager.matrixMode(GL_TEXTURE);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(GL_MODELVIEW);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(invisible);
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
}
