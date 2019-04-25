package com.TheRPGAdventurer.ROTD.client.render.layers;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.breeds.DefaultDragonBreedRenderer;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class LayerRendererDragonChest extends LayerRendererDragon {
    
	    public LayerRendererDragonChest(DragonRenderer renderer, DefaultDragonBreedRenderer breedRenderer, DragonModel model) {
              super(renderer, breedRenderer, model);
        }

	    @Override
	    public void doRenderLayer(EntityTameableDragon dragon, float moveTime,
	            float moveSpeed, float partialTicks, float ticksExisted, float lookYaw,
	            float lookPitch, float scale) {
			if (dragon.isChested()) {
				renderer.bindTexture(breedRenderer.getChestTexture());
		        model.render(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
			}
		}

		@Override
		public boolean shouldCombineTextures() {
			return false;
		}
	}