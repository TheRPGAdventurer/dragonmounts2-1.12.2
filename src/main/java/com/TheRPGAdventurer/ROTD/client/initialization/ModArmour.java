package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonArmor;
import com.TheRPGAdventurer.ROTD.client.items.gemset.ItemDragonArmour;
import com.TheRPGAdventurer.ROTD.client.items.gemset.ItemDragonArmour;
import com.TheRPGAdventurer.ROTD.client.items.specialset.ItemEnderDragonArmour;
import com.TheRPGAdventurer.ROTD.client.items.specialset.ItemNetherDragonArmour;
import com.TheRPGAdventurer.ROTD.client.items.specialset.ItemWaterDragonArmour;

import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

public class ModArmour {
	
	public static final ArmorMaterial ForestDragonScaleMaterial        = EnumHelper.addArmorMaterial("forest", DragonMounts.MODID + ":forest", 45, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5.0F);
	public static final ArmorMaterial FireDragonScaleMaterial          = EnumHelper.addArmorMaterial("fire", DragonMounts.MODID + ":fire", 45, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5.0F);
	public static final ArmorMaterial AetherDragonScaleMaterial        = EnumHelper.addArmorMaterial("aether", DragonMounts.MODID + ":aether", 45, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5.0F);
	public static final ArmorMaterial WaterDragonScaleMaterial         = EnumHelper.addArmorMaterial("water", DragonMounts.MODID + ":water", 45, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5.0F);
	public static final ArmorMaterial IceDragonScaleMaterial           = EnumHelper.addArmorMaterial("ice", DragonMounts.MODID + ":ice", 45, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5.0F);
	public static final ArmorMaterial NetherDragonScaleMaterial        = EnumHelper.addArmorMaterial("nether", DragonMounts.MODID + ":nether", 47, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 6.0F);
	public static final ArmorMaterial EnderDragonScaleMaterial         = EnumHelper.addArmorMaterial("ender", DragonMounts.MODID + ":ender", 50, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
	public static final ArmorMaterial EnchantDragonScaleMaterial       = EnumHelper.addArmorMaterial("enchant", DragonMounts.MODID + ":enchant", 45, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5.0F);
	public static final ArmorMaterial SunlightDragonScaleMaterial      = EnumHelper.addArmorMaterial("sunlight", DragonMounts.MODID + ":sunlight", 45, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5.0F);
	public static final ArmorMaterial StormDragonScaleMaterial         = EnumHelper.addArmorMaterial("storm", DragonMounts.MODID + ":storm", 45, new int [] {4,6,8,4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5.0F);
	
	public static ItemDragonArmour forestDragonScaleCap;
	public static ItemDragonArmour forestDragonScaleTunic;
	public static ItemDragonArmour forestDragonScaleLeggings;
	public static ItemDragonArmour forestDragonScaleBoots;
	
	public static ItemDragonArmour fireDragonScaleCap;
	public static ItemDragonArmour fireDragonScaleTunic;
	public static ItemDragonArmour fireDragonScaleLeggings;
	public static ItemDragonArmour fireDragonScaleBoots;
	
	public static ItemDragonArmour aetherDragonScaleCap;
	public static ItemDragonArmour aetherDragonScaleTunic;
	public static ItemDragonArmour aetherDragonScaleLeggings;
	public static ItemDragonArmour aetherDragonScaleBoots;
	
	public static ItemWaterDragonArmour waterDragonScaleCap;
	public static ItemWaterDragonArmour waterDragonscaleChesplate;
	public static ItemWaterDragonArmour waterDragonScaleLeggings;
	public static ItemWaterDragonArmour waterDragonScaleBoots;
	
	public static ItemDragonArmour iceDragonScaleCap;
	public static ItemDragonArmour iceDragonScaleTunic;
	public static ItemDragonArmour iceDragonScaleLeggings;
	public static ItemDragonArmour iceDragonScaleBoots;
	
	public static ItemNetherDragonArmour netherDragonScaleCap;
	public static ItemNetherDragonArmour netherDragonScaleTunic;
	public static ItemNetherDragonArmour netherDragonScaleLeggings;
	public static ItemNetherDragonArmour netherDragonScaleBoots;
	
	public static ItemEnderDragonArmour enderDragonScaleCap;
	public static ItemEnderDragonArmour enderDragonScaleTunic;
	public static ItemEnderDragonArmour enderDragonScaleLeggings;
	public static ItemEnderDragonArmour enderDragonScaleBoots;
	
	public static ItemDragonArmour enchantDragonScaleCap;
	public static ItemDragonArmour enchantDragonScaleTunic;
	public static ItemDragonArmour enchantDragonScaleLeggings;
	public static ItemDragonArmour enchantDragonScaleBoots;
	
	public static ItemDragonArmour sunlightDragonScaleCap;
	public static ItemDragonArmour sunlightDragonScaleTunic;
	public static ItemDragonArmour sunlightDragonScaleLeggings;
	public static ItemDragonArmour sunlightDragonScaleBoots;
	
	public static ItemDragonArmour stormDragonScaleCap;
	public static ItemDragonArmour stormDragonScaleTunic;
	public static ItemDragonArmour stormDragonScaleLeggings;
	public static ItemDragonArmour stormDragonScaleBoots;
	
	// for dragon
	public static ItemDragonArmor dragonarmor_diamond;
	public static ItemDragonArmor dragonarmor_gold;
	public static ItemDragonArmor dragonarmor_iron;
	
	public static final Item[] ARMOR =  {
		forestDragonScaleCap = new ItemDragonArmour(ForestDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "forest_dragonscale_cap", EnumItemBreedTypes.FOREST),
		forestDragonScaleTunic = new ItemDragonArmour(ForestDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "forest_dragonscale_tunic", EnumItemBreedTypes.FOREST),
		forestDragonScaleLeggings = new ItemDragonArmour(ForestDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "forest_dragonscale_leggings", EnumItemBreedTypes.FOREST),
		forestDragonScaleBoots = new ItemDragonArmour(ForestDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "forest_dragonscale_boots", EnumItemBreedTypes.FOREST),
		
		fireDragonScaleCap = new ItemDragonArmour(FireDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "fire_dragonscale_cap", EnumItemBreedTypes.FIRE),
		fireDragonScaleTunic = new ItemDragonArmour(FireDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "fire_dragonscale_tunic", EnumItemBreedTypes.FIRE),
		fireDragonScaleLeggings = new ItemDragonArmour(FireDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "fire_dragonscale_leggings", EnumItemBreedTypes.FIRE),
		fireDragonScaleBoots = new ItemDragonArmour(FireDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "fire_dragonscale_boots", EnumItemBreedTypes.FIRE),
		
		aetherDragonScaleCap = new ItemDragonArmour(AetherDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "aether_dragonscale_cap", EnumItemBreedTypes.AETHER),
		aetherDragonScaleTunic = new ItemDragonArmour(AetherDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "aether_dragonscale_tunic", EnumItemBreedTypes.AETHER),
		aetherDragonScaleLeggings = new ItemDragonArmour(AetherDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "aether_dragonscale_leggings", EnumItemBreedTypes.AETHER),
		aetherDragonScaleBoots = new ItemDragonArmour(AetherDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "aether_dragonscale_boots", EnumItemBreedTypes.AETHER),
		
		iceDragonScaleCap = new ItemDragonArmour(IceDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "ice_dragonscale_cap", EnumItemBreedTypes.ICE),
		iceDragonScaleTunic = new ItemDragonArmour(IceDragonScaleMaterial,  1, EntityEquipmentSlot.CHEST, "ice_dragonscale_tunic", EnumItemBreedTypes.ICE),
		iceDragonScaleLeggings = new ItemDragonArmour(IceDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "ice_dragonscale_leggings", EnumItemBreedTypes.ICE),
		iceDragonScaleBoots = new ItemDragonArmour(IceDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "ice_dragonscale_boots", EnumItemBreedTypes.ICE),
		
		waterDragonScaleCap = new ItemWaterDragonArmour(WaterDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "water_dragonscale_cap", EnumItemBreedTypes.WATER, new PotionEffect(MobEffects.WATER_BREATHING)),
		waterDragonscaleChesplate = new ItemWaterDragonArmour(WaterDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "water_dragonscale_tunic", EnumItemBreedTypes.WATER, new PotionEffect(MobEffects.WATER_BREATHING)),
		waterDragonScaleLeggings = new ItemWaterDragonArmour(WaterDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "water_dragonscale_leggings", EnumItemBreedTypes.WATER, new PotionEffect(MobEffects.WATER_BREATHING)),
		waterDragonScaleBoots = new ItemWaterDragonArmour(WaterDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "water_dragonscale_boots", EnumItemBreedTypes.WATER, new PotionEffect(MobEffects.WATER_BREATHING)),
		
		netherDragonScaleCap = new ItemNetherDragonArmour(NetherDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "nether_dragonscale_cap", new PotionEffect(MobEffects.RESISTANCE, 2, 1, true, false), EnumItemBreedTypes.NETHER),
		netherDragonScaleTunic = new ItemNetherDragonArmour(NetherDragonScaleMaterial,  1, EntityEquipmentSlot.CHEST, "nether_dragonscale_tunic", new PotionEffect(MobEffects.RESISTANCE, 2, 1, true, false), EnumItemBreedTypes.NETHER),
		netherDragonScaleLeggings = new ItemNetherDragonArmour(NetherDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "nether_dragonscale_leggings", new PotionEffect(MobEffects.RESISTANCE, 2, 1, true, false), EnumItemBreedTypes.NETHER),
		netherDragonScaleBoots = new ItemNetherDragonArmour(NetherDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "nether_dragonscale_boots", new PotionEffect(MobEffects.RESISTANCE, 2, 1, true, false), EnumItemBreedTypes.NETHER),
		
		enderDragonScaleCap = new ItemEnderDragonArmour(EnderDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "ender_dragonscale_cap", new PotionEffect(MobEffects.RESISTANCE, 2, 2, true, false), EnumItemBreedTypes.END),
		enderDragonScaleTunic = new ItemEnderDragonArmour(EnderDragonScaleMaterial,  1, EntityEquipmentSlot.CHEST, "ender_dragonscale_tunic", new PotionEffect(MobEffects.RESISTANCE, 2, 2, true, false), EnumItemBreedTypes.END),
		enderDragonScaleLeggings = new ItemEnderDragonArmour(EnderDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "ender_dragonscale_leggings", new PotionEffect(MobEffects.RESISTANCE, 2, 2, true, false), EnumItemBreedTypes.END),
		enderDragonScaleBoots = new ItemEnderDragonArmour(EnderDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "ender_dragonscale_boots", new PotionEffect(MobEffects.RESISTANCE, 2, 2, true, false), EnumItemBreedTypes.END),
		
		enchantDragonScaleCap = new ItemDragonArmour(EnchantDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "enchant_dragonscale_cap", EnumItemBreedTypes.ENCHANT),
		enchantDragonScaleTunic = new ItemDragonArmour(EnchantDragonScaleMaterial,  1, EntityEquipmentSlot.CHEST, "enchant_dragonscale_tunic", EnumItemBreedTypes.ENCHANT),
		enchantDragonScaleLeggings = new ItemDragonArmour(EnchantDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "enchant_dragonscale_leggings", EnumItemBreedTypes.ENCHANT),
		enchantDragonScaleBoots = new ItemDragonArmour(EnchantDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "enchant_dragonscale_boots", EnumItemBreedTypes.ENCHANT),
				
		sunlightDragonScaleCap = new ItemDragonArmour(SunlightDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "sunlight_dragonscale_cap", EnumItemBreedTypes.SUNLIGHT),
		sunlightDragonScaleTunic = new ItemDragonArmour(SunlightDragonScaleMaterial,  1, EntityEquipmentSlot.CHEST, "sunlight_dragonscale_tunic", EnumItemBreedTypes.SUNLIGHT),
		sunlightDragonScaleLeggings = new ItemDragonArmour(SunlightDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "sunlight_dragonscale_leggings", EnumItemBreedTypes.SUNLIGHT),
		sunlightDragonScaleBoots = new ItemDragonArmour(SunlightDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "sunlight_dragonscale_boots", EnumItemBreedTypes.SUNLIGHT),
						
		stormDragonScaleCap = new ItemDragonArmour(StormDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "storm_dragonscale_cap", EnumItemBreedTypes.STORM),
		stormDragonScaleTunic = new ItemDragonArmour(StormDragonScaleMaterial,  1, EntityEquipmentSlot.CHEST, "storm_dragonscale_tunic", EnumItemBreedTypes.STORM),
		stormDragonScaleLeggings = new ItemDragonArmour(StormDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "storm_dragonscale_leggings", EnumItemBreedTypes.STORM),
		stormDragonScaleBoots = new ItemDragonArmour(StormDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "storm_dragonscale_boots", EnumItemBreedTypes.STORM),
								
		
		dragonarmor_iron = new ItemDragonArmor( "dragonarmor_iron"),
		dragonarmor_gold = new ItemDragonArmor("dragonarmor_gold"),
		dragonarmor_diamond = new ItemDragonArmor("dragonarmor_diamond"),
		
	};
	
	public static void initRepairs() {
		WaterDragonScaleMaterial. setRepairItem(new ItemStack(ModItems.WaterDragonScales));
		AetherDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.AetherDragonScales));
		ForestDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.ForestDragonScales));
		FireDragonScaleMaterial.  setRepairItem(new ItemStack(ModItems.FireDragonScales));
		IceDragonScaleMaterial.   setRepairItem(new ItemStack(ModItems.IceDragonScales));
		NetherDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.NetherDragonScales));
		EnderDragonScaleMaterial. setRepairItem(new ItemStack(ModItems.EnderDragonScales));
	}
		
}