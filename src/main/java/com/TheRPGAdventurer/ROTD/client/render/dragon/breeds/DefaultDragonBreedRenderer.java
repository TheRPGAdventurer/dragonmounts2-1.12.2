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

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.layer.*;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DefaultDragonBreedRenderer implements DragonBreedRenderer {

    protected final List<LayerRenderer<EntityTameableDragon>> layers = new ArrayList<>();

    private final DragonRenderer renderer;
    private final DragonModel model;

    private final ResourceLocation maleBodyTexture;
    private final ResourceLocation maleGlowTexture;
    private final ResourceLocation hmaleBodyTexture;
    private final ResourceLocation hmaleGlowTexture;
    private final ResourceLocation aMaleBodyTexture;
    private final ResourceLocation aMaleGlowTexture;
    private final ResourceLocation a_hMaleBodyTexture;
    private final ResourceLocation a_hMaleGlowTexture;

    private final ResourceLocation maleBodyTexturealt;
    private final ResourceLocation maleGlowTexturealt;
    private final ResourceLocation hmaleBodyTexturealt;
    private final ResourceLocation hmaleGlowTexturealt;

    private final ResourceLocation femaleBodyTexture;
    private final ResourceLocation femaleGlowTexture;
    private final ResourceLocation hfemaleBodyTexture;
    private final ResourceLocation hfemaleGlowTexture;
    private final ResourceLocation aFemaleBodyTexture;
    private final ResourceLocation aFemaleGlowTexture;
    private final ResourceLocation a_hFemaleBodyTexture;
    private final ResourceLocation a_hFemaleGlowTexture;

    private final ResourceLocation femaleBodyTexturealt;
    private final ResourceLocation femaleGlowTexturealt;
    private final ResourceLocation hfemaleBodyTexturealt;
    private final ResourceLocation hfemaleGlowTexturealt;

    private final ResourceLocation glowAnimTexture;
    private final ResourceLocation saddleTexture;
    private final ResourceLocation eggTexture;
    private final ResourceLocation dissolveTexture;
    private final ResourceLocation chestTexture;
//    private final ResourceLocation armorTexture;

    public DefaultDragonBreedRenderer(DragonRenderer parent, EnumDragonBreed breed) {
        renderer = parent;
        model = new DragonModel(breed);

        // standard layers
        layers.add(new LayerRendererDragonGlow(parent, this, model));
//        layers.add(new LayerRendererDragonGlowAnim(parent, this, model));
        layers.add(new LayerRendererDragonSaddle(parent, this, model));
        layers.add(new LayerRendererDragonArmor(parent, this, model));
        layers.add(new LayerRendererDragonChest(parent, this, model));
        layers.add(new LayerRendererDragonBanner(parent, this, model));


        // textures
        String skin = breed.getBreed().getSkin();
        maleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/bodym.png");
        maleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/glowm.png");
        hmaleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/hbodym.png");
        hmaleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/hglowm.png");
        aMaleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/abodym.png");
        aMaleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/aglowm.png");
        a_hMaleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/a_hbodym.png");
        a_hMaleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/a_hglowm.png");

        maleBodyTexturealt = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/alt/bodymalt.png");
        maleGlowTexturealt = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/alt/glowmalt.png");
        hmaleBodyTexturealt = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/alt/hbodymalt.png");
        hmaleGlowTexturealt = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/alt/hglowmalt.png");

        femaleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/bodyfm.png");
        femaleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/glowfm.png");
        hfemaleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/hbodyfm.png");
        hfemaleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/hglowfm.png");
        aFemaleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/abodyfm.png");
        aFemaleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/aglowfm.png");
        a_hFemaleBodyTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/a_hbodyfm.png");
        a_hFemaleGlowTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/a_hglowfm.png");

        femaleBodyTexturealt = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/alt/bodyfmalt.png");
        femaleGlowTexturealt = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/alt/glowfmalt.png");
        hfemaleBodyTexturealt = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/alt/hbodyfmalt.png");
        hfemaleGlowTexturealt = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/alt/hglowfmalt.png");

        glowAnimTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/glow_anim.png");
        saddleTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/saddle.png");
        eggTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/egg.png");
        dissolveTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + "dissolve.png");
        chestTexture = new ResourceLocation(DragonMounts.MODID, DragonRenderer.TEX_BASE + skin + "/chest.png");
    }

    @Override
    public List<LayerRenderer<EntityTameableDragon>> getLayers() {
        return Collections.unmodifiableList(layers);
    }

    @Override
    public DragonRenderer getRenderer() {
        return renderer;
    }

    @Override
    public DragonModel getModel() {
        return model;
    }

    @Override
    public ResourceLocation getMaleBodyTexture(boolean hatchling, boolean albino, boolean alt) {
        if (alt) {
            return hatchling ? hmaleBodyTexturealt : maleBodyTexturealt;
        } else {
            return hatchling ? (albino ? a_hMaleBodyTexture : hmaleBodyTexture) : albino ? aMaleBodyTexture : maleBodyTexture;
        }
    }

    @Override
    public ResourceLocation getFemaleBodyTexture(boolean hatchling, boolean albino, boolean alt) {
        if (alt) {
            return hatchling ? hfemaleBodyTexturealt : femaleBodyTexturealt;
        } else {
            return hatchling ? (albino ? a_hFemaleBodyTexture : hfemaleBodyTexture) : albino ? aFemaleBodyTexture : femaleBodyTexture;
        }
    }

    @Override
    public ResourceLocation getMaleGlowTexture(boolean hatchling, boolean albino, boolean alt) {
        if (alt) {
            return hatchling ? hmaleGlowTexturealt : maleGlowTexturealt;
        } else {
            return hatchling ? (albino ? a_hMaleGlowTexture : hmaleGlowTexture) : albino ? aMaleGlowTexture : maleGlowTexture;
        }
    }

    @Override
    public ResourceLocation getFemaleGlowTexture(boolean hatchling, boolean albino, boolean alt) {
        if (alt) {
            return hatchling ? hfemaleGlowTexturealt : femaleGlowTexturealt;
        } else {
            return hatchling ? (albino ? a_hFemaleGlowTexture : hfemaleGlowTexture) : albino ? aFemaleGlowTexture : femaleGlowTexture;
        }
    }

    @Override
    public ResourceLocation getGlowAnimTexture() {
        return glowAnimTexture;
    }

    @Override
    public ResourceLocation getSaddleTexture() {
        return saddleTexture;
    }

    @Override
    public ResourceLocation getEggTexture() {
        return eggTexture;
    }

    @Override
    public ResourceLocation getDissolveTexture() {
        return dissolveTexture;
    }

    @Override
    public ResourceLocation getChestTexture() {
        return chestTexture;
    }

    @Override
    public ResourceLocation getArmorTexture() {
        return null;
    }

}
