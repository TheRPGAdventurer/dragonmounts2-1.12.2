package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class ItemDragonShulker extends ItemBlock implements IHasModel {
    public ItemDragonShulker(Block p_i47260_1_) {
        super(p_i47260_1_);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("item_dragon_shulker");
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "item_dragon_shulker"));

        ModItems.ITEMS.add(this);
    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
