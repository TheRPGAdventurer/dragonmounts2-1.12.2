package com.TheRPGAdventurer.ROTD.objects.items;


import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;

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
        this.setCreativeTab(DragonMounts.mainTab);
    }

    @Override
    public int getMetadata(int metadata) {
        return metadata;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        EnumDragonBreed breed = EnumDragonBreed.META_MAPPING.inverse().get(stack.getMetadata());
        String breedName = net.minecraft.util.text.translation.I18n.translateToLocal("entity.DragonMount." + breed.getName() + ".name");
        return net.minecraft.util.text.translation.I18n.translateToLocalFormatted("item.dragonEgg.name", breedName);
    }
    
   public static final Item[] ITEM_EGG =  {
    	DRAGON_BREED_EGG = new ItemDragonBreedEgg()
    };
}
