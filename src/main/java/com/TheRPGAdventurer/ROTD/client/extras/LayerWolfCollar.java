package com.TheRPGAdventurer.ROTD.client.extras;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerWolfCollar implements LayerRenderer<EntityWolf>
{
    private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    private final RenderWolfMODIFIED wolfRenderer;

    public LayerWolfCollar(RenderWolfMODIFIED wolfRendererIn)
    {
        this.wolfRenderer = wolfRendererIn;
    }

    public void doRenderLayer(EntityWolf entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible())
        {
            this.wolfRenderer.bindTexture(WOLF_COLLAR);
            float[] afloat = entitylivingbaseIn.getCollarColor().getColorComponentValues();
            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
            this.wolfRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}