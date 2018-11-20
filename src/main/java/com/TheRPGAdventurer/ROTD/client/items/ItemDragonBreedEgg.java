package com.TheRPGAdventurer.ROTD.client.items;


import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemDragonBreedEgg extends ItemBlock {
    
    public static ItemDragonBreedEgg DRAGON_BREED_EGG;
    
    public ItemDragonBreedEgg() {
        super(BlockDragonBreedEgg.DRAGON_BREED_EGG);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setCreativeTab(DragonMounts.TAB);
    }

    @Override
    public int getMetadata(int metadata) {
        return metadata;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        EnumDragonBreed breed = EnumDragonBreed.META_MAPPING.inverse().get(stack.getMetadata());
        String breedName = I18n.format("entity.DragonMounts.dragon." + breed.getName() + ".name");
        return I18n.format("item.dragonEgg.name", breedName);
    }
    
   public static final Item[] ITEM_EGG =  {
    	DRAGON_BREED_EGG = new ItemDragonBreedEgg()
    };
}
