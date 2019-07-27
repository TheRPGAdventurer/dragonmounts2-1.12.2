package com.TheRPGAdventurer.ROTD.objects.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemDragonShulker extends ItemBlock implements IHasModel {
    public ItemDragonShulker(Block p_i47260_1_) {
        super(p_i47260_1_);
        this.setMaxStackSize(1);
        this.setTranslationKey("item_dragon_shulker");
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "item_dragon_shulker"));

        ModItems.ITEMS.add(this);
    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public void setTileEntityItemStackRenderer(@Nullable TileEntityItemStackRenderer teisr) {
        super.setTileEntityItemStackRenderer(teisr);
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    private net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer teisr;
}
