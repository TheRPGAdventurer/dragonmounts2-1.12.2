package com.TheRPGAdventurer.ROTD.client.render.layers;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.client.renderer.GlStateManager;
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
