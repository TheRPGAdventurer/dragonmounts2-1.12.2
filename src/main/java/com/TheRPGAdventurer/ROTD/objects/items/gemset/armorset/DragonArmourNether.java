package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import java.util.List;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DragonArmourNether extends DragonArmourBase {
	
	public DragonArmourNether(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.NETHER);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
			
		if (player.getCooldownTracker().getCooldown(this, 0) > 0) return;
		super.onArmorTick(world, player, itemStack);
		if (!((head == ModArmour.netherDragonScaleCap && chest == ModArmour.netherDragonScaleTunic && legs == ModArmour.netherDragonScaleLeggings && feet == ModArmour.netherDragonScaleBoots) ||
			  (head == ModArmour.netherDragonScaleCap2 && chest == ModArmour.netherDragonScaleTunic2 && legs == ModArmour.netherDragonScaleLeggings2 && feet == ModArmour.netherDragonScaleBoots2)))
			return;
		if (player.hurtTime == 0) return;
		doEffect(player);
		List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(5));
		if (entities.isEmpty()) return; // Don't set a coolDown for not hitting anything
		for (net.minecraft.entity.Entity entity : entities)
			if (entity instanceof EntityMob) {
				((EntityMob) entity).setFire(10);
				((EntityMob) entity).knockBack(entity, 0.4f, 1, 1);
			}
		if (player.hurtTime < 7) player.getCooldownTracker().setCooldown(this, 1200);
	}

	private void doEffect(EntityPlayer player) {
		player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.46f, 1.0f);
		for (int x = -27; x < 28; x++) player.world.spawnParticle(EnumParticleTypes.FLAME, player.posX, player.posY + 1, player.posZ, Math.sin(x) / 3, 0, Math.cos(x) / 3);
	}
	
}
