package com.TheRPGAdventurer.ROTD.server.initialization;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.items.*;
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
    public static final Item MoonlightDragonScales;

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
    public static final Item SpawnMoonlight;

    public static final ItemDragonEssence EssenceForest;
    public static final ItemDragonEssence EssenceAether;
    public static final ItemDragonEssence EssenceFire;
    public static final ItemDragonEssence EssenceIce;
    public static final ItemDragonEssence EssenceWater;
    public static final ItemDragonEssence EssenceSkeleton;
    public static final ItemDragonEssence EssenceWither;
    public static final ItemDragonEssence EssenceEnd;
    public static final ItemDragonEssence EssenceNether;
    public static final ItemDragonEssence EssenceEnchant;
    public static final ItemDragonEssence EssenceSunlight;
    public static final ItemDragonEssence EssenceStorm;
    public static final ItemDragonEssence EssenceZombie;
    public static final ItemDragonEssence EssenceTerra;
    public static final ItemDragonEssence EssenceMoonlight;

    public static final ItemDragonAmulet AmuletForest;
    public static final ItemDragonAmulet AmuletAether;
    public static final ItemDragonAmulet AmuletFire;
    public static final ItemDragonAmulet AmuletIce;
    public static final ItemDragonAmulet AmuletWater;
    public static final ItemDragonAmulet AmuletSkeleton;
    public static final ItemDragonAmulet AmuletWither;
    public static final ItemDragonAmulet AmuletEnd;
    public static final ItemDragonAmulet AmuletNether;
    public static final ItemDragonAmulet AmuletEnchant;
    public static final ItemDragonAmulet AmuletSunlight;
    public static final ItemDragonAmulet AmuletStorm;
    public static final ItemDragonAmulet AmuletZombie;
    public static final ItemDragonAmulet AmuletTerra;
    public static final ItemDragonAmulet AmuletMoonlight;
    public static final ItemDragonAmulet AmuletEmpty;

    public static final Item structure_spawner;
    public static final Item dragon_wand;
    public static final Item dragon_whistle;
    public static final Item gender;

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
    public static final Item moonlight_dragon_shield;


    public static final Item[] ITEMS = {
            ForestDragonScales = new ItemDragonScales("forest_dragonscales", EnumItemBreedTypes.FOREST),
            FireDragonScales = new ItemDragonScales("fire_dragonscales", EnumItemBreedTypes.FIRE),
            FireDragonScales2 = new ItemDragonScales("fire2_dragonscales", EnumItemBreedTypes.FIRE),
            IceDragonScales = new ItemDragonScales("ice_dragonscales", EnumItemBreedTypes.ICE),
            WaterDragonScales = new ItemDragonScales("water_dragonscales", EnumItemBreedTypes.WATER),
            AetherDragonScales = new ItemDragonScales("aether_dragonscales", EnumItemBreedTypes.AETHER),
            NetherDragonScales = new ItemDragonScales("nether_dragonscales", EnumItemBreedTypes.NETHER),
            NetherDragonScales2 = new ItemDragonScales("nether2_dragonscales", EnumItemBreedTypes.NETHER),
            EnderDragonScales = new ItemDragonScales("ender_dragonscales", EnumItemBreedTypes.END),
            SunlightDragonScales = new ItemDragonScales("sunlight_dragonscales", EnumItemBreedTypes.SUNLIGHT),
            SunlightDragonScales2 = new ItemDragonScales("sunlight2_dragonscales", EnumItemBreedTypes.SUNLIGHT),
            EnchantDragonScales = new ItemDragonScales("enchant_dragonscales", EnumItemBreedTypes.ENCHANT),
            StormDragonScales = new ItemDragonScales("storm_dragonscales", EnumItemBreedTypes.STORM),
            StormDragonScales2 = new ItemDragonScales("storm2_dragonscales", EnumItemBreedTypes.STORM),
            TerraDragonScales = new ItemDragonScales("terra_dragonscales", EnumItemBreedTypes.TERRA),
            TerraDragonScales2 = new ItemDragonScales("terra2_dragonscales", EnumItemBreedTypes.TERRA2),
            ZombieDragonScales = new ItemDragonScales("zombie_dragonscales", EnumItemBreedTypes.ZOMBIE),
            MoonlightDragonScales = new ItemDragonScales("moonlight_dragonscales", EnumItemBreedTypes.MOONLIGHT),

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
            SpawnStorm = new ItemDragonSpawner(EnumItemBreedTypes.STORM, EnumDragonBreed.STORM, DragonMounts.TAB),
            SpawnZombie = new ItemDragonSpawner(EnumItemBreedTypes.ZOMBIE, EnumDragonBreed.ZOMBIE, DragonMounts.TAB),
            SpawnTerra = new ItemDragonSpawner(EnumItemBreedTypes.TERRA, EnumDragonBreed.TERRA, DragonMounts.TAB),
            SpawnMoonlight = new ItemDragonSpawner(EnumItemBreedTypes.MOONLIGHT, EnumDragonBreed.MOONLIGHT, DragonMounts.TAB),

            EssenceForest = new ItemDragonEssence(EnumItemBreedTypes.FOREST, EnumDragonBreed.FOREST),
            EssenceFire = new ItemDragonEssence(EnumItemBreedTypes.FIRE, EnumDragonBreed.FIRE),
            EssenceIce = new ItemDragonEssence(EnumItemBreedTypes.ICE, EnumDragonBreed.ICE),
            EssenceWater = new ItemDragonEssence(EnumItemBreedTypes.WATER, EnumDragonBreed.SYLPHID),
            EssenceAether = new ItemDragonEssence(EnumItemBreedTypes.AETHER, EnumDragonBreed.AETHER),
            EssenceNether = new ItemDragonEssence(EnumItemBreedTypes.NETHER, EnumDragonBreed.NETHER),
            EssenceEnd = new ItemDragonEssence(EnumItemBreedTypes.END, EnumDragonBreed.END),
            EssenceSunlight = new ItemDragonEssence(EnumItemBreedTypes.SUNLIGHT, EnumDragonBreed.SUNLIGHT),
            EssenceEnchant = new ItemDragonEssence(EnumItemBreedTypes.ENCHANT, EnumDragonBreed.ENCHANT),
            EssenceStorm = new ItemDragonEssence(EnumItemBreedTypes.STORM, EnumDragonBreed.STORM),
            EssenceTerra = new ItemDragonEssence(EnumItemBreedTypes.TERRA, EnumDragonBreed.TERRA),
            EssenceZombie = new ItemDragonEssence(EnumItemBreedTypes.ZOMBIE, EnumDragonBreed.ZOMBIE),
            EssenceSkeleton = new ItemDragonEssence(EnumItemBreedTypes.SKELETON, EnumDragonBreed.SKELETON),
            EssenceWither = new ItemDragonEssence(EnumItemBreedTypes.WITHER, EnumDragonBreed.WITHER),
            EssenceMoonlight = new ItemDragonEssence(EnumItemBreedTypes.MOONLIGHT, EnumDragonBreed.MOONLIGHT),

            AmuletForest = new ItemDragonAmulet(EnumItemBreedTypes.FOREST, EnumDragonBreed.FOREST),
            AmuletFire = new ItemDragonAmulet(EnumItemBreedTypes.FIRE, EnumDragonBreed.FIRE),
            AmuletIce = new ItemDragonAmulet(EnumItemBreedTypes.ICE, EnumDragonBreed.ICE),
            AmuletWater = new ItemDragonAmulet(EnumItemBreedTypes.WATER, EnumDragonBreed.SYLPHID),
            AmuletAether = new ItemDragonAmulet(EnumItemBreedTypes.AETHER, EnumDragonBreed.AETHER),
            AmuletNether = new ItemDragonAmulet(EnumItemBreedTypes.NETHER, EnumDragonBreed.NETHER),
            AmuletEnd = new ItemDragonAmulet(EnumItemBreedTypes.END, EnumDragonBreed.END),
            AmuletSunlight = new ItemDragonAmulet(EnumItemBreedTypes.SUNLIGHT, EnumDragonBreed.SUNLIGHT),
            AmuletEnchant = new ItemDragonAmulet(EnumItemBreedTypes.ENCHANT, EnumDragonBreed.ENCHANT),
            AmuletStorm = new ItemDragonAmulet(EnumItemBreedTypes.STORM, EnumDragonBreed.STORM),
            AmuletTerra = new ItemDragonAmulet(EnumItemBreedTypes.TERRA, EnumDragonBreed.TERRA),
            AmuletZombie = new ItemDragonAmulet(EnumItemBreedTypes.ZOMBIE, EnumDragonBreed.ZOMBIE),
            AmuletSkeleton = new ItemDragonAmulet(EnumItemBreedTypes.SKELETON, EnumDragonBreed.SKELETON),
            AmuletWither = new ItemDragonAmulet(EnumItemBreedTypes.WITHER, EnumDragonBreed.WITHER),
            AmuletMoonlight = new ItemDragonAmulet(EnumItemBreedTypes.MOONLIGHT, EnumDragonBreed.MOONLIGHT),
            AmuletEmpty = new ItemDragonAmulet(null, null),

            structure_spawner = new ItemStructureSpawner("structure_spawner"),
            dragon_wand = new ItemDragonWand("dragon_wand"),
            dragon_whistle = new ItemDragonWhistle(),
            gender = new ItemDragonGender("dragon_gender"),

            carriage_oak = new ItemCarriage("carriage_", EntityCarriage.Type.OAK),
            carriage_spruce = new ItemCarriage("carriage_", EntityCarriage.Type.SPRUCE),
            carriage_birch = new ItemCarriage("carriage_", EntityCarriage.Type.BIRCH),
            carriage_jungle = new ItemCarriage("carriage_", EntityCarriage.Type.JUNGLE),
            carriage_acacia = new ItemCarriage("carriage_", EntityCarriage.Type.ACACIA),
            carriage_darkoak = new ItemCarriage("carriage_", EntityCarriage.Type.DARK_OAK),

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
            terra2_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.TERRA2, ModItems.TerraDragonScales2),
            moonlight_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.MOONLIGHT, ModItems.MoonlightDragonScales)

    };
}
