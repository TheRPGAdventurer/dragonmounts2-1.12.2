package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.items.ItemCarriage;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonEssence;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonScales;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonShield;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonSpawner;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonWand;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonWhistle;
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
	public static final Item TerraDragonScales;
	public static final Item TerraDragonScales2;
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
	public static final Item SpawnStorm;
	public static final Item SpawnZombie;
	public static final Item SpawnTerra;
	
	public static final Item EssenceForest;
	public static final Item EssenceAether;
	public static final Item EssenceFire;
	public static final Item EssenceIce;
	public static final Item EssenceWater;
	public static final Item EssenceSkeleton;
	public static final Item EssenceWither;
	public static final Item EssenceEnd;
	public static final Item EssenceNether;
	public static final Item EssenceEnchant;
	public static final Item EssenceSunlight;
	public static final Item EssenceStorm;
	public static final Item EssenceZombie;
	public static final Item EssenceTerra;
	
	public static final Item structure_spawner;
	public static final Item dragon_wand;
	public static final Item dragon_whistle;
	
	public static final Item carriage_oak;
	public static final Item carriage_acacia;
	public static final Item carriage_birch;
	public static final Item carriage_darkoak;
	public static final Item carriage_jungle;
	public static final Item carriage_spruce;	
	
	public static final Item aether_dragon_shield;
	public static final Item forest_dragon_shield;
	public static final Item fire_dragon_shield;
	public static final Item fire2_dragon_shield;
	public static final Item ice_dragon_shield;
	public static final Item water_dragon_shield;
	public static final Item sunlight_dragon_shield;
	public static final Item sunlight2_dragon_shield;
	public static final Item enchant_dragon_shield;
	public static final Item storm_dragon_shield;
	public static final Item nether_dragon_shield;
	public static final Item ender_dragon_shield;
	public static final Item terra_dragon_shield;
	public static final Item terra2_dragon_shield;
	
	
	public static final Item[] ITEMS = {
		ForestDragonScales = new ItemDragonScales(EnumItemBreedTypes.FOREST),
		FireDragonScales = new ItemDragonScales(EnumItemBreedTypes.FIRE),
		FireDragonScales2 = new ItemDragonScales(EnumItemBreedTypes.FIRE),
		IceDragonScales = new ItemDragonScales(EnumItemBreedTypes.ICE),
		WaterDragonScales = new ItemDragonScales(EnumItemBreedTypes.WATER),
		AetherDragonScales = new ItemDragonScales(EnumItemBreedTypes.AETHER),
		NetherDragonScales = new ItemDragonScales(EnumItemBreedTypes.NETHER),
		NetherDragonScales2 = new ItemDragonScales(EnumItemBreedTypes.NETHER),
		EnderDragonScales = new ItemDragonScales(EnumItemBreedTypes.ENDER),
		SunlightDragonScales = new ItemDragonScales(EnumItemBreedTypes.SUNLIGHT),
		SunlightDragonScales2 = new ItemDragonScales(EnumItemBreedTypes.SUNLIGHT),
		EnchantDragonScales = new ItemDragonScales(EnumItemBreedTypes.ENCHANT),
		StormDragonScales = new ItemDragonScales(EnumItemBreedTypes.STORM),
		StormDragonScales2 = new ItemDragonScales(EnumItemBreedTypes.STORM),
		TerraDragonScales = new ItemDragonScales(EnumItemBreedTypes.TERRA),
		TerraDragonScales2 = new ItemDragonScales(EnumItemBreedTypes.TERRA2),
		ZombieDragonScales = new ItemDragonScales(EnumItemBreedTypes.ZOMBIE),
		
		EssenceForest = new ItemDragonEssence(EnumItemBreedTypes.FOREST),
		EssenceFire = new ItemDragonEssence(EnumItemBreedTypes.FIRE),
		EssenceIce = new ItemDragonEssence(EnumItemBreedTypes.ICE),
		EssenceWater = new ItemDragonEssence(EnumItemBreedTypes.WATER),
		EssenceAether = new ItemDragonEssence(EnumItemBreedTypes.AETHER),
		EssenceNether = new ItemDragonEssence(EnumItemBreedTypes.NETHER),
		EssenceEnd = new ItemDragonEssence(EnumItemBreedTypes.ENDER),
	 EssenceSunlight = new ItemDragonEssence(EnumItemBreedTypes.SUNLIGHT),
		EssenceEnchant = new ItemDragonEssence(EnumItemBreedTypes.ENCHANT),
		EssenceStorm = new ItemDragonEssence(EnumItemBreedTypes.STORM),
		EssenceTerra = new ItemDragonEssence(EnumItemBreedTypes.TERRA),
		EssenceZombie = new ItemDragonEssence(EnumItemBreedTypes.ZOMBIE),
		EssenceSkeleton = new ItemDragonEssence(EnumItemBreedTypes.SKELETON),
		EssenceWither = new ItemDragonEssence(EnumItemBreedTypes.WITHER),
		
		SpawnForest = new ItemDragonSpawner(EnumItemBreedTypes.FOREST, EnumDragonBreed.FOREST, DragonMounts.TAB),
		SpawnAether = new ItemDragonSpawner(EnumItemBreedTypes.AETHER, EnumDragonBreed.AETHER, DragonMounts.TAB),
		SpawnFire = new ItemDragonSpawner(EnumItemBreedTypes.FIRE, EnumDragonBreed.FIRE, DragonMounts.TAB),
		SpawnIce = new ItemDragonSpawner(EnumItemBreedTypes.ICE, EnumDragonBreed.ICE, DragonMounts.TAB),
		SpawnWater = new ItemDragonSpawner(EnumItemBreedTypes.WATER, EnumDragonBreed.SYLPHID, DragonMounts.TAB),
		SpawnSkeleton = new ItemDragonSpawner(EnumItemBreedTypes.SKELETON, EnumDragonBreed.SKELETON, DragonMounts.TAB),
		SpawnWither = new ItemDragonSpawner(EnumItemBreedTypes.WITHER, EnumDragonBreed.WITHER, DragonMounts.TAB),
		SpawnEnd = new ItemDragonSpawner(EnumItemBreedTypes.ENDER, EnumDragonBreed.END, DragonMounts.TAB),
		SpawnNether = new ItemDragonSpawner(EnumItemBreedTypes.NETHER, EnumDragonBreed.NETHER, DragonMounts.TAB),
		SpawnEnchant = new ItemDragonSpawner(EnumItemBreedTypes.ENCHANT, EnumDragonBreed.ENCHANT, DragonMounts.TAB),
		SpawnSunlight = new ItemDragonSpawner(EnumItemBreedTypes.SUNLIGHT, EnumDragonBreed.SUNLIGHT, DragonMounts.TAB),
		SpawnStorm = new ItemDragonSpawner(EnumItemBreedTypes.STORM, EnumDragonBreed.STORM, DragonMounts.TAB),
		SpawnZombie = new ItemDragonSpawner(EnumItemBreedTypes.ZOMBIE, EnumDragonBreed.ZOMBIE, DragonMounts.TAB),
		SpawnTerra = new ItemDragonSpawner(EnumItemBreedTypes.TERRA, EnumDragonBreed.TERRA, DragonMounts.TAB),

		structure_spawner = new ItemStructureSpawner("structure_spawner"),
		dragon_wand = new ItemDragonWand("dragon_wand"),
		dragon_whistle = new ItemDragonWhistle(),
		
		carriage_oak   = new ItemCarriage(EntityCarriage.Type.OAK),
		carriage_spruce = new ItemCarriage(EntityCarriage.Type.SPRUCE),
		carriage_birch = new ItemCarriage(EntityCarriage.Type.BIRCH),
		carriage_jungle = new ItemCarriage(EntityCarriage.Type.JUNGLE),
		carriage_acacia = new ItemCarriage(EntityCarriage.Type.ACACIA),
		carriage_darkoak = new ItemCarriage(EntityCarriage.Type.DARK_OAK),
		
		aether_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.AETHER, ModItems.AetherDragonScales),
		forest_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.FOREST, ModItems.ForestDragonScales),
		ice_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.ICE, ModItems.IceDragonScales),
		fire_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.FIRE, ModItems.FireDragonScales),
		fire2_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.FIRE2, ModItems.FireDragonScales2),
		water_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.WATER, ModItems.WaterDragonScales),
		enchant_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.ENCHANT, ModItems.EnchantDragonScales),
		sunlight_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.SUNLIGHT, ModItems.SunlightDragonScales),
		sunlight2_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.SUNLIGHT2, ModItems.SunlightDragonScales2),
		storm_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.STORM, ModItems.StormDragonScales),
		nether_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.NETHER, ModItems.NetherDragonScales),
		ender_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.END, ModItems.EnderDragonScales),
		terra_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.TERRA, ModItems.TerraDragonScales),
		terra2_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.TERRA2, ModItems.TerraDragonScales2)
		
	};
}
