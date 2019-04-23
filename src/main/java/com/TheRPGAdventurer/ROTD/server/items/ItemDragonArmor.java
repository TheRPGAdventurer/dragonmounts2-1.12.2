package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.util.IHasModel;

import net.minecraft.item.Item;


public class ItemDragonArmor extends Item implements IHasModel {

    public String name;

    public ItemDragonArmor(String name) {
        this.name = name;
        this.setUnlocalizedName(name);
        this.maxStackSize = 1;
        this.setRegistryName(DragonMounts.MODID, name);
        this.setCreativeTab(DragonMounts.armoryTab);

        ModItems.ITEMS.add(this);

    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
    }
}