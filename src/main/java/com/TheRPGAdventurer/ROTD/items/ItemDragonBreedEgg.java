package com.TheRPGAdventurer.ROTD.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breeds.EnumDragonBreed;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDragonBreedEgg extends ItemBlock {

    public static ItemDragonBreedEgg DRAGON_BREED_EGG;
    private int meta;

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
        EnumDragonBreed breed=EnumDragonBreed.META_MAPPING.inverse().get(stack.getMetadata());
        String breedName=net.minecraft.util.text.translation.I18n.translateToLocal("item.dragon." + breed.getName() + ".name");
        return net.minecraft.util.text.translation.I18n.translateToLocalFormatted("item.dragonEgg.name", breedName);
    }

    public static final Item[] ITEM_EGG={DRAGON_BREED_EGG=new ItemDragonBreedEgg()};

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        TextFormatting t=null;
        if (stack.hasTagCompound()) {
            tooltip.add("Owner: " + t.GOLD + stack.getTagCompound().getString("inventory"));
            tooltip.add("Owner: " + t.GOLD + stack.getTagCompound().getString("id"));
        }
    }
}
