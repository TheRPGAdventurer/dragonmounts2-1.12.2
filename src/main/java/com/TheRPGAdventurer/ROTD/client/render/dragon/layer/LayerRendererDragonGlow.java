package com.TheRPGAdventurer.ROTD.client.render.dragon.layer;

import org.lwjgl.opengl.GL11;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.client.renderer.OpenGlHelper;

/**
 * Created by EveryoneElse on 14/06/2015.
 */
public class LayerRendererDragonGlow extends LayerRendererDragon {

    public LayerRendererDragonGlow(DragonRenderer renderer,
            DefaultDragonBreedRenderer breedRenderer, DragonModel model) {
        super(renderer, breedRenderer, model);
    }

    @Override
    public void doRenderLayer(EntityTameableDragon dragon, float moveTime,
            float moveSpeed, float partialTicks, float ticksExisted, float lookYaw,
            float lookPitch, float scale) {
        renderer.bindTexture(dragon.isMale() ? breedRenderer.getMaleGlowTexture() : breedRenderer.getFemaleGlowTexture());
        model.renderPass = DragonModel.RenderPass.GLOW;
        
        try {
         GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);

         GL11.glEnable(GL11.GL_BLEND);
         GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
         GL11.glDisable(GL11.GL_LIGHTING);      // use full lighting
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 65536, 0);

         model.render(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
       } finally {
         GL11.glPopAttrib();
       }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
