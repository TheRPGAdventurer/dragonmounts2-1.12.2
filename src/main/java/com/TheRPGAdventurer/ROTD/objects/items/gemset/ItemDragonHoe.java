package com.TheRPGAdventurer.ROTD.objects.items.gemset;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModTools;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDragonHoe extends ItemHoe {

    public EnumItemBreedTypes type;

    public ItemDragonHoe(ToolMaterial material, String name, EnumItemBreedTypes type) {
        super(material);
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name));
        this.setUnlocalizedName("dragon_hoe");
        this.setCreativeTab(DragonMounts.armoryTab);
        this.type=type;

        ModTools.TOOLS.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(type.color + DMUtils.translateToLocal("dragon." + type.toString().toLowerCase()));
    }

}
