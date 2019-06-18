package com.TheRPGAdventurer.ROTD.inits;

import java.util.ArrayList;
import java.util.List;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.objects.items.ItemDragonArmor;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourAether;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourEnchant;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourEnder;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourFire;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourForest;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourIce;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourMoon;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourNether;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourStorm;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourSunlight;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourTerra;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourWater;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset.DragonArmourZombie;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class ModArmour {
	
	public static final List<Item> ARMOR = new ArrayList<Item>();

    public static final ArmorMaterial ForestDragonScaleMaterial = EnumHelper.addArmorMaterial("forest", DragonMounts.MODID + ":forest", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial FireDragonScaleMaterial = EnumHelper.addArmorMaterial("fire", DragonMounts.MODID + ":fire", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial FireDragonScaleMaterial2 = EnumHelper.addArmorMaterial("fire2", DragonMounts.MODID + ":fire2", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial AetherDragonScaleMaterial = EnumHelper.addArmorMaterial("aether", DragonMounts.MODID + ":aether", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial WaterDragonScaleMaterial = EnumHelper.addArmorMaterial("water", DragonMounts.MODID + ":water", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial IceDragonScaleMaterial = EnumHelper.addArmorMaterial("ice", DragonMounts.MODID + ":ice", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial NetherDragonScaleMaterial = EnumHelper.addArmorMaterial("nether", DragonMounts.MODID + ":nether", 55, new int[]{4, 7, 9, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 8.0F);
    public static final ArmorMaterial NetherDragonScaleMaterial2 = EnumHelper.addArmorMaterial("nether2", DragonMounts.MODID + ":nether2", 55, new int[]{4, 7, 9, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 8.0F);
    public static final ArmorMaterial EnderDragonScaleMaterial = EnumHelper.addArmorMaterial("ender", DragonMounts.MODID + ":ender", 70, new int[]{4, 7, 9, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 9.0F);
    public static final ArmorMaterial EnchantDragonScaleMaterial = EnumHelper.addArmorMaterial("enchant", DragonMounts.MODID + ":enchant", 50, new int[]{4, 7, 8, 4}, 30, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial SunlightDragonScaleMaterial = EnumHelper.addArmorMaterial("sunlight", DragonMounts.MODID + ":sunlight", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial SunlightDragonScaleMaterial2 = EnumHelper.addArmorMaterial("sunlight2", DragonMounts.MODID + ":sunlight2", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial StormDragonScaleMaterial = EnumHelper.addArmorMaterial("storm", DragonMounts.MODID + ":storm", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial StormDragonScaleMaterial2 = EnumHelper.addArmorMaterial("storm2", DragonMounts.MODID + ":storm2", 50, new int[]{4, 7, 8, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial ZombieDragonScaleMaterial = EnumHelper.addArmorMaterial("zombie", DragonMounts.MODID + ":zombie", 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial TerraDragonScaleMaterial = EnumHelper.addArmorMaterial("terra", DragonMounts.MODID + ":terra", 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial TerraDragonScaleMaterial2 = EnumHelper.addArmorMaterial("terra2", DragonMounts.MODID + ":terra2", 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial MoonlightDragonScaleMaterial = EnumHelper.addArmorMaterial("moonlight", DragonMounts.MODID + ":moonlight", 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial GhostDragonScaleMaterial = EnumHelper.addArmorMaterial("ghost", DragonMounts.MODID + ":ghost", 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);
    public static final ArmorMaterial WitherDragonScaleMaterial = EnumHelper.addArmorMaterial("wither", DragonMounts.MODID + ":wither", 50, new int[]{3, 7, 8, 3}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 7.0F);

    public static final Item dragonarmor_iron = new ItemDragonArmor("dragonarmor_iron");
    public static final Item dragonarmor_gold = new ItemDragonArmor("dragonarmor_gold");
    public static final Item dragonarmor_diamond = new ItemDragonArmor("dragonarmor_diamond");
    public static final Item dragonarmor_emerald = new ItemDragonArmor("dragonarmor_emerald");

    public static final Item forestDragonScaleCap = new DragonArmourForest(ForestDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "forest_dragonscale_cap");
    public static final Item forestDragonScaleTunic = new DragonArmourForest(ForestDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "forest_dragonscale_tunic");
    public static final Item forestDragonScaleLeggings = new DragonArmourForest(ForestDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "forest_dragonscale_leggings");
    public static final Item forestDragonScaleBoots = new DragonArmourForest(ForestDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "forest_dragonscale_boots");

	public static final Item fireDragonScaleCap = new DragonArmourFire(FireDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "fire_dragonscale_cap");
	public static final Item fireDragonScaleTunic = new DragonArmourFire(FireDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "fire_dragonscale_tunic");
	public static final Item fireDragonScaleLeggings = new DragonArmourFire(FireDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "fire_dragonscale_leggings");
	public static final Item fireDragonScaleBoots = new DragonArmourFire(FireDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "fire_dragonscale_boots");

	public static final Item fireDragonScaleCap2 = new DragonArmourFire(FireDragonScaleMaterial2, 1, EntityEquipmentSlot.HEAD, "fire2_dragonscale_cap");
	public static final Item fireDragonScaleTunic2 = new DragonArmourFire(FireDragonScaleMaterial2, 1, EntityEquipmentSlot.CHEST, "fire2_dragonscale_tunic");
	public static final Item fireDragonScaleLeggings2 = new DragonArmourFire(FireDragonScaleMaterial2, 2, EntityEquipmentSlot.LEGS, "fire2_dragonscale_leggings");
	public static final Item fireDragonScaleBoots2 = new DragonArmourFire(FireDragonScaleMaterial2, 1, EntityEquipmentSlot.FEET, "fire2_dragonscale_boots");

	public static final Item aetherDragonScaleCap = new DragonArmourAether(AetherDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "aether_dragonscale_cap");
	public static final Item aetherDragonScaleTunic = new DragonArmourAether(AetherDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "aether_dragonscale_tunic");
	public static final Item aetherDragonScaleLeggings = new DragonArmourAether(AetherDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "aether_dragonscale_leggings");
	public static final Item aetherDragonScaleBoots = new DragonArmourAether(AetherDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "aether_dragonscale_boots");

	public static final Item iceDragonScaleCap = new DragonArmourIce(IceDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "ice_dragonscale_cap");
	public static final Item iceDragonScaleTunic = new DragonArmourIce(IceDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "ice_dragonscale_tunic");
	public static final Item iceDragonScaleLeggings = new DragonArmourIce(IceDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "ice_dragonscale_leggings");
	public static final Item iceDragonScaleBoots = new DragonArmourIce(IceDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "ice_dragonscale_boots");

	public static final Item waterDragonScaleCap = new DragonArmourWater(WaterDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "water_dragonscale_cap");
	public static final Item waterDragonscaleChesplate = new DragonArmourWater(WaterDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "water_dragonscale_tunic");
	public static final Item waterDragonScaleLeggings = new DragonArmourWater(WaterDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "water_dragonscale_leggings");
	public static final Item waterDragonScaleBoots = new DragonArmourWater(WaterDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "water_dragonscale_boots");

	public static final Item netherDragonScaleCap = new DragonArmourNether(NetherDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "nether_dragonscale_cap");
	public static final Item netherDragonScaleTunic = new DragonArmourNether(NetherDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "nether_dragonscale_tunic");
	public static final Item netherDragonScaleLeggings = new DragonArmourNether(NetherDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "nether_dragonscale_leggings");
	public static final Item netherDragonScaleBoots = new DragonArmourNether(NetherDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "nether_dragonscale_boots");

	public static final Item netherDragonScaleCap2 = new DragonArmourNether(NetherDragonScaleMaterial2, 1, EntityEquipmentSlot.HEAD, "nether2_dragonscale_cap");
	public static final Item netherDragonScaleTunic2 = new DragonArmourNether(NetherDragonScaleMaterial2, 1, EntityEquipmentSlot.CHEST, "nether2_dragonscale_tunic");
	public static final Item netherDragonScaleLeggings2 = new DragonArmourNether(NetherDragonScaleMaterial2, 2, EntityEquipmentSlot.LEGS, "nether2_dragonscale_leggings");
	public static final Item netherDragonScaleBoots2 = new DragonArmourNether(NetherDragonScaleMaterial2, 1, EntityEquipmentSlot.FEET, "nether2_dragonscale_boots");

	public static final Item enderDragonScaleCap = new DragonArmourEnder(EnderDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "ender_dragonscale_cap");
	public static final Item enderDragonScaleTunic = new DragonArmourEnder(EnderDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "ender_dragonscale_tunic");
	public static final Item enderDragonScaleLeggings = new DragonArmourEnder(EnderDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "ender_dragonscale_leggings");
	public static final Item enderDragonScaleBoots = new DragonArmourEnder(EnderDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "ender_dragonscale_boots");

	public static final Item enchantDragonScaleCap = new DragonArmourEnchant(EnchantDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "enchant_dragonscale_cap");
	public static final Item enchantDragonScaleTunic = new DragonArmourEnchant(EnchantDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "enchant_dragonscale_tunic");
	public static final Item enchantDragonScaleLeggings = new DragonArmourEnchant(EnchantDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "enchant_dragonscale_leggings");
	public static final Item enchantDragonScaleBoots = new DragonArmourEnchant(EnchantDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "enchant_dragonscale_boots");

	public static final Item sunlightDragonScaleCap = new DragonArmourSunlight(SunlightDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "sunlight_dragonscale_cap");
	public static final Item sunlightDragonScaleTunic = new DragonArmourSunlight(SunlightDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "sunlight_dragonscale_tunic");
	public static final Item sunlightDragonScaleLeggings = new DragonArmourSunlight(SunlightDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "sunlight_dragonscale_leggings");
	public static final Item sunlightDragonScaleBoots = new DragonArmourSunlight(SunlightDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "sunlight_dragonscale_boots");

	public static final Item sunlightDragonScaleCap2 = new DragonArmourSunlight(SunlightDragonScaleMaterial2, 1, EntityEquipmentSlot.HEAD, "sunlight2_dragonscale_cap");
	public static final Item sunlightDragonScaleTunic2 = new DragonArmourSunlight(SunlightDragonScaleMaterial2, 1, EntityEquipmentSlot.CHEST, "sunlight2_dragonscale_tunic");
	public static final Item sunlightDragonScaleLeggings2 = new DragonArmourSunlight(SunlightDragonScaleMaterial2, 2, EntityEquipmentSlot.LEGS, "sunlight2_dragonscale_leggings");
	public static final Item sunlightDragonScaleBoots2 = new DragonArmourSunlight(SunlightDragonScaleMaterial2, 1, EntityEquipmentSlot.FEET, "sunlight2_dragonscale_boots");

	public static final Item stormDragonScaleCap = new DragonArmourStorm(StormDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "storm_dragonscale_cap");
	public static final Item stormDragonScaleTunic = new DragonArmourStorm(StormDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "storm_dragonscale_tunic");
	public static final Item stormDragonScaleLeggings = new DragonArmourStorm(StormDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "storm_dragonscale_leggings");
	public static final Item stormDragonScaleBoots = new DragonArmourStorm(StormDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "storm_dragonscale_boots");

	public static final Item stormDragonScaleCap2 = new DragonArmourStorm(StormDragonScaleMaterial2, 1, EntityEquipmentSlot.HEAD, "storm2_dragonscale_cap");
	public static final Item stormDragonScaleTunic2 = new DragonArmourStorm(StormDragonScaleMaterial2, 1, EntityEquipmentSlot.CHEST, "storm2_dragonscale_tunic");
	public static final Item stormDragonScaleLeggings2 = new DragonArmourStorm(StormDragonScaleMaterial2, 2, EntityEquipmentSlot.LEGS, "storm2_dragonscale_leggings");
	public static final Item stormDragonScaleBoots2 = new DragonArmourStorm(StormDragonScaleMaterial2, 1, EntityEquipmentSlot.FEET, "storm2_dragonscale_boots");

	public static final Item zombieDragonScaleCap = new DragonArmourZombie(ZombieDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "zombie_dragonscale_cap");
	public static final Item zombieDragonScaleTunic = new DragonArmourZombie(ZombieDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "zombie_dragonscale_tunic");
	public static final Item zombieDragonScaleLeggings = new DragonArmourZombie(ZombieDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "zombie_dragonscale_leggings");
	public static final Item zombieDragonScaleBoots = new DragonArmourZombie(ZombieDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "zombie_dragonscale_boots");

	public static final Item terraDragonScaleCap = new DragonArmourTerra(TerraDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "terra_dragonscale_cap");
	public static final Item terraDragonScaleTunic = new DragonArmourTerra(TerraDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "terra_dragonscale_tunic");
	public static final Item terraDragonScaleLeggings = new DragonArmourTerra(TerraDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "terra_dragonscale_leggings");
	public static final Item terraDragonScaleBoots = new DragonArmourTerra(TerraDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "terra_dragonscale_boots");

	public static final Item terra2DragonScaleCap = new DragonArmourTerra(TerraDragonScaleMaterial2, 1, EntityEquipmentSlot.HEAD, "terra2_dragonscale_cap");
	public static final Item terra2DragonScaleTunic = new DragonArmourTerra(TerraDragonScaleMaterial2, 1, EntityEquipmentSlot.CHEST, "terra2_dragonscale_tunic");
	public static final Item terra2DragonScaleLeggings = new DragonArmourTerra(TerraDragonScaleMaterial2, 2, EntityEquipmentSlot.LEGS, "terra2_dragonscale_leggings");
	public static final Item terra2DragonScaleBoots = new DragonArmourTerra(TerraDragonScaleMaterial2, 1, EntityEquipmentSlot.FEET, "terra2_dragonscale_boots");

	public static final Item moonlightDragonScaleCap = new DragonArmourMoon(MoonlightDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "moonlight_dragonscale_cap");
	public static final Item moonlightDragonScaleTunic = new DragonArmourMoon(MoonlightDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "moonlight_dragonscale_tunic");
	public static final Item moonlightDragonScaleLeggings = new DragonArmourMoon(MoonlightDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "moonlight_dragonscale_leggings");
	public static final Item moonlightDragonScaleBoots = new DragonArmourMoon(MoonlightDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "moonlight_dragonscale_boots");

/*	public static final Item ghostDragonScaleCap = new ItemDragonArmour(GhostDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "ghost_dragonscale_cap");
	public static final Item ghostDragonScaleTunic = new ItemDragonArmour(GhostDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "ghost_dragonscale_tunic");
	public static final Item ghostDragonScaleLeggings = new ItemDragonArmour(GhostDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "ghost_dragonscale_leggings");
	public static final Item ghostDragonScaleBoots = new ItemDragonArmour(GhostDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "ghost_dragonscale_boots");

	public static final Item witherDragonScaleCap = new ItemDragonArmour(WitherDragonScaleMaterial, 1, EntityEquipmentSlot.HEAD, "wither_dragonscale_cap");
	public static final Item witherDragonScaleTunic = new ItemDragonArmour(WitherDragonScaleMaterial, 1, EntityEquipmentSlot.CHEST, "wither_dragonscale_tunic");
	public static final Item witherDragonScaleLeggings = new ItemDragonArmour(WitherDragonScaleMaterial, 2, EntityEquipmentSlot.LEGS, "wither_dragonscale_leggings");
	public static final Item witherDragonScaleBoots = new ItemDragonArmour(WitherDragonScaleMaterial, 1, EntityEquipmentSlot.FEET, "wither_dragonscale_boots");
*/
    public static void InitializaRepairs() {
        WaterDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.WaterDragonScales));
        AetherDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.AetherDragonScales));
        ForestDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.ForestDragonScales));
        FireDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.FireDragonScales));
        FireDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.FireDragonScales2));
        IceDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.IceDragonScales));
        NetherDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.NetherDragonScales));
        NetherDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.NetherDragonScales2));
        EnderDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.EnderDragonScales));
        EnchantDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.EnchantDragonScales));
        ZombieDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.ZombieDragonScales));
        TerraDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.TerraDragonScales));
        TerraDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.TerraDragonScales2));
        SunlightDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.SunlightDragonScales));
        SunlightDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.SunlightDragonScales2));
        StormDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.StormDragonScales));
        StormDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.StormDragonScales2));
        MoonlightDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.MoonlightDragonScales));
    }
}