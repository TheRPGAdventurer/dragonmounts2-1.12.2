package com.TheRPGAdventurer.ROTD.client.items.specialset;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.client.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.client.items.gemset.ItemDragonArmour;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemWaterDragonArmor extends ItemDragonArmour {
	
	private final PotionEffect potionEffect;

	public ItemWaterDragonArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn,
			String unlocalizedName, EnumItemBreedTypes type, @Nullable PotionEffect potionEffect) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, type);
		this.potionEffect = potionEffect;
		
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if (player.isInWater()) { // If the Potion isn't currently active,
			player.addPotionEffect(new PotionEffect(potionEffect)); // Apply a copy of the PotionEffect to the player
		}
	}
}
