package com.TheRPGAdventurer.ROTD.server.items.specialset;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.server.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.server.items.gemset.ItemDragonArmour;
import com.TheRPGAdventurer.ROTD.DragonMounts;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemWaterDragonArmour extends ItemDragonArmour {
	
	private final PotionEffect potionEffect;

	public ItemWaterDragonArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn,
			String unlocalizedName, EnumItemBreedTypes type, @Nullable PotionEffect potionEffect) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, type);
		this.potionEffect = potionEffect;
		this.setCreativeTab(DragonMounts.ATAB);
		
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if (player.isInWater()) { // If the Potion isn't currently active,
			player.addPotionEffect(new PotionEffect(potionEffect)); // Apply a copy of the PotionEffect to the player
		}
	}
}
