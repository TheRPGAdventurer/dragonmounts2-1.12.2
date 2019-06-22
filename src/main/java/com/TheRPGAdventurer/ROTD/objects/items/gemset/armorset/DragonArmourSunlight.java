package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DragonArmourSunlight extends DragonArmourBase {

	public DragonArmourSunlight(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.SUNLIGHT);
	}

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.getCooldownTracker().getCooldown(this, 0) > 0) return;
        super.onArmorTick(world, player, itemStack);
        if (!(head == ModArmour.forestDragonScaleCap && chest == ModArmour.forestDragonScaleTunic && legs == ModArmour.forestDragonScaleLeggings && feet == ModArmour.forestDragonScaleBoots))
            return;
        if (player.fishEntity != null && !isActive(MobEffects.LUCK, player))
            player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 210, 0, false, false));
        if (player.getCooldownTracker().getCooldown(this, 0) > 0 && (player.getFoodStats().getFoodLevel() < 10f))
            return; // check this after because luck should not be a perma effect

        if (!isActive(MobEffects.SATURATION, player) && world.isDaytime()) {
            player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 200, 1, false, true));
            player.getCooldownTracker().setCooldown(this, 1160); // you could survive without eating with this one
        }
    }
}
