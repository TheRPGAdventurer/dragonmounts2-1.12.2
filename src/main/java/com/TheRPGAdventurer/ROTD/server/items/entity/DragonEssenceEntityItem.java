package com.TheRPGAdventurer.ROTD.server.items.entity;

import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DragonEssenceEntityItem extends EntityItem {
	
	EnumDragonBreed breed;

	public DragonEssenceEntityItem(World worldIn, EnumDragonBreed breed) {
		super(worldIn);
		this.breed = breed;
	}
	
	@Override
	public ItemStack getItem() {
		return null;

	}
	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
	}

}
