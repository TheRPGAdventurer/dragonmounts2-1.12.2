package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;

import net.minecraft.inventory.EntityEquipmentSlot;

public class DragonArmourSunlight extends DragonArmourBase {
	
	public DragonArmourSunlight(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.SUNLIGHT);
	}
	
}
