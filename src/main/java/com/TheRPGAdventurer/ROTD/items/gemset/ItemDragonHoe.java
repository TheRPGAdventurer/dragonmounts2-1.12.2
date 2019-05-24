package com.TheRPGAdventurer.ROTD.items.gemset;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.inits.ModTools;
import com.TheRPGAdventurer.ROTD.items.EnumItemBreedTypes;
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
		this.type = type;

		new ItemStack(this).setStackDisplayName(type.color + new ItemStack(this).getDisplayName());
		ModTools.TOOLS.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(type.color + StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()));
	}

}