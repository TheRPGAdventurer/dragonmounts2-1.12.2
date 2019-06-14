package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.Random;

public class DragonArmourEnder extends DragonArmourBase {

	private Random rand = new Random();
	
	public DragonArmourEnder(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.END);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        double px=player.posX + (rand.nextDouble() - 0.3);
        double py=player.posY + (rand.nextDouble() - 0.3);
        double pz=player.posZ + (rand.nextDouble() - 0.3);
        double ox=(rand.nextDouble() - 0.3) * 2;
        double oy=(rand.nextDouble() - 0.3) * 2;
        double oz=(rand.nextDouble() - 0.3) * 2;
        player.world.spawnParticle(EnumParticleTypes.PORTAL, px, py, pz, ox, oy, oz);
		
        if (player.getCooldownTracker().getCooldown(this, 0) > 0) return;
        super.onArmorTick(world, player, itemStack);
		if (!(head == ModArmour.enderDragonScaleCap && chest == ModArmour.enderDragonScaleTunic && legs == ModArmour.enderDragonScaleLeggings && feet == ModArmour.enderDragonScaleBoots))
			return;
        if (player.getHealth() >= 5f && isActive(MobEffects.RESISTANCE, player) && isActive(MobEffects.STRENGTH, player)) return;
        
        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 2, false, false));
        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 0, false, false));
        player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.HOSTILE, 0.047f, 1f);
        world.playEvent(2003, player.getPosition(), 0);
        player.getCooldownTracker().setCooldown(this, 1200);
	}
	
}
