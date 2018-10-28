package com.TheRPGAdventurer.ROTD.client.items;

import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDragonShield extends ItemShield {
	
	public EnumItemBreedTypes type;
	public ToolMaterial material;
	
	public ItemDragonShield(EnumItemBreedTypes type) {
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_shield_" + type.toString().toLowerCase()));
//		this.material = material;
		this.setUnlocalizedName("dragon_shield_" + type.toString().toLowerCase());
		this.setMaxDamage(669);
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

}
