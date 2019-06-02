package com.TheRPGAdventurer.ROTD.client.render;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.model.ModelDragonCarriage;
import com.TheRPGAdventurer.ROTD.objects.entity.entitycarriage.EntityCarriage;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCarriage extends Render<EntityCarriage> {
	
    private static final ResourceLocation[] CARRIAGE_TEXTURES = new ResourceLocation[] {
    		new ResourceLocation(DragonMounts.MODID, "textures/entities/dragon_carriage/carriage_oak.png"),
    		new ResourceLocation(DragonMounts.MODID, "textures/entities/dragon_carriage/carriage_spruce.png"),
    		new ResourceLocation(DragonMounts.MODID, "textures/entities/dragon_carriage/carriage_birch.png"),
    		new ResourceLocation(DragonMounts.MODID, "textures/entities/dragon_carriage/carriage_jungle.png"),
    		new ResourceLocation(DragonMounts.MODID, "textures/entities/dragon_carriage/carriage_acacia.png"),
    		new ResourceLocation(DragonMounts.MODID, "textures/entities/dragon_carriage/carriage_dark_oak.png"),
    		};
    /** instance of modelCarriage for rendering */
    protected ModelBase modelCarriage = new ModelDragonCarriage();  

    public RenderCarriage(RenderManager renderManagerIn){
        super(renderManagerIn);
        this.shadowSize = 0.2F;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityCarriage entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.setupTranslation(x, y, z);
        this.setupRotation(entity, entityYaw, partialTicks);
        this.bindEntityTexture(entity);

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.modelCarriage.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    public void setupRotation(EntityCarriage entity, float entityYaw, float partialTicks) {
        GlStateManager.rotate(90.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        float f = (float)entity.getTimeSinceHit() - partialTicks;
        float f1 = entity.getDamage() - partialTicks;

        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            GlStateManager.rotate(MathHelper.sin(f) * f * f1 / 10.0F * (float)entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }

        GlStateManager.scale(-0.8F, -0.8F, 0.8F);
    }

    public void setupTranslation(double x, double y, double z) {
        GlStateManager.translate((float)x, (float)y + 0.2F, (float)z);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityCarriage entity) {
        return CARRIAGE_TEXTURES[entity.getType().ordinal()];  
    }

    public boolean isMultipass() {
        return true;
    }

}