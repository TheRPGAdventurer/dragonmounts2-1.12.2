package com.TheRPGAdventurer.ROTD.server.items.gemset;

import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.server.initialization.ModTools;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDragonShovel extends ItemSpade {
	
    public EnumItemBreedTypes type;

	public ItemDragonShovel(ToolMaterial material, String unlocalizedName, EnumItemBreedTypes type) {
		super(material);
		this.setUnlocalizedName("dragon_shovel");
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, unlocalizedName));
		this.setCreativeTab(DragonMounts.armoryTab);
		this.type = type;
		
		ModTools.TOOLS.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GRAY + StatCollector.translateToLocal("item.armoryitems.info") + " " + type.color + TextFormatting.BOLD + StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()));
	}

}