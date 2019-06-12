package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

public class DragonArmourStorm extends DragonArmourBase {
	
	private Random rand = new Random();
	
	public DragonArmourStorm(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.STORM);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if (!((head == ModArmour.stormDragonScaleCap && chest == ModArmour.stormDragonScaleTunic && legs == ModArmour.stormDragonScaleLeggings && feet == ModArmour.stormDragonScaleBoots) ||
			  (head == ModArmour.stormDragonScaleCap2 && chest == ModArmour.stormDragonScaleTunic2 && legs == ModArmour.stormDragonScaleLeggings2 && feet == ModArmour.stormDragonScaleBoots2)))
			return;
		if (player.getLastAttackedEntity() == null) return;
		if (rand.nextInt(20) != 0) { player.setLastAttackedEntity(null); return; }
		
		BlockPos pos = player.getLastAttackedEntity().getPosition();
		world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
		player.setLastAttackedEntity(null);
	}
	
}
