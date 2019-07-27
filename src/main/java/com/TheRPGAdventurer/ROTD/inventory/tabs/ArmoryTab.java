package com.TheRPGAdventurer.ROTD.inventory.tabs;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.ItemDragonSword;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmoryTab extends CreativeTabs
{
	
	public ArmoryTab(String label)
	{
		super("armorytab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack createIcon()
	{
		return new ItemStack(ItemDragonSword.getByNameOrId(DragonMounts.MODID + ":ender_dragon_sword"));
	}
	
	@Override
	public boolean hasSearchBar() {
		return false;
	}
}
