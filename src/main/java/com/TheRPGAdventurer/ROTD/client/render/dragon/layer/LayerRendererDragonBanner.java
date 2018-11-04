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
		ItemStack itemstack = dragon.dragonInv.getStackInSlot(31);
		ItemStack itemstack2 = dragon.dragonInv.getStackInSlot(32);

    	GlStateManager.pushMatrix();
    	
        if(!itemstack.isEmpty()) {
 	       float f = 0.625F; 	   	       
 	       model.body.postRender(0.0625F);
           GlStateManager.translate(1.0F, 0.4, -0.8F); 
           GlStateManager.translate(0.0F, 0.0, -2.4F);
           GlStateManager.translate(0, 0.1, 0);
     //      GlStateManager.translate(0F, 0.8, 0F); 
          // GlStateManager.translate(0.0F, -0.3F, -1.9F); 
           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
           GlStateManager.rotate(-dragon.getBodyPitch(), 0.0F, 0.0F, 1.0F);
           GlStateManager.scale(0.625F, -0.625F, -0.625F);
           mc.getItemRenderer().renderItem(dragon, itemstack, ItemCameraTransforms.TransformType.HEAD);   
	           	  
        } 
        
        GlStateManager.popMatrix(); 
        
        GlStateManager.pushMatrix();
        
        if (!itemstack2.isEmpty()) {
        	float f = 0.625F; 	   	       
  	        model.body.postRender(0.0625F);
            GlStateManager.translate(-1.0F, 0.4, -0.8F);
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(dragon.getBodyPitch(), 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
            mc.getItemRenderer().renderItem(dragon, itemstack2, ItemCameraTransforms.TransformType.HEAD); 
        }
        
        GlStateManager.popMatrix(); 	       		
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}

}
