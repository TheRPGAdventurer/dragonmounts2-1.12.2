package com.TheRPGAdventurer.ROTD.client.render;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.layer.*;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DefaultBreedRendererTileEntity {

    protected final List<LayerRenderer<EntityTameableDragon>> layers = new ArrayList<>();

    private DragonModel model;
    private EntityTameableDragon dragon;

    public ResourceLocation maleBodyTexture;
    public ResourceLocation femaleBodyTexture;
    public ResourceLocation maleGlowTexture;
    public ResourceLocation femaleGlowTexture;
//    private final ResourceLocation armorTexture;

    public DefaultBreedRendererTileEntity(TileEntityDragonRenderer parent, EnumDragonBreed breed) {
        model = new DragonModel(breed);

        // textures
        String skin = breed.getBreed().getSkin();
        maleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/bodym.png");
        femaleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/bodyfm.png");
        maleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/glowm.png");
        femaleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/glowfm.png");
    }

}
