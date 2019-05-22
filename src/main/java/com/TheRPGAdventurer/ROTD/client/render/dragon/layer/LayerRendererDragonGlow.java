package com.TheRPGAdventurer.ROTD.client.render.dragon.layer;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModelMode;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.entity.helper.EnumDragonLifeStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import static org.lwjgl.opengl.GL11.GL_ONE;

/**
 * Created by EveryoneElse on 14/06/2015.
 */
public class LayerRendererDragonGlow extends LayerRendererDragon {

    public LayerRendererDragonGlow(DragonRenderer renderer, DefaultDragonBreedRenderer breedRenderer, DragonModel model) {
        super(renderer, breedRenderer, model);
    }

    @Override
    public void doRenderLayer(EntityTameableDragon dragon, float moveTime, float moveSpeed, float partialTicks, float ticksExisted, float lookYaw, float lookPitch, float scale) {
        if (dragon.getLifeStageHelper().getLifeStage()==EnumDragonLifeStage.HATCHLING) {
            renderer.bindTexture(dragon.isMale() ? breedRenderer.getHMaleGlowTexture() : breedRenderer.getHFemaleGlowTexture());
        } else {
            renderer.bindTexture(dragon.isMale() ? breedRenderer.getMaleGlowTexture() : breedRenderer.getFemaleGlowTexture());
        }

        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_ONE, GL_ONE);
        GlStateManager.color(1, 1, 1, 1);

        disableLighting();
        model.setMode(DragonModelMode.FULL);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        model.render(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        enableLighting(dragon.getBrightnessForRender());

        GlStateManager.disableBlend();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();

        if (dragon.getBreedType()==EnumDragonBreed.ENCHANT) {
            renderEnchantedGlint(this.renderer, dragon, model, moveTime, moveSpeed, partialTicks, ticksExisted, lookYaw, lookPitch, scale);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
