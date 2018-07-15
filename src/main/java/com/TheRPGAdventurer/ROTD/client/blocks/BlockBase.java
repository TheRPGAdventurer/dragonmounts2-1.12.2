package com.TheRPGAdventurer.ROTD.client.blocks;

import com.TheRPGAdventurer.ROTD.DragonMounts;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {
	
	BlockBase(String name, Material material) {
		super(material);
		
		this.setRegistryName(DragonMounts.MODID, name);
		this.setUnlocalizedName(this.getRegistryName().toString());
	}
}