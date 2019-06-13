package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DragonArmourMoon extends DragonArmourBase {
	
	public DragonArmourMoon(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.MOONLIGHT);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (player.getCooldownTracker().getCooldown(this, 0) > 0 && !isActive(MobEffects.NIGHT_VISION, player)) return;
		super.onArmorTick(world, player, itemStack);
		if (!(head == ModArmour.moonlightDragonScaleCap && chest == ModArmour.moonlightDragonScaleTunic && legs == ModArmour.moonlightDragonScaleLeggings && feet == ModArmour.moonlightDragonScaleBoots))
			return;
		
		player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, false, false));
		player.getCooldownTracker().setCooldown(this, 70);
	}
	
}
