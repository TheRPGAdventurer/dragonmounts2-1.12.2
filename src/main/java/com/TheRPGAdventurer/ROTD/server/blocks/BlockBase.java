package com.TheRPGAdventurer.ROTD.server.blocks;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.initialization.ModBlocks;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel
{
	
	BlockBase(String name, Material material)
	{
		super(material);
		
		this.setRegistryName(DragonMounts.MODID, name);
		this.setUnlocalizedName(this.getRegistryName().toString());
		
		ModBlocks.BLOCKS.add(this);
	}

	@Override
	public void RegisterModels()
	{
		DragonMounts.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}