package com.TheRPGAdventurer.ROTD.items.specialset;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.items.gemset.ItemDragonArmour;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemWaterDragonArmour extends ItemDragonArmour {
	
	private final PotionEffect potionEffect;

	public ItemWaterDragonArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName, EnumItemBreedTypes type, @Nullable PotionEffect potionEffect) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, type);
		this.potionEffect = potionEffect;
		this.setCreativeTab(DragonMounts.armoryTab);
		this.setUnlocalizedName("dragonscale_" + equipmentSlotIn.toString().toLowerCase());
		
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (player.isInWater()) { // If the Potion isn't currently active,
			player.addPotionEffect(potionEffect); // Apply a copy of the PotionEffect to the player
		}
	}
}
