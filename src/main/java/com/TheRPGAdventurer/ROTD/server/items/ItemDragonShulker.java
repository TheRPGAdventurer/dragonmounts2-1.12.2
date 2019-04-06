package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.initialization.ModBlocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class ItemDragonShulker extends ItemBlock {
    public ItemDragonShulker() {
        super(ModBlocks.dragon_shulker_box);
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_shulker_box"));
        this.setUnlocalizedName("dragon_shulker_box");
    }
}
