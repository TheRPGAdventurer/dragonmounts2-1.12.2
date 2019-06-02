package com.TheRPGAdventurer.ROTD.objects.items.specialset;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.ItemDragonArmour;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemWaterDragonArmour extends ItemDragonArmour {
	
	public ItemWaterDragonArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName, EnumItemBreedTypes type, @Nullable PotionEffect potionEffect) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, type);
		this.setCreativeTab(DragonMounts.armoryTab);
		this.setUnlocalizedName("dragonscale_" + equipmentSlotIn.toString().toLowerCase());

		new ItemStack(this).setStackDisplayName(type.color + new ItemStack(this).getDisplayName());
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		if (head.getItem() == ModArmour.waterDragonScaleCap && player.isInWater()) { // If the Potion isn't currently active,
			player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 300,0, false, false));
			// Apply a copy of the PotionEffect to the player
		}
	}
}
