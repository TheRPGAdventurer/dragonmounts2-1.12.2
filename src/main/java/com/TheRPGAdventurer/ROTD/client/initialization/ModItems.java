package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.items.ItemCarriage;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonScales;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonShield;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonSpawner;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonWand;
import com.TheRPGAdventurer.ROTD.client.items.ItemStructureSpawner;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.item.Item;

public class ModItems {
	
	public static final Item ForestDragonScales;
	public static final Item FireDragonScales;
	public static final Item FireDragonScales2;
	public static final Item IceDragonScales;
	public static final Item WaterDragonScales;
	public static final Item AetherDragonScales;
	public static final Item NetherDragonScales;
	public static final Item NetherDragonScales2;
	public static final Item EnderDragonScales;
	public static final Item SunlightDragonScales;
	public static final Item SunlightDragonScales2;
	public static final Item EnchantDragonScales;
	public static final Item StormDragonScales;
	public static final Item StormDragonScales2;
	public static final Item ZombieDragonScales;
	
	public static final Item SpawnForest;
	public static final Item SpawnAether;
	public static final Item SpawnFire;
	public static final Item SpawnIce;
	public static final Item SpawnWater;
	public static final Item SpawnSkeleton;
	public static final Item SpawnWither;
	public static final Item SpawnEnd;
	public static final Item SpawnNether;
	public static final Item SpawnEnchant;
	public static final Item SpawnSunlight;
//	public static final Item SpawnStorm;
	public static final Item SpawnZombie;
	
	public static final Item structure_spawner;
	public static final Item dragon_wand;
	
	public static final Item carriage_oak;
	public static final Item carriage_acacia;
	public static final Item carriage_birch;
	public static final Item carriage_darkoak;
	public static final Item carriage_jungle;
	public static final Item carriage_spruce;	
	
	public static final Item aether_dragon_shield;
	public static final Item forest_dragon_shield;
	public static final Item fire_dragon_shield;
	public static final Item ice_dragon_shield;
	public static final Item water_dragon_shield;
	public static final Item sunlight_dragon_shield;
	public static final Item sunlight2_dragon_shield;
	public static final Item enchant_dragon_shield;
	public static final Item storm_dragon_shield;
	public static final Item nether_dragon_shield;
	public static final Item ender_dragon_shield;
	
	
	public static final Item[] ITEMS = {
		ForestDragonScales = new ItemDragonScales("forest_dragonscales", EnumItemBreedTypes.FOREST, 16),
		FireDragonScales = new ItemDragonScales("fire_dragonscales", EnumItemBreedTypes.FIRE, 16),
		FireDragonScales2 = new ItemDragonScales("fire2_dragonscales", EnumItemBreedTypes.FIRE, 16),
		IceDragonScales = new ItemDragonScales("ice_dragonscales", EnumItemBreedTypes.ICE, 16),
		WaterDragonScales = new ItemDragonScales("water_dragonscales", EnumItemBreedTypes.WATER, 16),
		AetherDragonScales = new ItemDragonScales("aether_dragonscales", EnumItemBreedTypes.AETHER, 16),
		NetherDragonScales = new ItemDragonScales("nether_dragonscales", EnumItemBreedTypes.NETHER, 16),
		NetherDragonScales2 = new ItemDragonScales("nether2_dragonscales", EnumItemBreedTypes.NETHER, 16),
		EnderDragonScales = new ItemDragonScales("ender_dragonscales", EnumItemBreedTypes.END, 16),
		SunlightDragonScales = new ItemDragonScales("sunlight_dragonscales", EnumItemBreedTypes.SUNLIGHT, 16),
		SunlightDragonScales2 = new ItemDragonScales("sunlight2_dragonscales", EnumItemBreedTypes.SUNLIGHT, 16),
		EnchantDragonScales = new ItemDragonScales("enchant_dragonscales", EnumItemBreedTypes.ENCHANT, 16),
		StormDragonScales = new ItemDragonScales("storm_dragonscales", EnumItemBreedTypes.STORM, 16),
		StormDragonScales2 = new ItemDragonScales("storm2_dragonscales", EnumItemBreedTypes.STORM, 16),
		ZombieDragonScales = new ItemDragonScales("zombie_dragonscales", EnumItemBreedTypes.ZOMBIE, 16),
		
		SpawnForest = new ItemDragonSpawner(EnumItemBreedTypes.FOREST, EnumDragonBreed.FOREST, DragonMounts.TAB),
		SpawnAether = new ItemDragonSpawner(EnumItemBreedTypes.AETHER, EnumDragonBreed.AETHER, DragonMounts.TAB),
		SpawnFire = new ItemDragonSpawner(EnumItemBreedTypes.FIRE, EnumDragonBreed.FIRE, DragonMounts.TAB),
		SpawnIce = new ItemDragonSpawner(EnumItemBreedTypes.ICE, EnumDragonBreed.ICE, DragonMounts.TAB),
		SpawnWater = new ItemDragonSpawner(EnumItemBreedTypes.WATER, EnumDragonBreed.SYLPHID, DragonMounts.TAB),
		SpawnSkeleton = new ItemDragonSpawner(EnumItemBreedTypes.SKELETON, EnumDragonBreed.SKELETON, DragonMounts.TAB),
		SpawnWither = new ItemDragonSpawner(EnumItemBreedTypes.WITHER, EnumDragonBreed.WITHER, DragonMounts.TAB),
		SpawnEnd = new ItemDragonSpawner(EnumItemBreedTypes.END, EnumDragonBreed.END, DragonMounts.TAB),
		SpawnNether = new ItemDragonSpawner(EnumItemBreedTypes.NETHER, EnumDragonBreed.NETHER, DragonMounts.TAB),
		SpawnEnchant = new ItemDragonSpawner(EnumItemBreedTypes.ENCHANT, EnumDragonBreed.ENCHANT, DragonMounts.TAB),
		SpawnSunlight = new ItemDragonSpawner(EnumItemBreedTypes.SUNLIGHT, EnumDragonBreed.SUNLIGHT, DragonMounts.TAB),
	//	SpawnStorm = new ItemDragonSpawner(EnumItemBreedTypes.STORM, EnumDragonBreed.STORM, DragonMounts.TAB),
		SpawnZombie = new ItemDragonSpawner(EnumItemBreedTypes.ZOMBIE, EnumDragonBreed.ZOMBIE, DragonMounts.TAB),
		
		structure_spawner = new ItemStructureSpawner("structure_spawner"),
		dragon_wand = new ItemDragonWand("dragon_wand"),
		
		carriage_oak   = new ItemCarriage("carriage_", EntityCarriage.Type.OAK),
		carriage_spruce = new ItemCarriage("carriage_", EntityCarriage.Type.SPRUCE),
		carriage_birch = new ItemCarriage("carriage_", EntityCarriage.Type.BIRCH),
		carriage_jungle = new ItemCarriage("carriage_", EntityCarriage.Type.JUNGLE),
		carriage_acacia = new ItemCarriage("carriage_", EntityCarriage.Type.ACACIA),
		carriage_darkoak = new ItemCarriage("carriage_", EntityCarriage.Type.DARK_OAK),
		
		aether_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.AETHER),
		forest_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.FOREST),
		ice_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.ICE),
		fire_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.FIRE),
		water_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.WATER),
		enchant_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.ENCHANT),
		sunlight_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.SUNLIGHT),
		sunlight2_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.SUNLIGHT2),
		storm_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.STORM),
		nether_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.NETHER),
		ender_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.END),
		
	};
}
