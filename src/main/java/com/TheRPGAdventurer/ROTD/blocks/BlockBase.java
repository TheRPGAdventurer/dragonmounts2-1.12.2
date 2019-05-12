package com.TheRPGAdventurer.ROTD.blocks;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModBlocks;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockBase extends Block implements IHasModel {

    BlockBase(String name, Material material) {
        super(material);

        this.setRegistryName(DragonMounts.MODID, name);
        this.setUnlocalizedName(this.getRegistryName().toString());

        ModBlocks.BLOCKS.add(this);
    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}