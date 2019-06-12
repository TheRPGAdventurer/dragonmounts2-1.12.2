package com.TheRPGAdventurer.ROTD.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDragonShulker extends ModelBase {
    public ModelRenderer wtfisthis;
    public ModelRenderer base;
    public ModelRenderer lid;

    public ModelDragonShulker()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.lid = new ModelRenderer(this, 0, 0);
        this.lid.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.lid.addBox(-8.0F, -16.0F, -8.0F, 16, 12, 16, 0.0F);
        this.base = new ModelRenderer(this, 0, 28);
        this.base.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.base.addBox(-8.0F, -8.0F, -8.0F, 16, 8, 16, 0.0F);
        this.wtfisthis = new ModelRenderer(this, 0, 52);
        this.wtfisthis.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.wtfisthis.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);

    }
    
    public void renderAll(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.lid.render(0.0625F);
        this.base.render(0.0625F);
        this.wtfisthis.render(0.0625F);
    }
}
