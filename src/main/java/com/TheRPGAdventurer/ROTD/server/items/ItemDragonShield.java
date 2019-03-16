package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.initialization.EnumItemBreedTypes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDragonShield extends ItemShield {
	
	public EnumItemBreedTypes type;
	public ToolMaterial material;
	public Item repair;
	
	public ItemDragonShield(EnumItemBreedTypes type, Item repair) {
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_shield_" + type.toString().toLowerCase()));
		this.repair = repair;
		this.setUnlocalizedName("dragon_shield_" + type.toString().toLowerCase());
		this.setMaxDamage(2500);
		this.setMaxStackSize(1);
		this.setCreativeTab(DragonMounts.TAB);
		this.type = type;
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(type.color + StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()));
	}
	
	@Override
	public boolean isShield(ItemStack stack, EntityLivingBase entity) {
		return true;
	}
	
    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack item) {
        return item.getItem() == repair ? true : super.getIsRepairable(toRepair, new ItemStack(repair));
    }

}
