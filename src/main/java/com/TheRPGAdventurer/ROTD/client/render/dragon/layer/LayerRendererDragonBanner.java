package com.TheRPGAdventurer.ROTD.client.render.dragon.layer;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.math.Interpolation;
import net.minecraft.block.BlockBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class LayerRendererDragonBanner extends LayerRendererDragon {

    private final ModelBanner bannerModel=new ModelBanner();

    public LayerRendererDragonBanner(DragonRenderer renderer, DefaultDragonBreedRenderer breedRenderer, DragonModel model) {
        super(renderer, breedRenderer, model);

    }

    @Override
    public void doRenderLayer(EntityTameableDragon dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Minecraft mc=Minecraft.getMinecraft();
        Item item1=dragon.getBanner1().getItem();
        Item item2=dragon.getBanner2().getItem();
        Item item3=dragon.getBanner3().getItem();
        Item item4=dragon.getBanner4().getItem();

        if(item1==TileEntityBanner.getItem().getItem()) 
        ResourceLocation resourcelocation1=this.getBannerResourceLocation(TileEntityBanner.getKey());
        ResourceLocation resourcelocation2=this.getBannerResourceLocation((TileEntityBanner) BlockBanner.getBlockFromItem(item2).createNewTileEntity(null, null));
        ResourceLocation resourcelocation3=this.getBannerResourceLocation((TileEntityBanner) BlockBanner.getBlockFromItem(item3).createNewTileEntity(null, null));
        ResourceLocation resourcelocation4=this.getBannerResourceLocation((TileEntityBanner) BlockBanner.getBlockFromItem(item4)).createNewTileEntity(null, null));


        GlStateManager.pushMatrix();

        if (item1!=null) {
            model.body.postRender(0.0625F);
            GlStateManager.translate(1.0F, 0.4F, -0.5F);
            GlStateManager.translate(0.0F, 0.0, Interpolation.smoothStep(-2.5F, 0.0F, dragon.getAnimator().getSpeed()));
            GlStateManager.translate(0, Interpolation.smoothStep(0.3F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-dragon.getBodyPitch(), 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
//            mc.getItemRenderer().renderItem(dragon, item1, ItemCameraTransforms.TransformType.HEAD);
            renderer.renderBanner(resourcelocation1, bannerModel);

        }

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        if (item2!=null) {
            model.body.postRender(0.0625F);
            GlStateManager.translate(-1.0F, 0.4, -0.5F);
            GlStateManager.translate(0.0F, 0.0, Interpolation.smoothStep(-2.5F, 0.0F, dragon.getAnimator().getSpeed()));
            GlStateManager.translate(0, Interpolation.smoothStep(0.3F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(dragon.getBodyPitch(), 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
//            mc.getItemRenderer().renderItem(dragon, item2, ItemCameraTransforms.TransformType.HEAD);
            renderer.renderBanner(resourcelocation2, bannerModel);
        }

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        if (item3!=null) {
            model.body.postRender(0.0625F);
            GlStateManager.translate(-0.4F, -1.7F, 1.7F);
            GlStateManager.translate(0.0F, 0.0, Interpolation.smoothStep(0F, 0.0F, dragon.getAnimator().getSpeed()));
            GlStateManager.translate(0, Interpolation.smoothStep(3.2F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
            GlStateManager.translate(0, 0, Interpolation.smoothStep(-1.9F, dragon.getAnimator().getModelOffsetZ() + 1.5F, dragon.getAnimator().getSpeed()));
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-dragon.getBodyPitch() - 5, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.525F, -0.625F, -0.625F);
//            mc.getItemRenderer().renderItem(dragon, item3, ItemCameraTransforms.TransformType.HEAD);
            renderer.renderBanner(resourcelocation3, bannerModel);
        }

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        if (item4!=null) {
            model.body.postRender(0.0625F);
            GlStateManager.translate(0.4F, -1.7F, 1.7F);
            GlStateManager.translate(0, Interpolation.smoothStep(3.2F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
            GlStateManager.translate(0, 0, Interpolation.smoothStep(-1.9F, dragon.getAnimator().getModelOffsetZ() + 1.5F, dragon.getAnimator().getSpeed()));

            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-dragon.getBodyPitch() - 5, 1.0F, 0.0F, 0.0F);

            GlStateManager.scale(0.525F, -0.625F, -0.625F);
//            mc.getItemRenderer().renderItem(dragon, item4, ItemCameraTransforms.TransformType.HEAD);
            renderer.renderBanner(resourcelocation4, bannerModel);
        }

        GlStateManager.popMatrix();
    }

    @Nullable
    private ResourceLocation getBannerResourceLocation(TileEntityBanner bannerObj) {
        return BannerTextures.BANNER_DESIGNS.getResourceLocation(bannerObj.getPatternResourceLocation(), bannerObj.getPatternList(), bannerObj.getColorList());
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}