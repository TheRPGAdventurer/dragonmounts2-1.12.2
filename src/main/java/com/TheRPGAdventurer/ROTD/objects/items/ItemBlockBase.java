package com.TheRPGAdventurer.ROTD.objects.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockBase extends ItemBlock implements IHasModel
{
	

	public ItemBlockBase(String name, Block block) {
		super(block);
		this.setRegistryName(DragonMounts.MODID, name);
		this.setTranslationKey(this.getRegistryName().toString());
		this.setCreativeTab(DragonMounts.mainTab);
	}

	@Override
	public void RegisterModels()
	{
		DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
