package com.TheRPGAdventurer.ROTD.client.render.dragon.layer;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

/**
 * Created by TheRPGAdventurer based on ice and fire code5.
 */
public class LayerRendererDragonArmor extends LayerRendererDragon {
	   
    public LayerRendererDragonArmor(DragonRenderer renderer,
            DefaultDragonBreedRenderer breedRenderer, DragonModel model) {
        super(renderer, breedRenderer, model);
    }
    
    @Override
    public void doRenderLayer(EntityTameableDragon dragon, float moveTime,
            float moveSpeed, float partialTicks, float ticksExisted, float lookYaw,
            float lookPitch, float scale) { 
    	if (dragon.getArmor() != 0) {
			renderer.bindTexture(new ResourceLocation(DragonMounts.MODID + ":textures/entities/armor/armor_" + (dragon.getArmor() != 1 ? dragon.getArmor() != 2 ? "diamond" : "gold" : "iron") + ".png"));
	        model.render(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
		}
	}

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
  
}
