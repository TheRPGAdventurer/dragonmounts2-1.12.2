package com.TheRPGAdventurer.ROTD.client.render;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.model.ModelDragonCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.EntityDragonCarriage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDragonCarriage extends Render<EntityDragonCarriage> {
	
    private static final ResourceLocation CARRIAGE_TEXTURES = new ResourceLocation(DragonMounts.MODID, "textures/entities/dragon_carriage/dragon_carriage.png");
    /** instance of modelCarriage for rendering */
    protected ModelBase modelCarriage = new ModelDragonCarriage();  

    public RenderDragonCarriage(RenderManager renderManagerIn) {
        super(renderManagerIn); 
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityDragonCarriage entity, double x, double y, double z, float yaw, float partialTicks) {   
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    	this.bindEntityTexture(entity);
//    	this.setupRotation(entity, yaw, partialTicks);
    	this.renderModel(entity, x, y, z, yaw, partialTicks); 
    	GlStateManager.disableBlend();  

        super.doRender(entity, x, y, z, yaw, partialTicks);
    }
    
    protected void renderModel(EntityDragonCarriage entity, double x, double y, double z, float yaw, float partialTicks) {
        GlStateManager.pushMatrix();
        Vec3d look = entity.getLook(partialTicks);
        GlStateManager.translate((float) x, (float) y + 0.4F, (float) z);
        GlStateManager.rotate(180 - yaw, 180, 1, 0); 
        GlStateManager.scale(0.8, 0.8, 0.8);
        modelCarriage.render(entity, 0, 0, 0, 0, 0, 0.0625F); 
        GlStateManager.popMatrix(); 
    } 

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityDragonCarriage entity)
    {
        return CARRIAGE_TEXTURES;
    }

    protected void renderCartContents(EntityDragonCarriage p_188319_1_, float partialTicks, IBlockState p_188319_3_)
    {
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(p_188319_3_, p_188319_1_.getBrightness());
        GlStateManager.popMatrix();
    }
}