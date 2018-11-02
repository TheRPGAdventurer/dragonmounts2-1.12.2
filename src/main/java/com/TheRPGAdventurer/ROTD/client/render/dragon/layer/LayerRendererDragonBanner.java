package com.TheRPGAdventurer.ROTD.client.render.dragon.layer;

import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
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
		Item item = itemstack.getItem();
		TileEntityBanner te = new TileEntityBanner(); 

    	GlStateManager.pushMatrix();
    	
        if(!itemstack.isEmpty()) {
 	   //     this.model.body.postRender(scale);
 	       float f = 0.625F;
 	       
 	   //    if(dragon.isSitting()) {
 	    //	  GlStateManager.translate(1.0F, 0.4, -0.7F);
 	    //   }
 	       
           GlStateManager.translate(1.0F, 0.4 - (dragon.isSitting() ? -0.5 : 0), 0.7F);
           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
           GlStateManager.scale(1.625F, -0.625F, -0.625F);
           GlStateManager.rotate(90, 1, 0, 0);
//           Minecraft.getMinecraft().getItemRenderer().renderItem(entitylivingbaseIn, new ItemStack(Blocks.PUMPKIN, 1), ItemCameraTransforms.TransformType.HEAD);
       //    mc.getItemRenderer().renderItem(dragon, new ItemStack(Blocks.PUMPKIN, 1), ItemCameraTransforms.TransformType.HEAD);           
           mc.getItemRenderer().renderItem(dragon, itemstack, ItemCameraTransforms.TransformType.HEAD);   
	           	  
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
