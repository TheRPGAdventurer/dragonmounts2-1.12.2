/*
** 2016 March 07
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.client.render.dragon.breeds;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public interface DragonBreedRenderer {

    public ResourceLocation getMaleBodyTexture(boolean hatchling, boolean albino, boolean alttexture);
    
    public ResourceLocation getFemaleBodyTexture(boolean hatchling, boolean albino, boolean alttexture);

    public ResourceLocation getDissolveTexture();

    public ResourceLocation getEggTexture();

    public ResourceLocation getMaleGlowTexture(boolean hatchling, boolean albino, boolean alttexture);
    
    public ResourceLocation getFemaleGlowTexture(boolean hatchling, boolean albino, boolean alttexture);
    
    public ResourceLocation getGlowAnimTexture();

    public List<LayerRenderer<EntityTameableDragon>> getLayers();

    public DragonModel getModel();

    public DragonRenderer getRenderer();

    public ResourceLocation getSaddleTexture();
    
    public ResourceLocation getChestTexture();
    
    public ResourceLocation getArmorTexture();

}
