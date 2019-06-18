package com.TheRPGAdventurer.ROTD.inits;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.objects.items.ItemDiamondShears;
import com.TheRPGAdventurer.ROTD.objects.items.bow.*;
import com.TheRPGAdventurer.ROTD.objects.items.gemset.*;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class ModTools {
	//Tool Array
	public static final List<Item> TOOLS = new ArrayList<Item>();
	//Bow Array - Separate for Bow Anim
    public static final List<Item> BOWS = new ArrayList<Item>();
	
	static float damage = 10.0F; static float speed = -2.8F;
	static float netherDamage = 12.0F; static float netherSpeed = -2.9F;
	static float enderDamage = 9.0F; static float enderSpeed = -2.9F;
 
	public static final ToolMaterial ForestDragonScaleMaterial	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":forestdragonscales",   4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial FireDragonScaleMaterial	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":icedragonscales",      4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial FireDragonScaleMaterial2	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":icedragonscales2",      4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial AetherDragonScaleMaterial	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":aetherdragonscales",   5, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial IceDragonScaleMaterial	      = EnumHelper.addToolMaterial(DragonMounts.MODID + ":firedragonscales",     4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial WaterDragonScaleMaterial	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":waterdragonscales",    4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial NetherDragonScaleMaterial	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":netherdragonscales",   5, 2700, 8.0F, 6.0F, 11);
	public static final ToolMaterial NetherDragonScaleMaterial2	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":netherdragonscales2",   5, 2700, 8.0F, 6.0F, 11);
	public static final ToolMaterial EnderDragonScaleMaterial	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":enderdragonscales",    5, 3000, 8.0F, 6.0F, 11);
	public static final ToolMaterial EnchantDragonScaleMaterial	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":enchantdragonscales",  4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial StormDragonScaleMaterial     = EnumHelper.addToolMaterial(DragonMounts.MODID + ":stormdragonscales",    4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial SunlightDragonScaleMaterial  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":sunlightdragonscales", 4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial StormDragonScaleMaterial2    = EnumHelper.addToolMaterial(DragonMounts.MODID + ":stormdragonscales2",    4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial SunlightDragonScaleMaterial2 = EnumHelper.addToolMaterial(DragonMounts.MODID + ":sunlightdragonscales2", 4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial TerraDragonScaleMaterial     = EnumHelper.addToolMaterial(DragonMounts.MODID + ":terradragonscales", 4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial TerraDragonScaleMaterial2    = EnumHelper.addToolMaterial(DragonMounts.MODID + ":terra2dragonscales", 4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial MoonlightDragonScaleMaterial = EnumHelper.addToolMaterial(DragonMounts.MODID + ":moonlightdragonscales", 4, 2700, 8.0F, 5.0F, 11);
	public static final ToolMaterial ZombieDragonScaleMaterial	  = EnumHelper.addToolMaterial(DragonMounts.MODID + ":zombiedragonscales", 4, 2700, 8.0F, 5.0F, 11);

	//Forest Start
	public static final ItemDragonSword forestDragonSword = new ItemDragonSword(ForestDragonScaleMaterial, "forest_dragon_sword", EnumItemBreedTypes.FOREST);
	public static final ItemDragonPickAxe forestDragonPickaxe = new ItemDragonPickAxe(ForestDragonScaleMaterial, "forest_dragon_pickaxe", EnumItemBreedTypes.FOREST);
	public static final ItemDragonAxe  forestDragonAxe = new ItemDragonAxe(ForestDragonScaleMaterial, "forest_dragon_axe", damage, speed, EnumItemBreedTypes.FOREST);
	public static final ItemDragonShovel forestDragonShovel = new ItemDragonShovel(ForestDragonScaleMaterial, "forest_dragon_shovel", EnumItemBreedTypes.FOREST);
	public static final ItemDragonHoe forestDragonHoe = new ItemDragonHoe(ForestDragonScaleMaterial, "forest_dragon_hoe", EnumItemBreedTypes.FOREST);

	//Aether Start
	public static final ItemDragonSword aetherDragonSword = new ItemDragonSword(AetherDragonScaleMaterial, "aether_dragon_sword", EnumItemBreedTypes.AETHER);
	public static final ItemDragonPickAxe aetherDragonPickaxe = new ItemDragonPickAxe(AetherDragonScaleMaterial, "aether_dragon_pickaxe", EnumItemBreedTypes.AETHER);
	public static final ItemDragonAxe aetherDragonAxe = new ItemDragonAxe(AetherDragonScaleMaterial, "aether_dragon_axe", damage, speed, EnumItemBreedTypes.AETHER);
	public static final ItemDragonShovel aetherDragonShovel = new ItemDragonShovel(AetherDragonScaleMaterial, "aether_dragon_shovel", EnumItemBreedTypes.AETHER);
	public static final ItemDragonHoe aetherDragonHoe = new ItemDragonHoe(AetherDragonScaleMaterial, "aether_dragon_hoe", EnumItemBreedTypes.AETHER);

	//Water Start
	public static final ItemDragonPickAxe waterDragonPickaxe = new ItemDragonPickAxe(WaterDragonScaleMaterial, "water_dragon_pickaxe", EnumItemBreedTypes.WATER);
	public static final ItemDragonAxe waterDragonAxe = new ItemDragonAxe(WaterDragonScaleMaterial, "water_dragon_axe", damage, speed, EnumItemBreedTypes.WATER);
	public static final ItemDragonShovel waterDragonShovel = new ItemDragonShovel(WaterDragonScaleMaterial, "water_dragon_shovel", EnumItemBreedTypes.WATER);
	public static final ItemDragonSword waterDragonSword = new ItemDragonSword(WaterDragonScaleMaterial, "water_dragon_sword", EnumItemBreedTypes.WATER);
	public static final ItemDragonHoe waterDragonHoe = new ItemDragonHoe(WaterDragonScaleMaterial, "water_dragon_hoe", EnumItemBreedTypes.WATER);
    
	//Fire Start
	public static final ItemDragonSword fireDragonSword = new ItemDragonSword(FireDragonScaleMaterial, "fire_dragon_sword", EnumItemBreedTypes.FIRE);   
	public static final ItemDragonPickAxe fireDragonPickaxe = new ItemDragonPickAxe(FireDragonScaleMaterial, "fire_dragon_pickaxe", EnumItemBreedTypes.FIRE);
	public static final ItemDragonAxe fireDragonAxe = new ItemDragonAxe(FireDragonScaleMaterial, "fire_dragon_axe", damage, speed, EnumItemBreedTypes.FIRE);
	public static final ItemDragonShovel fireDragonShovel = new ItemDragonShovel(FireDragonScaleMaterial, "fire_dragon_shovel", EnumItemBreedTypes.FIRE);
	public static final ItemDragonHoe fireDragonHoe = new ItemDragonHoe(FireDragonScaleMaterial, "fire_dragon_hoe", EnumItemBreedTypes.FIRE);

	//Fire2 Start
	public static final ItemDragonSword fireDragonSword2 = new ItemDragonSword(FireDragonScaleMaterial2, "fire2_dragon_sword", EnumItemBreedTypes.FIRE);   
	public static final ItemDragonPickAxe fireDragonPickaxe2 = new ItemDragonPickAxe(FireDragonScaleMaterial2, "fire2_dragon_pickaxe", EnumItemBreedTypes.FIRE);
	public static final ItemDragonAxe fireDragonAxe2 = new ItemDragonAxe(FireDragonScaleMaterial2, "fire2_dragon_axe", damage, speed, EnumItemBreedTypes.FIRE);
	public static final ItemDragonShovel fireDragonShovel2 = new ItemDragonShovel(FireDragonScaleMaterial2, "fire2_dragon_shovel", EnumItemBreedTypes.FIRE);
	public static final ItemDragonHoe fireDragonHoe2 = new ItemDragonHoe(FireDragonScaleMaterial2, "fire2_dragon_hoe", EnumItemBreedTypes.FIRE);

	//Ice Start
    public static final ItemDragonSword iceDragonSword = new ItemDragonSword(IceDragonScaleMaterial, "ice_dragon_sword", EnumItemBreedTypes.ICE);
	public static final ItemDragonPickAxe iceDragonPickaxe = new ItemDragonPickAxe(IceDragonScaleMaterial, "ice_dragon_pickaxe", EnumItemBreedTypes.ICE);
	public static final ItemDragonAxe iceDragonAxe = new ItemDragonAxe(IceDragonScaleMaterial, "ice_dragon_axe", damage, speed, EnumItemBreedTypes.ICE);
	public static final ItemDragonShovel iceDragonShovel = new ItemDragonShovel(IceDragonScaleMaterial, "ice_dragon_shovel", EnumItemBreedTypes.ICE);
    public static final ItemDragonHoe iceDragonHoe = new ItemDragonHoe(IceDragonScaleMaterial, "ice_dragon_hoe", EnumItemBreedTypes.ICE);
	
    //Nether Start
	public static final ItemDragonSword netherDragonSword = new ItemDragonSword(NetherDragonScaleMaterial, "nether_dragon_sword", EnumItemBreedTypes.NETHER);
	public static final ItemDragonPickAxe netherDragonPickaxe = new ItemDragonPickAxe(NetherDragonScaleMaterial, "nether_dragon_pickaxe", EnumItemBreedTypes.NETHER);
	public static final ItemDragonAxe netherDragonAxe = new ItemDragonAxe(NetherDragonScaleMaterial, "nether_dragon_axe", netherDamage, netherSpeed, EnumItemBreedTypes.NETHER);
	public static final ItemDragonShovel netherDragonShovel = new ItemDragonShovel(NetherDragonScaleMaterial, "nether_dragon_shovel", EnumItemBreedTypes.NETHER);
	public static final ItemDragonHoe netherDragonHoe = new ItemDragonHoe(NetherDragonScaleMaterial, "nether_dragon_hoe", EnumItemBreedTypes.NETHER);
	
	//Nether2 Start
	public static final ItemDragonSword netherDragonSword2 = new ItemDragonSword(NetherDragonScaleMaterial2, "nether2_dragon_sword", EnumItemBreedTypes.NETHER);
	public static final ItemDragonPickAxe netherDragonPickaxe2 = new ItemDragonPickAxe(NetherDragonScaleMaterial2, "nether2_dragon_pickaxe", EnumItemBreedTypes.NETHER);
	public static final ItemDragonAxe netherDragonAxe2 = new ItemDragonAxe(NetherDragonScaleMaterial2, "nether2_dragon_axe", netherDamage, netherSpeed, EnumItemBreedTypes.NETHER);
	public static final ItemDragonShovel netherDragonShovel2 = new ItemDragonShovel(NetherDragonScaleMaterial2, "nether2_dragon_shovel", EnumItemBreedTypes.NETHER);
	public static final ItemDragonHoe netherDragonHoe2 = new ItemDragonHoe(NetherDragonScaleMaterial2, "nether2_dragon_hoe", EnumItemBreedTypes.NETHER);

	//Ender Start
    public static final ItemDragonSword enderDragonSword = new ItemDragonSword(EnderDragonScaleMaterial, "ender_dragon_sword", EnumItemBreedTypes.END);
    public static final ItemDragonPickAxe enderDragonPickaxe = new ItemDragonPickAxe(EnderDragonScaleMaterial, "ender_dragon_pickaxe", EnumItemBreedTypes.END);
	public static final ItemDragonAxe enderDragonAxe = new ItemDragonAxe(EnderDragonScaleMaterial, "ender_dragon_axe", enderDamage, enderSpeed, EnumItemBreedTypes.END);
	public static final ItemDragonShovel enderDragonShovel = new ItemDragonShovel(EnderDragonScaleMaterial, "ender_dragon_shovel", EnumItemBreedTypes.END);
	public static final ItemDragonHoe enderDragonHoe = new ItemDragonHoe(EnderDragonScaleMaterial, "ender_dragon_hoe", EnumItemBreedTypes.END);
	
	//Enchant Start
	public static final ItemDragonSword enchantDragonSword = new ItemDragonSword(EnchantDragonScaleMaterial, "enchant_dragon_sword", EnumItemBreedTypes.ENCHANT);
	public static final ItemDragonPickAxe enchantDragonPickaxe = new ItemDragonPickAxe(EnchantDragonScaleMaterial, "enchant_dragon_pickaxe", EnumItemBreedTypes.ENCHANT);
	public static final ItemDragonAxe enchantDragonAxe = new ItemDragonAxe(EnchantDragonScaleMaterial, "enchant_dragon_axe", damage, speed, EnumItemBreedTypes.ENCHANT);
	public static final ItemDragonShovel enchantDragonShovel = new ItemDragonShovel(EnchantDragonScaleMaterial, "enchant_dragon_shovel", EnumItemBreedTypes.ENCHANT);
	public static final ItemDragonHoe enchantDragonHoe = new ItemDragonHoe(EnchantDragonScaleMaterial, "enchant_dragon_hoe", EnumItemBreedTypes.ENCHANT);
	
	//Storm Start
	public static final ItemDragonSword stormDragonSword = new ItemDragonSword(StormDragonScaleMaterial, "storm_dragon_sword", EnumItemBreedTypes.STORM);
	public static final ItemDragonPickAxe stormDragonPickaxe = new ItemDragonPickAxe(StormDragonScaleMaterial, "storm_dragon_pickaxe", EnumItemBreedTypes.STORM);
	public static final ItemDragonAxe stormDragonAxe = new ItemDragonAxe(StormDragonScaleMaterial, "storm_dragon_axe", damage, speed, EnumItemBreedTypes.STORM);
	public static final ItemDragonShovel stormDragonShovel = new ItemDragonShovel(StormDragonScaleMaterial, "storm_dragon_shovel", EnumItemBreedTypes.STORM);
	public static final ItemDragonHoe stormDragonHoe = new ItemDragonHoe(StormDragonScaleMaterial, "storm_dragon_hoe", EnumItemBreedTypes.STORM);
	
	//Sunlight Start
	public static final ItemDragonSword sunlightDragonSword = new ItemDragonSword(SunlightDragonScaleMaterial, "sunlight_dragon_sword", EnumItemBreedTypes.SUNLIGHT);
	public static final ItemDragonPickAxe sunlightDragonPickaxe = new ItemDragonPickAxe(SunlightDragonScaleMaterial, "sunlight_dragon_pickaxe", EnumItemBreedTypes.SUNLIGHT);
	public static final ItemDragonAxe sunlightDragonAxe = new ItemDragonAxe(SunlightDragonScaleMaterial, "sunlight_dragon_axe", damage, speed, EnumItemBreedTypes.SUNLIGHT);
	public static final ItemDragonShovel sunlightDragonShovel = new ItemDragonShovel(SunlightDragonScaleMaterial, "sunlight_dragon_shovel", EnumItemBreedTypes.SUNLIGHT);
	public static final ItemDragonHoe sunlightDragonHoe = new ItemDragonHoe(SunlightDragonScaleMaterial, "sunlight_dragon_hoe", EnumItemBreedTypes.SUNLIGHT);

	//Storm2 Start
	public static final ItemDragonSword stormDragonSword2 = new ItemDragonSword(StormDragonScaleMaterial2, "storm2_dragon_sword", EnumItemBreedTypes.STORM);
	public static final ItemDragonPickAxe stormDragonPickaxe2 = new ItemDragonPickAxe(StormDragonScaleMaterial2, "storm2_dragon_pickaxe", EnumItemBreedTypes.STORM);
	public static final ItemDragonAxe stormDragonAxe2 = new ItemDragonAxe(StormDragonScaleMaterial2, "storm2_dragon_axe", damage, speed, EnumItemBreedTypes.STORM);
	public static final ItemDragonShovel stormDragonShovel2 = new ItemDragonShovel(StormDragonScaleMaterial2, "storm2_dragon_shovel", EnumItemBreedTypes.STORM);
	public static final ItemDragonHoe stormDragonHoe2 = new ItemDragonHoe(StormDragonScaleMaterial2, "storm2_dragon_hoe", EnumItemBreedTypes.STORM);
	
	//Sunlight2 Start
	public static final ItemDragonSword sunlightDragonSword2 = new ItemDragonSword(SunlightDragonScaleMaterial2, "sunlight2_dragon_sword", EnumItemBreedTypes.SUNLIGHT2);
	public static final ItemDragonPickAxe sunlightDragonPickaxe2 = new ItemDragonPickAxe(SunlightDragonScaleMaterial2, "sunlight2_dragon_pickaxe", EnumItemBreedTypes.SUNLIGHT2);
	public static final ItemDragonAxe sunlightDragonAxe2 = new ItemDragonAxe(SunlightDragonScaleMaterial2, "sunlight2_dragon_axe", damage, speed, EnumItemBreedTypes.SUNLIGHT2);
	public static final ItemDragonShovel sunlightDragonShovel2 = new ItemDragonShovel(SunlightDragonScaleMaterial2, "sunlight2_dragon_shovel", EnumItemBreedTypes.SUNLIGHT2);
	public static final ItemDragonHoe sunlightDragonHoe2 = new ItemDragonHoe(SunlightDragonScaleMaterial2, "sunlight2_dragon_hoe", EnumItemBreedTypes.SUNLIGHT2);
	
	//Zombie Start
	public static final ItemDragonSword zombieDragonSword = new ItemDragonSword(ZombieDragonScaleMaterial, "zombie_dragon_sword", EnumItemBreedTypes.ZOMBIE);
	public static final ItemDragonPickAxe zombieDragonPickaxe = new ItemDragonPickAxe(ZombieDragonScaleMaterial, "zombie_dragon_pickaxe", EnumItemBreedTypes.ZOMBIE);
	public static final ItemDragonAxe zombieDragonAxe = new ItemDragonAxe(ZombieDragonScaleMaterial, "zombie_dragon_axe", damage, speed, EnumItemBreedTypes.ZOMBIE);
	public static final ItemDragonShovel zombieDragonShovel = new ItemDragonShovel(ZombieDragonScaleMaterial, "zombie_dragon_shovel", EnumItemBreedTypes.ZOMBIE);
	public static final ItemDragonHoe zombieDragonHoe = new ItemDragonHoe(ZombieDragonScaleMaterial, "zombie_dragon_hoe", EnumItemBreedTypes.ZOMBIE);

	//Terra Start
	public static final ItemDragonSword terraDragonSword = new ItemDragonSword(TerraDragonScaleMaterial, "terra_dragon_sword", EnumItemBreedTypes.TERRA);
	public static final ItemDragonPickAxe terraDragonPickaxe = new ItemDragonPickAxe(TerraDragonScaleMaterial, "terra_dragon_pickaxe", EnumItemBreedTypes.TERRA);
	public static final ItemDragonAxe terraDragonAxe = new ItemDragonAxe(TerraDragonScaleMaterial, "terra_dragon_axe", damage, speed, EnumItemBreedTypes.TERRA);
	public static final ItemDragonShovel terraDragonShovel = new ItemDragonShovel(TerraDragonScaleMaterial, "terra_dragon_shovel", EnumItemBreedTypes.TERRA);
	public static final ItemDragonHoe terraDragonHoe = new ItemDragonHoe(TerraDragonScaleMaterial, "terra_dragon_hoe", EnumItemBreedTypes.TERRA);
	
	//Terra2 Start
	public static final ItemDragonSword terraDragonSword2 = new ItemDragonSword(TerraDragonScaleMaterial2, "terra2_dragon_sword", EnumItemBreedTypes.TERRA2);
	public static final ItemDragonPickAxe terraDragonPickaxe2 = new ItemDragonPickAxe(TerraDragonScaleMaterial2, "terra2_dragon_pickaxe", EnumItemBreedTypes.TERRA2);
	public static final ItemDragonAxe terraDragonAxe2 = new ItemDragonAxe(TerraDragonScaleMaterial2, "terra2_dragon_axe", damage, speed, EnumItemBreedTypes.TERRA2);
	public static final ItemDragonShovel terraDragonShovel2 = new ItemDragonShovel(TerraDragonScaleMaterial2, "terra2_dragon_shovel", EnumItemBreedTypes.TERRA2);
	public static final ItemDragonHoe terraDragonHoe2 = new ItemDragonHoe(TerraDragonScaleMaterial2, "terra2_dragon_hoe", EnumItemBreedTypes.TERRA2);

	//Moonlight Start
	public static final ItemDragonSword moonlightDragonSword = new ItemDragonSword(MoonlightDragonScaleMaterial, "moonlight_dragon_sword", EnumItemBreedTypes.MOONLIGHT);
	public static final ItemDragonPickAxe moonlightDragonPickaxe = new ItemDragonPickAxe(MoonlightDragonScaleMaterial, "moonlight_dragon_pickaxe", EnumItemBreedTypes.MOONLIGHT);
	public static final ItemDragonAxe moonlightDragonAxe = new ItemDragonAxe(MoonlightDragonScaleMaterial, "moonlight_dragon_axe", damage, speed, EnumItemBreedTypes.MOONLIGHT);
	public static final ItemDragonShovel moonlightDragonShovel = new ItemDragonShovel(MoonlightDragonScaleMaterial, "moonlight_dragon_shovel", EnumItemBreedTypes.MOONLIGHT);
	public static final ItemDragonHoe moonlightDragonHoe = new ItemDragonHoe(MoonlightDragonScaleMaterial, "moonlight_dragon_hoe", EnumItemBreedTypes.MOONLIGHT);
	
	//Shears Start
	public static final ItemDiamondShears diamond_shears = new ItemDiamondShears(ToolMaterial.DIAMOND, "diamond_shears");
	
	//Bows Start
    public static final Item aether_dragon_bow = new ItemDragonAetherBow(EnumItemBreedTypes.AETHER, ModItems.AetherDragonScales);
    public static final Item forest_dragon_bow = new ItemDragonForestBow(EnumItemBreedTypes.FOREST, ModItems.ForestDragonScales);
    public static final Item fire_dragon_bow = new ItemDragonFireBow(EnumItemBreedTypes.FIRE, ModItems.FireDragonScales);
    public static final Item fire2_dragon_bow = new ItemDragonFire2Bow(EnumItemBreedTypes.FIRE2, ModItems.FireDragonScales2);
    public static final Item ice_dragon_bow = new ItemDragonIceBow(EnumItemBreedTypes.ICE, ModItems.IceDragonScales);
    public static final Item water_dragon_bow = new ItemDragonWaterBow(EnumItemBreedTypes.WATER, ModItems.WaterDragonScales);
    public static final Item sunlight_dragon_bow = new ItemDragonSunlightBow(EnumItemBreedTypes.SUNLIGHT, ModItems.SunlightDragonScales);
    public static final Item sunlight2_dragon_bow = new ItemDragonSunlight2Bow(EnumItemBreedTypes.SUNLIGHT2, ModItems.SunlightDragonScales2);
    public static final Item enchant_dragon_bow = new ItemDragonEnchantBow(EnumItemBreedTypes.ENCHANT, ModItems.EnchantDragonScales);
    public static final Item storm_dragon_bow = new ItemDragonStormBow(EnumItemBreedTypes.STORM, ModItems.StormDragonScales);
    public static final Item storm_dragon_bow2 = new ItemDragonStorm2Bow(EnumItemBreedTypes.STORM2, ModItems.StormDragonScales2);
    public static final Item nether_dragon_bow = new ItemDragonNetherBow(EnumItemBreedTypes.NETHER, ModItems.NetherDragonScales);
    public static final Item nether2_dragon_bow = new ItemDragonNether2Bow(EnumItemBreedTypes.NETHER2, ModItems.NetherDragonScales2);
    public static final Item ender_dragon_bow = new ItemDragonEndBow(EnumItemBreedTypes.END, ModItems.EnderDragonScales);
    public static final Item terra_dragon_bow = new ItemDragonTerraBow(EnumItemBreedTypes.TERRA, ModItems.TerraDragonScales);
    public static final Item terra2_dragon_bow = new ItemDragonTerra2Bow(EnumItemBreedTypes.TERRA2, ModItems.TerraDragonScales2);
    public static final Item moonlight_dragon_bow = new ItemDragonMoonlightBow(EnumItemBreedTypes.MOONLIGHT, ModItems.MoonlightDragonScales);
//    public static final Item skelton_dragon_bow = new ItemDragonSkelBow(EnumItemBreedTypes.SKELETON, Items.BONE);
    public static final Item zombie_dragon_bow = new ItemDragonZombieBow(EnumItemBreedTypes.ZOMBIE, ModItems.ZombieDragonScales);
	
	//Material Repairability Start
	public static void InitializaRepairs() {
		IceDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.IceDragonScales));
		AetherDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.AetherDragonScales));
		ForestDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.ForestDragonScales));
		FireDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.FireDragonScales));
		FireDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.FireDragonScales2));
		WaterDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.WaterDragonScales));
		NetherDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.NetherDragonScales));
		NetherDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.NetherDragonScales2));
		EnderDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.EnderDragonScales));
		SunlightDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.SunlightDragonScales));
		SunlightDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.SunlightDragonScales2));
		StormDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.StormDragonScales));
		StormDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.StormDragonScales2));
		EnchantDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.EnchantDragonScales));
		ZombieDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.ZombieDragonScales));
		TerraDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.TerraDragonScales));
		TerraDragonScaleMaterial2.setRepairItem(new ItemStack(ModItems.TerraDragonScales2));
		MoonlightDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.MoonlightDragonScales));
	}
}