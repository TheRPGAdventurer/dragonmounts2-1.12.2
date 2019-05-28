package com.TheRPGAdventurer.ROTD.client.render.dragon.layer;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.math.Interpolation;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;

public class LayerRendererDragonBanner extends LayerRendererDragon {

    private final ModelBanner bannerModel=new ModelBanner();
    private final TileEntityBanner banner1=new TileEntityBanner();
    private final TileEntityBanner banner2=new TileEntityBanner();
    private final TileEntityBanner banner3=new TileEntityBanner();
    private final TileEntityBanner banner4=new TileEntityBanner();

    public LayerRendererDragonBanner(DragonRenderer renderer, DefaultDragonBreedRenderer breedRenderer, DragonModel model) {
        super(renderer, breedRenderer, model);

    }

    @Override
    public void doRenderLayer(EntityTameableDragon dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack itemstack1=dragon.getBanner1();
        ItemStack itemstack2=dragon.getBanner2();
        ItemStack itemstack3=dragon.getBanner3();
        ItemStack itemstack4=dragon.getBanner4();

        GlStateManager.pushMatrix();

        if (itemstack1!=null && itemstack1.getItem()==Items.BANNER && banner1!=null) {
            banner1.setItemValues(itemstack1, false);
            TileEntityRendererDispatcher.instance.render(this.banner1, 0.0D, 0.0D, 0.0D, 0.0F, 1.0f);
            model.body.postRender(0.0625F);
            ResourceLocation resourcelocation1=this.getBannerResourceLocation(banner1);
            GlStateManager.translate(1.0F, 0.4F, -0.5F);
            GlStateManager.translate(0.0F, 0.0, Interpolation.smoothStep(-2.5F, 0.0F, dragon.getAnimator().getSpeed()));
            GlStateManager.translate(0, Interpolation.smoothStep(0.3F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-dragon.getBodyPitch(), 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1);
            //            mc.getItemRenderer().renderItem(dragon, itemstack1, ItemCameraTransforms.TransformType.HEAD);
            renderer.bindTexture(resourcelocation1);
            bannerModel.bannerStand.showModel=false;


        }

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        if (itemstack2!=null && itemstack2.getItem()==Items.BANNER && banner2!=null) {
            banner2.setItemValues(itemstack2, false);
            TileEntityRendererDispatcher.instance.render(this.banner2, 0.0D, 0.0D, 0.0D, 0.0F, 1.0f);
            model.body.postRender(0.0625F);
            ResourceLocation resourcelocation2=this.getBannerResourceLocation(banner2);
            GlStateManager.translate(-1.0F, 0.4, -0.5F);
            GlStateManager.translate(0.0F, 0.0, Interpolation.smoothStep(-2.5F, 0.0F, dragon.getAnimator().getSpeed()));
            GlStateManager.translate(0, Interpolation.smoothStep(0.3F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(dragon.getBodyPitch(), 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1);
            //            mc.getItemRenderer().renderItem(dragon, itemstack2, ItemCameraTransforms.TransformType.HEAD);
            renderer.bindTexture(resourcelocation2);
            bannerModel.bannerStand.showModel=false;

        }

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        if (itemstack3!=null && itemstack3.getItem()==Items.BANNER && banner3!=null) {
            banner3.setItemValues(itemstack3, false);
            TileEntityRendererDispatcher.instance.render(this.banner3, 0.0D, 0.0D, 0.0D, 0.0F, 1.0f);
            model.body.postRender(0.0625F);
            ResourceLocation resourcelocation3=this.getBannerResourceLocation(banner3);
            GlStateManager.translate(-0.4F, -1.7F, 1.7F);
            GlStateManager.translate(0.0F, 0.0, Interpolation.smoothStep(0F, 0.0F, dragon.getAnimator().getSpeed()));
            GlStateManager.translate(0, Interpolation.smoothStep(3.2F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
            GlStateManager.translate(0, 0, Interpolation.smoothStep(-1.9F, dragon.getAnimator().getModelOffsetZ() + 1.5F, dragon.getAnimator().getSpeed()));
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-dragon.getBodyPitch() - 5, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.525F, -0.625F, -0.625F);
            //            mc.getItemRenderer().renderItem(dragon, itemstack3, ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1);
            renderer.bindTexture(resourcelocation3);
            bannerModel.bannerStand.showModel=false;
        }

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        if (itemstack4!=null && itemstack4.getItem()==Items.BANNER && banner4!=null) {
            banner4.setItemValues(itemstack4, false);
            TileEntityRendererDispatcher.instance.render(this.banner4, 0.0D, 0.0D, 0.0D, 0.0F, 1.0f);
            model.body.postRender(0.0625F);
            ResourceLocation resourcelocation4=this.getBannerResourceLocation(banner4);
            GlStateManager.translate(0.4F, -1.7F, 1.7F);
            GlStateManager.translate(0, Interpolation.smoothStep(3.2F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
            GlStateManager.translate(0, 0, Interpolation.smoothStep(-1.9F, dragon.getAnimator().getModelOffsetZ() + 1.5F, dragon.getAnimator().getSpeed()));
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-dragon.getBodyPitch() - 5, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.525F, -0.625F, -0.625F);
            //            mc.getItemRenderer().renderItem(dragon, itemstack4, ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1);
            renderer.bindTexture(resourcelocation4);
            bannerModel.bannerStand.showModel=false;

        }

        GlStateManager.popMatrix();
    }

    private ResourceLocation getBannerResourceLocation(TileEntityBanner bannerObj) {
        return BannerTextures.BANNER_DESIGNS.getResourceLocation(bannerObj.getPatternResourceLocation(), bannerObj.getPatternList(), bannerObj.getColorList());
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}