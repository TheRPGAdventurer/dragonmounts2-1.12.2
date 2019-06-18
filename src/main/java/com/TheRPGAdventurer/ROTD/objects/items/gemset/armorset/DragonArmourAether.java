package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DragonArmourAether extends DragonArmourBase {

    public DragonArmourAether(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
        super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.AETHER);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.getCooldownTracker().getCooldown(this, 0) > 0) return;
        super.onArmorTick(world, player, itemStack);
        if (!(head == ModArmour.aetherDragonScaleCap && chest == ModArmour.aetherDragonScaleTunic && legs == ModArmour.aetherDragonScaleLeggings && feet == ModArmour.aetherDragonScaleBoots))
            return;
        if (!player.isSprinting() && isActive(MobEffects.SPEED, player)) return;

        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100, 1));
        player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_GUARDIAN_HURT, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        player.getCooldownTracker().setCooldown(this, 300); //260
    }

}
