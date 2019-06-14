package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DragonArmourTerra extends DragonArmourBase {
	
	public DragonArmourTerra(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.TERRA);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (player.getCooldownTracker().getCooldown(this, 0) > 0) return;
		super.onArmorTick(world, player, itemStack);
		if (!((head == ModArmour.terraDragonScaleCap && chest == ModArmour.terraDragonScaleTunic && legs == ModArmour.terraDragonScaleLeggings && feet == ModArmour.terraDragonScaleBoots) ||
			  (head == ModArmour.terra2DragonScaleCap && chest == ModArmour.terra2DragonScaleTunic && legs == ModArmour.terra2DragonScaleLeggings && feet == ModArmour.terra2DragonScaleBoots)))
			return;

		// TODO fix the work of actually calling if is breaking block if buggy
		if (player.canHarvestBlock(world.getBlockState(BlockPos.ORIGIN)) && !(player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) && isActive(MobEffects.HASTE, player)) return;
		player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 72));
		player.getCooldownTracker().setCooldown(this, 70);
	}
	
}
