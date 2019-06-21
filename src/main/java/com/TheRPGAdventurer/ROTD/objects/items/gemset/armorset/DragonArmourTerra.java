package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DragonArmourTerra extends DragonArmourBase {

    public DragonArmourTerra(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
        super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.TERRA);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        if (!((head == ModArmour.terraDragonScaleCap || head == ModArmour.terra2DragonScaleCap) && (chest == ModArmour.terraDragonScaleTunic || chest == ModArmour.terra2DragonScaleTunic)
                && (legs == ModArmour.terraDragonScaleLeggings || legs == ModArmour.terra2DragonScaleLeggings) && (feet == ModArmour.terraDragonScaleBoots || feet == ModArmour.terra2DragonScaleBoots)))
            return;

        if ((player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) && !isActive(MobEffects.HASTE, player))
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE));
    }

}
