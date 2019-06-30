package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DragonArmourForest extends DragonArmourBase {

    public DragonArmourForest(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName, EnumItemBreedTypes type) {
        super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, type);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.getCooldownTracker().getCooldown(this, 0) > 0) return;
        super.onArmorTick(world, player, itemStack);
        if (!((head == ModArmour.forestDragonScaleCap || head == ModArmour.forestDragonScaleCap2) && (chest == ModArmour.forestDragonScaleTunic || chest == ModArmour.forestDragonScaleTunic2) && (legs == ModArmour.forestDragonScaleLeggings || legs == ModArmour.forestDragonScaleLeggings2) && (feet == ModArmour.forestDragonScaleBoots || feet == ModArmour.forestDragonScaleBoots2)))
            return;
        if (player.fishEntity != null && !isActive(MobEffects.LUCK, player))
            player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 210, 0, false, false));
        if (player.getCooldownTracker().getCooldown(this, 0) > 0 && (player.getHealth() < 10f))
            return; // check this after because luck should not be a perma effect

        if (player.hurtTime > 0 && !isActive(MobEffects.REGENERATION, player)) {
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1, false, true));
            player.getCooldownTracker().setCooldown(this, 1160); //Relatively high because this is op af
        }
    }
}
