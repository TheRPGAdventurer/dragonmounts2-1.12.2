package com.TheRPGAdventurer.ROTD.objects.items.bow;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModTools;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.objects.items.ItemDragonBow;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDragonStorm2Bow extends ItemDragonBow implements IHasModel {

    public EnumItemBreedTypes type;
    public Item repair;

    public ItemDragonStorm2Bow(EnumItemBreedTypes type, Item repair) {
        String name = "dragon_bow_" + type.toString().toLowerCase();
        this.setUnlocalizedName("dragon_bow");
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name));
        this.type = type;
        this.repair = repair;


        new ItemStack(this).setStackDisplayName(type.color + new ItemStack(this).getDisplayName());
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn == null) {
                    return 0.0F;
                } else {
                    return entityIn.getActiveItemStack().getItem() != ModTools.storm_dragon_bow2 ? 0.0F : (float) (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });

        ModTools.BOWS.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(type.color + DMUtils.translateToLocal("dragon." + type.toString().toLowerCase()));
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack item) {
        return item.getItem() == repair ? true : super.getIsRepairable(toRepair, new ItemStack(repair));
    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
