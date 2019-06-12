package com.TheRPGAdventurer.ROTD.objects.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.item.Item;


public class ItemDragonArmor extends Item implements IHasModel {

    public String name;

    public ItemDragonArmor(String name) {
        this.name = name;
        this.setUnlocalizedName(name);
        this.maxStackSize = 1;
        this.setRegistryName(DragonMounts.MODID, name);
        this.setCreativeTab(DragonMounts.armoryTab);

        ModArmour.ARMOR.add(this);
    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
    }
}