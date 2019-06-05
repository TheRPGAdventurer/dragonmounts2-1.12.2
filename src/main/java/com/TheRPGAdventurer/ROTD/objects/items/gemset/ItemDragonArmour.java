package com.TheRPGAdventurer.ROTD.objects.items.gemset;


import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemDragonArmour extends ItemArmor {
	
    public EnumItemBreedTypes type;

	public ItemDragonArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName,  EnumItemBreedTypes type) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName("dragonscale_" + equipmentSlotIn.toString().toLowerCase());
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, unlocalizedName));
		this.setCreativeTab(DragonMounts.armoryTab);
		this.type = type;


		new ItemStack(this).setStackDisplayName(type.color + new ItemStack(this).getDisplayName());
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(type.color + DMUtils.translateToLocal("dragon." + type.toString().toLowerCase()));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		Item head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
		Item chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
		Item legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
		Item feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

		if(head == ModArmour.forestDragonScaleCap && player.fishEntity != null && new Random().nextInt(25) == 1) player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 300,0, false, false));
		if(head == ModArmour.moonlightDragonScaleCap && !player.world.isDaytime() && new Random().nextInt(25) == 1) player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300,0, false, false));
		if(chest == ModArmour.forestDragonScaleTunic && player.getAttackingEntity() != null && new Random().nextInt(25) == 1) player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200,0, false, false));
		if(chest == ModArmour.iceDragonScaleTunic && player.getAttackingEntity() != null && new Random().nextInt(25) == 1) player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200,0, false, false));

		if((head == ModArmour.fireDragonScaleCap && chest == ModArmour.fireDragonScaleTunic && legs == ModArmour.fireDragonScaleLeggings && feet == ModArmour.fireDragonScaleBoots)
		|| (head == ModArmour.fireDragonScaleCap2 && chest == ModArmour.fireDragonScaleTunic2 && legs == ModArmour.fireDragonScaleLeggings2 && feet == ModArmour.fireDragonScaleBoots2)
		&& (player.isInLava() || player.world.isMaterialInBB(player.getEntityBoundingBox().grow(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.FIRE) || player.isBurning()))
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 300,0, false, false));

		if (head == ModArmour.waterDragonScaleCap && player.isInWater()) { // If the Potion isn't currently active,
			player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 300,0, false, false));
		}

	}
}