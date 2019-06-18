package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DragonArmourZombie extends DragonArmourBase {

    public DragonArmourZombie(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
        super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.ZOMBIE);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.getCooldownTracker().getCooldown(this, 0) > 0) return;
        super.onArmorTick(world, player, itemStack);
        if (!(head == ModArmour.zombieDragonScaleCap && chest == ModArmour.zombieDragonScaleTunic && legs == ModArmour.zombieDragonScaleLeggings && feet == ModArmour.zombieDragonScaleBoots))
            return;
        if (world.isDaytime() && isActive(MobEffects.STRENGTH, player)) return;

        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 0, true, false));
        player.getCooldownTracker().setCooldown(this, 400);
    }

}
