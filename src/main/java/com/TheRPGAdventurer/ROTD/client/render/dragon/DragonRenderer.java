/*
 ** 2011 December 10
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.client.render.dragon;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModelMode;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.DragonLifeStageHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

/**
 * Generic renderer for all dragons.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonRenderer extends RenderLiving<EntityTameableDragon> {

  public static final String TEX_BASE="textures/entities/dragon/";
  public static final ResourceLocation ENDERCRYSTAL_BEAM_TEXTURES=new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");

  private final Map<EnumDragonBreed, DefaultDragonBreedRenderer> breedRenderers=new EnumMap<>(EnumDragonBreed.class);

  public DragonRenderer(RenderManager renderManager) {
    super(renderManager, null, 2);

    // create default breed renderers
    for (EnumDragonBreed breed : EnumDragonBreed.values()) {
      if (!breedRenderers.containsKey(breed)) {
        breedRenderers.put(breed, new DefaultDragonBreedRenderer(this, breed));
      }
    }
  }

  public DefaultDragonBreedRenderer getBreedRenderer(EntityTameableDragon dragon) {
    return breedRenderers.get(dragon.getBreedType());
  }

  @Override
  public void doRender(EntityTameableDragon dragon, double x, double y, double z, float yaw, float partialTicks) {
    DragonModel breedModel=getBreedRenderer(dragon).getModel();
    breedModel.setMode(DragonModelMode.FULL);
    mainModel=breedModel;
    renderName(dragon, x, y, z);

    if (dragon.isEgg()) {
      renderEgg(dragon, x, y, z, yaw, partialTicks);
    } else {
      super.doRender(dragon, x, y, z, yaw, partialTicks);
    }

    if (dragon.healingEnderCrystal!=null) {
      this.bindTexture(ENDERCRYSTAL_BEAM_TEXTURES);
      float f=MathHelper.sin(((float) dragon.healingEnderCrystal.ticksExisted + partialTicks) * 0.2F) / 2.0F + 0.5F;
      f=(f * f + f) * 0.2F;
      renderCrystalBeams(x, y, z, partialTicks, dragon.posX + (dragon.prevPosX - dragon.posX) * (double) (1.0F - partialTicks), dragon.posY + (dragon.prevPosY - dragon.posY) * (double) (1.0F - partialTicks), dragon.posZ + (dragon.prevPosZ - dragon.posZ) * (double) (1.0F - partialTicks), dragon.ticksExisted, dragon.healingEnderCrystal.posX, (double) f + dragon.healingEnderCrystal.posY, dragon.healingEnderCrystal.posZ);
    }
  }


  @Override
  protected void renderLayers(EntityTameableDragon dragon, float moveTime, float moveSpeed, float partialTicks, float ticksExisted, float lookYaw, float lookPitch, float scale) {
    List<LayerRenderer<EntityTameableDragon>> layers=getBreedRenderer(dragon).getLayers();
    layers.forEach(layer -> {
      boolean brighnessSet=setBrightness(dragon, partialTicks, layer.shouldCombineTextures());
      layer.doRenderLayer(dragon, moveTime, moveSpeed, partialTicks, ticksExisted, lookYaw, lookPitch, scale);
      if (brighnessSet) {
        unsetBrightness();
      }
    });
  }

  public void renderBanner(ResourceLocation resourceLocation, ModelBanner bannerModel) {
    if (resourceLocation!=null) {
      this.bindTexture(resourceLocation);
      bannerModel.bannerSlate.showModel=false;
      GlStateManager.pushMatrix();
      GlStateManager.scale(0.6666667F, -0.6666667F, -0.6666667F);
      bannerModel.renderBanner();
      GlStateManager.popMatrix();
    }
  }

  /**
   * Renders the model in RenderLiving
   */
  @Override
  protected void renderModel(EntityTameableDragon dragon, float moveTime, float moveSpeed, float ticksExisted, float lookYaw, float lookPitch, float scale) {

    float death=dragon.getDeathTime() / (float) dragon.getMaxDeathTime();

    if (death > 0) {
      glPushAttrib(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

      GlStateManager.depthFunc(GL_LEQUAL);
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(GL_GREATER, death);

      bindTexture(getBreedRenderer(dragon).getDissolveTexture());
      mainModel.render(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);

      GlStateManager.alphaFunc(GL_GREATER, 0.1f);
      GlStateManager.depthFunc(GL_EQUAL);
    }

    super.renderModel(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);

    if (death > 0) {
      GlStateManager.popAttrib();
    }
  }

  protected void renderEgg(EntityTameableDragon dragon, double x, double y, double z, float pitch, float partialTicks) {
    // apply egg wiggle
    DragonLifeStageHelper lifeStage=dragon.getLifeStageHelper();
    float tickX=lifeStage.getEggWiggleX();
    float tickZ=lifeStage.getEggWiggleZ();

    float rotX=0;
    float rotZ=0;

    if (tickX > 0) {
      rotX=(float) Math.sin(tickX - partialTicks) * 8;
    }
    if (tickZ > 0) {
      rotZ=(float) Math.sin(tickZ - partialTicks) * 8;
    }
        
/*		// Aether Egg Levitate
        float l = (float) (0.1 * Math.cos(dragon.ticksExisted / (Math.PI * 6.89)) + 0.307);
        boolean lev = false;
        if (dragon.getBreedType() == EnumDragonBreed.AETHER) lev = true;
*/

    // prepare GL states
    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y /*+ (lev ? l : 0)*/, z);
    GlStateManager.rotate(rotX, 1, 0, 0);
    GlStateManager.rotate(rotZ, 0, 0, 1);
    GlStateManager.disableLighting();

    bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

    // prepare egg rendering
    Tessellator tessellator=Tessellator.getInstance();
    BufferBuilder vb=tessellator.getBuffer();
    vb.begin(GL_QUADS, DefaultVertexFormats.BLOCK);

    Block block=BlockDragonBreedEgg.DRAGON_BREED_EGG;
    IBlockState iblockstate=block.getDefaultState().withProperty(BlockDragonBreedEgg.BREED, dragon.getBreedType());
    BlockPos blockpos=dragon.getPosition();

    double tx=-blockpos.getX() - 0.5;
    double ty=-blockpos.getY();
    double tz=-blockpos.getZ() - 0.5;
    vb.setTranslation(tx, ty, tz);

    BlockRendererDispatcher brd=Minecraft.getMinecraft().getBlockRendererDispatcher();
    IBakedModel bakedModel=brd.getModelForState(iblockstate);

    // render egg
    brd.getBlockModelRenderer().renderModel(dragon.world, bakedModel, iblockstate, blockpos, vb, false);
    vb.setTranslation(0, 0, 0);

    tessellator.draw();

    // restore GL state
    GlStateManager.enableLighting();
    GlStateManager.popMatrix();
  }

  @Override
  protected void applyRotations(EntityTameableDragon dragon, float par2, float par3, float par4) {
    GlStateManager.rotate(180 - par3, 0, 1, 0);
  }

  /**
   * Allows the render to do any OpenGL state modifications necessary before
   * the model is rendered. Args: entityLiving, partialTickTime
   */
  @Override
  protected void preRenderCallback(EntityTameableDragon dragon, float partialTicks) {
    final float MODEL_SCALE_FACTOR_FOR_FULLY_GROWN = dragon.getBreed().getAdultModelRenderScaleFactor();
    // a fully grown dragon is larger than the model by this amount
    float scale=dragon.getScale() * MODEL_SCALE_FACTOR_FOR_FULLY_GROWN;
    GlStateManager.scale(scale, scale, scale);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityTameableDragon dragon) {
    DefaultDragonBreedRenderer texture = getBreedRenderer(dragon);
    return dragon.isMale() ? texture.getMaleBodyTexture(dragon.isBaby(), dragon.isAlbino(), dragon.altTextures()) : texture.getFemaleBodyTexture(dragon.isBaby(), dragon.isAlbino(), dragon.altTextures());
  }

  public static void renderCrystalBeams(double p_188325_0_, double p_188325_2_, double p_188325_4_, float p_188325_6_, double p_188325_7_, double p_188325_9_, double p_188325_11_, int p_188325_13_, double p_188325_14_, double p_188325_16_, double p_188325_18_) {
    float f=(float) (p_188325_14_ - p_188325_7_);
    float f1=(float) (p_188325_16_ - 1.0D - p_188325_9_);
    float f2=(float) (p_188325_18_ - p_188325_11_);
    float f3=MathHelper.sqrt(f * f + f2 * f2);
    float f4=MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    GlStateManager.pushMatrix();
    GlStateManager.translate((float) p_188325_0_, (float) p_188325_2_ + 2.0F, (float) p_188325_4_);
    GlStateManager.rotate((float) (-Math.atan2((double) f2, (double) f)) * (180F / (float) Math.PI) - 90.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate((float) (-Math.atan2((double) f3, (double) f1)) * (180F / (float) Math.PI) - 90.0F, 1.0F, 0.0F, 0.0F);
    Tessellator tessellator=Tessellator.getInstance();
    BufferBuilder bufferbuilder=tessellator.getBuffer();
    RenderHelper.disableStandardItemLighting();
    GlStateManager.disableCull();
    GlStateManager.shadeModel(7425);
    float f5=0.0F - ((float) p_188325_13_ + p_188325_6_) * 0.01F;
    float f6=MathHelper.sqrt(f * f + f1 * f1 + f2 * f2) / 32.0F - ((float) p_188325_13_ + p_188325_6_) * 0.01F;
    bufferbuilder.begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);

    for (int j=0; j <= 8; ++j) {
      float f7=MathHelper.sin((float) (j % 8) * ((float) Math.PI * 2F) / 8.0F) * 0.75F;
      float f8=MathHelper.cos((float) (j % 8) * ((float) Math.PI * 2F) / 8.0F) * 0.75F;
      float f9=(float) (j % 8) / 8.0F;
      bufferbuilder.pos((double) (f7 * 0.2F), (double) (f8 * 0.2F), 0.0D).tex((double) f9, (double) f5).color(0, 0, 0, 255).endVertex();
      bufferbuilder.pos((double) f7, (double) f8, (double) f4).tex((double) f9, (double) f6).color(255, 255, 255, 255).endVertex();
    }

    tessellator.draw();
    GlStateManager.enableCull();
    GlStateManager.shadeModel(7424);
    RenderHelper.enableStandardItemLighting();
    GlStateManager.popMatrix();
  }

}

