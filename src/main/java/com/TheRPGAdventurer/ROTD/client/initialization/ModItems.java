package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.items.ItemCarriage;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonScales;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonSpawner;
import com.TheRPGAdventurer.ROTD.client.items.ItemStructureSpawner;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.item.Item;

public class ModItems {
	
	public static final Item ForestDragonScales;
	public static final Item FireDragonScales;
	public static final Item IceDragonScales;
	public static final Item WaterDragonScales;
	public static final Item AetherDragonScales;
	public static final Item NetherDragonScales;
	public static final Item EnderDragonScales;
	
	public static final Item SpawnForest;
	public static final Item SpawnFire;
	public static final Item SpawnIce;
	public static final Item SpawnWater;
	public static final Item SpawnSkeleton;
	public static final Item SpawnWither;
	public static final Item SpawnEnd;
	public static final Item SpawnNether;
	
	public static final Item structure_spawner;
	
	public static final Item carriage_oak;
	public static final Item carriage_acacia;
	public static final Item carriage_birch;
	public static final Item carriage_darkoak;
	public static final Item carriage_jungle;
	public static final Item carriage_spruce;
	
	
	public static final Item[] ITEMS = {
		ForestDragonScales = new ItemDragonScales("forest_dragonscales", EnumItemBreedTypes.FOREST, 16),
		FireDragonScales = new ItemDragonScales("fire_dragonscales", EnumItemBreedTypes.FIRE, 16),
		IceDragonScales = new ItemDragonScales("ice_dragonscales", EnumItemBreedTypes.ICE, 16),
		WaterDragonScales = new ItemDragonScales("water_dragonscales", EnumItemBreedTypes.WATER, 16),
		AetherDragonScales = new ItemDragonScales("aether_dragonscales", EnumItemBreedTypes.AETHER, 16),
		NetherDragonScales = new ItemDragonScales("nether_dragonscales", EnumItemBreedTypes.NETHER, 16),
		EnderDragonScales = new ItemDragonScales("ender_dragonscales", EnumItemBreedTypes.END, 16),
		
		SpawnForest = new ItemDragonSpawner(EnumItemBreedTypes.FOREST, EnumDragonBreed.FOREST, DragonMounts.TAB),
		SpawnFire = new ItemDragonSpawner(EnumItemBreedTypes.FIRE, EnumDragonBreed.FIRE, DragonMounts.TAB),
		SpawnIce = new ItemDragonSpawner(EnumItemBreedTypes.ICE, EnumDragonBreed.ICE, DragonMounts.TAB),
		SpawnWater = new ItemDragonSpawner(EnumItemBreedTypes.WATER, EnumDragonBreed.SYLPHID, DragonMounts.TAB),
		SpawnSkeleton = new ItemDragonSpawner(EnumItemBreedTypes.SKELETON, EnumDragonBreed.SKELETON, DragonMounts.TAB),
		SpawnWither = new ItemDragonSpawner(EnumItemBreedTypes.WITHER, EnumDragonBreed.WITHER, DragonMounts.TAB),
		SpawnEnd = new ItemDragonSpawner(EnumItemBreedTypes.END, EnumDragonBreed.END, DragonMounts.TAB),
		SpawnNether = new ItemDragonSpawner(EnumItemBreedTypes.NETHER, EnumDragonBreed.NETHER, DragonMounts.TAB),
		
		structure_spawner = new ItemStructureSpawner("structure_spawner"),
		
		carriage_oak   = new ItemCarriage("carriage_", EntityCarriage.Type.OAK),
		carriage_spruce = new ItemCarriage("carriage_", EntityCarriage.Type.SPRUCE),
		carriage_birch = new ItemCarriage("carriage_", EntityCarriage.Type.BIRCH),
		carriage_jungle = new ItemCarriage("carriage_", EntityCarriage.Type.JUNGLE),
		carriage_acacia = new ItemCarriage("carriage_", EntityCarriage.Type.ACACIA),
		carriage_darkoak = new ItemCarriage("carriage_", EntityCarriage.Type.DARK_OAK),
	};
}
