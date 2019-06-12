package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DragonArmourFire extends DragonArmourBase {
	
	public DragonArmourFire(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.FIRE);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (player.getCooldownTracker().getCooldown(this, 0) > 0) return;
		super.onArmorTick(world, player, itemStack);
		if (!((head == ModArmour.fireDragonScaleCap && chest == ModArmour.fireDragonScaleTunic && legs == ModArmour.fireDragonScaleLeggings && feet == ModArmour.fireDragonScaleBoots) ||
		      (head == ModArmour.fireDragonScaleCap2 && chest == ModArmour.fireDragonScaleTunic2 && legs == ModArmour.fireDragonScaleLeggings2 && feet == ModArmour.fireDragonScaleBoots2)))
			return;
		if (!player.isBurning()) return;
		
		player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 300, 0, false, false));
		player.getCooldownTracker().setCooldown(this, 1160);
	}
	
}
