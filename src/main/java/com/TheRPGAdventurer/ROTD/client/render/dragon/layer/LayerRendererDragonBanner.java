package com.TheRPGAdventurer.ROTD.client.render.dragon.layer;

import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.math.Interpolation;
import com.TheRPGAdventurer.ROTD.util.math.MathX;
import com.mojang.authlib.GameProfile;

import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class LayerRendererDragonBanner extends LayerRendererDragon {
	
    private final ModelBanner bannerModel = new ModelBanner();

	public LayerRendererDragonBanner(DragonRenderer renderer, DefaultDragonBreedRenderer breedRenderer,
			DragonModel model) {
		super(renderer, breedRenderer, model); 

	}

	@Override
	public void doRenderLayer(EntityTameableDragon dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack itemstack1 = dragon.dragonInv.getStackInSlot(31);
		ItemStack itemstack2 = dragon.dragonInv.getStackInSlot(32);
		ItemStack itemstack3 = dragon.dragonInv.getStackInSlot(33);
		ItemStack itemstack4 = dragon.dragonInv.getStackInSlot(34);
		
	//	ItemStack itemstack1 = dragon.getBanner1();
	//	ItemStack itemstack2 = dragon.getBanner2();
	//	ItemStack itemstack3 = dragon.getBanner3();
	//	ItemStack itemstack4 = dragon.getBanner4();

    	GlStateManager.pushMatrix();
    	
        if(itemstack1 != null) {
 	       float f = 0.625F; 	   	       
 	       model.body.postRender(0.0625F);
           GlStateManager.translate(1.0F, 0.4F, -0.5F); 
           GlStateManager.translate(0.0F, 0.0, -getBodyPitch(partialTicks, dragon));
      //     GlStateManager.translate(0, Interpolation.smoothStep(0.3F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
           GlStateManager.rotate(-getBodyPitch(partialTicks, dragon), 0.0F, 0.0F, 100.0F);
           GlStateManager.scale(0.625F, -0.625F, -0.625F);
           mc.getItemRenderer().renderItem(dragon, itemstack1, ItemCameraTransforms.TransformType.HEAD);   
	           	  
        } 
        
        GlStateManager.popMatrix(); 
        
        GlStateManager.pushMatrix();
        
        if (itemstack2 != null) {
        	float f = 0.625F; 	   	       
  	        model.body.postRender(0.0625F);
            GlStateManager.translate(-1.0F, 0.4 + dragon.getAnimator().getModelOffsetY(), -0.5F); 
            GlStateManager.translate(0.0F, 0.0, Interpolation.smoothStep(-2.5F, 0.0F, dragon.getAnimator().getSpeed()));
         //   GlStateManager.translate(0, Interpolation.smoothStep(0.3F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(dragon.getBodyPitch(), 0.0F, 0.0F, 100.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
            mc.getItemRenderer().renderItem(dragon, itemstack2, ItemCameraTransforms.TransformType.HEAD); 
        }
        
        GlStateManager.popMatrix(); 	 
        
        GlStateManager.pushMatrix();
        
        if (itemstack3 != null) {
        	float f = 0.625F; 	   	       
  	        model.body.postRender(0.0625F);
            GlStateManager.translate(-0.4F, -1.7F, 1.7F); 
            GlStateManager.translate(0.0F, 0.0, Interpolation.smoothStep(0F, 0.0F, dragon.getAnimator().getSpeed()));
            GlStateManager.translate(0, Interpolation.smoothStep(3.2F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);            
            GlStateManager.translate(0, 0, Interpolation.smoothStep(-1.9F, dragon.getAnimator().getModelOffsetZ() + 1.5F, dragon.getAnimator().getSpeed()));
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-dragon.getBodyPitch() - 5, 100.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.525F, -0.625F, -0.625F);
            mc.getItemRenderer().renderItem(dragon, itemstack3, ItemCameraTransforms.TransformType.HEAD); 
        }
        
        GlStateManager.popMatrix(); 
        
        GlStateManager.pushMatrix();
        
        if (itemstack4 != null) {
        	float f = 0.625F; 	   	        
  	        model.body.postRender(0.0625F);
            GlStateManager.translate(0.4F, -1.7F, 1.7F); 
            GlStateManager.translate(0, Interpolation.smoothStep(3.2F, dragon.getAnimator().getModelOffsetY() + 1.5F, dragon.getAnimator().getSpeed()), 0);            
            GlStateManager.translate(0, 0, Interpolation.smoothStep(-1.9F, dragon.getAnimator().getModelOffsetZ() + 1.5F, dragon.getAnimator().getSpeed()));
            
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-dragon.getBodyPitch() - 5, 100.0F, 0.0F, 0.0F);
            
            GlStateManager.scale(0.525F, -0.625F, -0.625F);
            mc.getItemRenderer().renderItem(dragon, itemstack4, ItemCameraTransforms.TransformType.HEAD); 
        }
        
        GlStateManager.popMatrix(); 
	}
	
    public float getBodyPitch(float partialTicks, EntityTameableDragon dragon) {
        float pitchMovingMax = 90;
        float pitchMoving = MathX.clamp(dragon.animator.yTrail.get(partialTicks, 5, 0) * 10, -pitchMovingMax, pitchMovingMax);
        float pitchHoverMax = 60;
        return Interpolation.smoothStep(pitchHoverMax, pitchMoving, dragon.getAnimator().getSpeed());
    }

	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}