package com.TheRPGAdventurer.ROTD.client.render;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.model.DragonModelHead;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.client.tile.TileEntityDragonHead;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.DragonBreed;

import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.EnumMap;
import java.util.Map;

public class TileEntityDragonRenderer extends TileEntitySpecialRenderer<TileEntityDragonHead> {

    private final DragonModelHead dragonHead = new DragonModelHead();
    public static TileEntityDragonRenderer instance;
    private final Map<EnumDragonBreed, DefaultBreedRendererTileEntity> breedRenderers = new EnumMap<>(EnumDragonBreed.class);     
    
    public TileEntityDragonRenderer() {
    	super();
     
        // create default breed renderers
        for (EnumDragonBreed breed : EnumDragonBreed.values()) {
            if (!breedRenderers.containsKey(breed)) {
                breedRenderers.put(breed, new DefaultBreedRendererTileEntity(this, breed));
            }
        }
	}

    public void render(TileEntityDragonHead te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing enumfacing = EnumFacing.getFront(te.getBlockMetadata() & 7);
        //float f = te.getAnimationProgress(partialTicks);
       // float f = te.getAnimationProgress(partialTicks);
        this.renderSkull((float)x, (float)y, (float)z, enumfacing, (float)(te.getSkullRotation() * 360) / 16.0F, destroyStage, partialTicks, te.breed); //, f
    }

    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
        super.setRendererDispatcher(rendererDispatcherIn);
        instance = this;
    }

    public void renderSkull(float x, float y, float z, EnumFacing facing, float rotationIn, int destroyStage, float animateTicks, EnumDragonBreed breed) {
        ModelBase modelbase = this.dragonHead;
        String skin = breed.getBreed().getSkin();
        ResourceLocation maleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/bodym.png");

        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
           this.bindTexture(maleBodyTexture);
            
        }

      //  GlStateManager.pushMatrix();
     //   GlStateManager.disableCull();

        if (facing == EnumFacing.UP) {
            GlStateManager.translate(x + 0.5F, y, z + 0.5F);
        } else {
            switch (facing)
            {
                case NORTH:
                    GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.74F);
                    break;
                case SOUTH:
                    GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.26F);
                    rotationIn = 180.0F;
                    break;
                case WEST:
                    GlStateManager.translate(x + 0.74F, y + 0.25F, z + 0.5F);
                    rotationIn = 270.0F;
                    break;
                case EAST:
                default:
                    GlStateManager.translate(x + 0.26F, y + 0.25F, z + 0.5F);
                    rotationIn = 90.0F;
            }
        }

        float f = 0.0625F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();


        modelbase.render((Entity)null, animateTicks, 0.0F, 0.0F, rotationIn, 0.0F, 0.0625F);
        GlStateManager.popMatrix();

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
