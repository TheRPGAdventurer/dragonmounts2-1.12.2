package com.TheRPGAdventurer.ROTD.server.initialization;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.items.*;
import com.TheRPGAdventurer.ROTD.server.items.bow.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    //Scales Start
    public static final Item ForestDragonScales = new ItemDragonScales("forest_dragonscales", EnumItemBreedTypes.FOREST);
    public static final Item FireDragonScales = new ItemDragonScales("fire_dragonscales", EnumItemBreedTypes.FIRE);
    public static final Item FireDragonScales2 = new ItemDragonScales("fire2_dragonscales", EnumItemBreedTypes.FIRE);
    public static final Item IceDragonScales = new ItemDragonScales("ice_dragonscales", EnumItemBreedTypes.ICE);
    public static final Item WaterDragonScales = new ItemDragonScales("water_dragonscales", EnumItemBreedTypes.WATER);
    public static final Item AetherDragonScales = new ItemDragonScales("aether_dragonscales", EnumItemBreedTypes.AETHER);
    public static final Item NetherDragonScales = new ItemDragonScales("nether_dragonscales", EnumItemBreedTypes.NETHER);
    public static final Item NetherDragonScales2 = new ItemDragonScales("nether2_dragonscales", EnumItemBreedTypes.NETHER);
    public static final Item EnderDragonScales = new ItemDragonScales("ender_dragonscales", EnumItemBreedTypes.END);
    public static final Item SunlightDragonScales = new ItemDragonScales("sunlight_dragonscales", EnumItemBreedTypes.SUNLIGHT);
    public static final Item SunlightDragonScales2 = new ItemDragonScales("sunlight2_dragonscales", EnumItemBreedTypes.SUNLIGHT);
    public static final Item EnchantDragonScales = new ItemDragonScales("enchant_dragonscales", EnumItemBreedTypes.ENCHANT);
    public static final Item StormDragonScales = new ItemDragonScales("storm_dragonscales", EnumItemBreedTypes.STORM);
    public static final Item StormDragonScales2 = new ItemDragonScales("storm2_dragonscales", EnumItemBreedTypes.STORM);
    public static final Item TerraDragonScales = new ItemDragonScales("terra_dragonscales", EnumItemBreedTypes.TERRA);
    public static final Item TerraDragonScales2 = new ItemDragonScales("terra2_dragonscales", EnumItemBreedTypes.TERRA2);
    public static final Item ZombieDragonScales = new ItemDragonScales("zombie_dragonscales", EnumItemBreedTypes.ZOMBIE);
    public static final Item MoonlightDragonScales = new ItemDragonScales("moonlight_dragonscales", EnumItemBreedTypes.MOONLIGHT);
    //Scales end

    //Spawn eggs start
    public static final Item SpawnForest = new ItemDragonSpawner(EnumItemBreedTypes.FOREST, EnumDragonBreed.FOREST, DragonMounts.mainTab);
    public static final Item SpawnAether = new ItemDragonSpawner(EnumItemBreedTypes.AETHER, EnumDragonBreed.AETHER, DragonMounts.mainTab);
    public static final Item SpawnFire = new ItemDragonSpawner(EnumItemBreedTypes.FIRE, EnumDragonBreed.FIRE, DragonMounts.mainTab);
    public static final Item SpawnIce = new ItemDragonSpawner(EnumItemBreedTypes.ICE, EnumDragonBreed.ICE, DragonMounts.mainTab);
    public static final Item SpawnWater = new ItemDragonSpawner(EnumItemBreedTypes.WATER, EnumDragonBreed.SYLPHID, DragonMounts.mainTab);
    public static final Item SpawnSkeleton = new ItemDragonSpawner(EnumItemBreedTypes.SKELETON, EnumDragonBreed.SKELETON, DragonMounts.mainTab);
    public static final Item SpawnWither = new ItemDragonSpawner(EnumItemBreedTypes.WITHER, EnumDragonBreed.WITHER, DragonMounts.mainTab);
    public static final Item SpawnEnd = new ItemDragonSpawner(EnumItemBreedTypes.END, EnumDragonBreed.END, DragonMounts.mainTab);
    public static final Item SpawnNether = new ItemDragonSpawner(EnumItemBreedTypes.NETHER, EnumDragonBreed.NETHER, DragonMounts.mainTab);
    public static final Item SpawnEnchant = new ItemDragonSpawner(EnumItemBreedTypes.ENCHANT, EnumDragonBreed.ENCHANT, DragonMounts.mainTab);
    public static final Item SpawnSunlight = new ItemDragonSpawner(EnumItemBreedTypes.SUNLIGHT, EnumDragonBreed.SUNLIGHT, DragonMounts.mainTab);
    public static final Item SpawnStorm = new ItemDragonSpawner(EnumItemBreedTypes.STORM, EnumDragonBreed.STORM, DragonMounts.mainTab);
    public static final Item SpawnZombie = new ItemDragonSpawner(EnumItemBreedTypes.ZOMBIE, EnumDragonBreed.ZOMBIE, DragonMounts.mainTab);
    public static final Item SpawnTerra = new ItemDragonSpawner(EnumItemBreedTypes.TERRA, EnumDragonBreed.TERRA, DragonMounts.mainTab);
    public static final Item SpawnMoonlight = new ItemDragonSpawner(EnumItemBreedTypes.MOONLIGHT, EnumDragonBreed.MOONLIGHT, DragonMounts.mainTab);
    //Spawn eggs end

    //Essence Start
    public static final ItemDragonEssence EssenceForest = new ItemDragonEssence(EnumItemBreedTypes.FOREST, EnumDragonBreed.FOREST);
    public static final ItemDragonEssence EssenceAether = new ItemDragonEssence(EnumItemBreedTypes.AETHER, EnumDragonBreed.AETHER);
    public static final ItemDragonEssence EssenceFire = new ItemDragonEssence(EnumItemBreedTypes.FIRE, EnumDragonBreed.FIRE);
    public static final ItemDragonEssence EssenceIce = new ItemDragonEssence(EnumItemBreedTypes.ICE, EnumDragonBreed.ICE);
    public static final ItemDragonEssence EssenceWater = new ItemDragonEssence(EnumItemBreedTypes.WATER, EnumDragonBreed.SYLPHID);
    public static final ItemDragonEssence EssenceSkeleton = new ItemDragonEssence(EnumItemBreedTypes.SKELETON, EnumDragonBreed.SKELETON);
    public static final ItemDragonEssence EssenceWither = new ItemDragonEssence(EnumItemBreedTypes.WITHER, EnumDragonBreed.WITHER);
    public static final ItemDragonEssence EssenceEnd = new ItemDragonEssence(EnumItemBreedTypes.END, EnumDragonBreed.END);
    public static final ItemDragonEssence EssenceNether = new ItemDragonEssence(EnumItemBreedTypes.NETHER, EnumDragonBreed.NETHER);
    public static final ItemDragonEssence EssenceEnchant = new ItemDragonEssence(EnumItemBreedTypes.ENCHANT, EnumDragonBreed.ENCHANT);
    public static final ItemDragonEssence EssenceSunlight = new ItemDragonEssence(EnumItemBreedTypes.SUNLIGHT, EnumDragonBreed.SUNLIGHT);
    public static final ItemDragonEssence EssenceStorm = new ItemDragonEssence(EnumItemBreedTypes.STORM, EnumDragonBreed.STORM);
    public static final ItemDragonEssence EssenceZombie = new ItemDragonEssence(EnumItemBreedTypes.ZOMBIE, EnumDragonBreed.ZOMBIE);
    public static final ItemDragonEssence EssenceTerra = new ItemDragonEssence(EnumItemBreedTypes.TERRA, EnumDragonBreed.TERRA);
    public static final ItemDragonEssence EssenceMoonlight = new ItemDragonEssence(EnumItemBreedTypes.MOONLIGHT, EnumDragonBreed.MOONLIGHT);
    //Essence End

    //Amulet Start
    public static final ItemDragonAmulet AmuletForest = new ItemDragonAmulet(EnumItemBreedTypes.FOREST, EnumDragonBreed.FOREST);
    public static final ItemDragonAmulet AmuletAether = new ItemDragonAmulet(EnumItemBreedTypes.AETHER, EnumDragonBreed.AETHER);
    public static final ItemDragonAmulet AmuletFire = new ItemDragonAmulet(EnumItemBreedTypes.FIRE, EnumDragonBreed.FIRE);
    public static final ItemDragonAmulet AmuletIce = new ItemDragonAmulet(EnumItemBreedTypes.ICE, EnumDragonBreed.ICE);
    public static final ItemDragonAmulet AmuletWater = new ItemDragonAmulet(EnumItemBreedTypes.WATER, EnumDragonBreed.SYLPHID);
    public static final ItemDragonAmulet AmuletSkeleton = new ItemDragonAmulet(EnumItemBreedTypes.SKELETON, EnumDragonBreed.SKELETON);
    public static final ItemDragonAmulet AmuletWither = new ItemDragonAmulet(EnumItemBreedTypes.WITHER, EnumDragonBreed.WITHER);
    public static final ItemDragonAmulet AmuletEnd = new ItemDragonAmulet(EnumItemBreedTypes.END, EnumDragonBreed.END);
    public static final ItemDragonAmulet AmuletNether = new ItemDragonAmulet(EnumItemBreedTypes.NETHER, EnumDragonBreed.NETHER);
    public static final ItemDragonAmulet AmuletEnchant = new ItemDragonAmulet(EnumItemBreedTypes.ENCHANT, EnumDragonBreed.ENCHANT);
    public static final ItemDragonAmulet AmuletSunlight = new ItemDragonAmulet(EnumItemBreedTypes.SUNLIGHT, EnumDragonBreed.SUNLIGHT);
    public static final ItemDragonAmulet AmuletStorm = new ItemDragonAmulet(EnumItemBreedTypes.STORM, EnumDragonBreed.STORM);
    public static final ItemDragonAmulet AmuletZombie = new ItemDragonAmulet(EnumItemBreedTypes.ZOMBIE, EnumDragonBreed.ZOMBIE);
    public static final ItemDragonAmulet AmuletTerra = new ItemDragonAmulet(EnumItemBreedTypes.TERRA, EnumDragonBreed.TERRA);
    public static final ItemDragonAmulet AmuletMoonlight = new ItemDragonAmulet(EnumItemBreedTypes.MOONLIGHT, EnumDragonBreed.MOONLIGHT);
    public static final ItemDragonAmuletEmpty AmuletEmpty = new ItemDragonAmuletEmpty();
    //Amulet End

    //Other Start
    public static final Item structure_spawner = new ItemStructureSpawner("structure_spawner");
    public static final Item dragon_wand = new ItemDragonWand("dragon_wand");
    public static final Item dragon_whistle = new ItemDragonWhistle();
    public static final Item gender = new ItemDragonGender("dragon_gender");
    //Other End

    //Carriages Start
    public static final Item carriage_oak = new ItemCarriage("carriage_", EntityCarriage.Type.OAK);
    public static final Item carriage_spruce = new ItemCarriage("carriage_", EntityCarriage.Type.SPRUCE);
    public static final Item carriage_birch = new ItemCarriage("carriage_", EntityCarriage.Type.BIRCH);
    public static final Item carriage_darkoak = new ItemCarriage("carriage_", EntityCarriage.Type.DARK_OAK);
    public static final Item carriage_jungle = new ItemCarriage("carriage_", EntityCarriage.Type.JUNGLE);
    public static final Item carriage_acacia = new ItemCarriage("carriage_", EntityCarriage.Type.ACACIA);
    //Carriages end

    //Shields start 
    public static final Item aether_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.AETHER, ModItems.AetherDragonScales);
    public static final Item forest_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.FOREST, ModItems.ForestDragonScales);
    public static final Item fire_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.FIRE, ModItems.FireDragonScales);
    public static final Item fire2_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.FIRE2, ModItems.FireDragonScales2);
    public static final Item ice_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.ICE, ModItems.IceDragonScales);
    public static final Item water_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.WATER, ModItems.WaterDragonScales);
    public static final Item sunlight_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.SUNLIGHT, ModItems.SunlightDragonScales);
    public static final Item sunlight2_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.SUNLIGHT2, ModItems.SunlightDragonScales2);
    public static final Item enchant_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.ENCHANT, ModItems.EnchantDragonScales);
    public static final Item storm_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.STORM, ModItems.StormDragonScales);
    public static final Item nether_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.NETHER, ModItems.NetherDragonScales);
    public static final Item ender_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.END, ModItems.EnderDragonScales);
    public static final Item terra_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.TERRA, ModItems.TerraDragonScales);
    public static final Item terra2_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.TERRA2, ModItems.TerraDragonScales2);
    public static final Item moonlight_dragon_shield = new ItemDragonShield(EnumItemBreedTypes.MOONLIGHT, ModItems.MoonlightDragonScales);

    public static final Item aether_dragon_bow = new ItemDragonAetherBow(EnumItemBreedTypes.AETHER, ModItems.AetherDragonScales);
    public static final Item forest_dragon_bow = new ItemDragonForestBow(EnumItemBreedTypes.FOREST, ModItems.ForestDragonScales);
    public static final Item fire_dragon_bow = new ItemDragonFireBow(EnumItemBreedTypes.FIRE, ModItems.FireDragonScales);
//    public static final Item fire2_dragon_bow = new ItemDragonBow(EnumItemBreedTypes.FIRE2, ModItems.FireDragonScales2);
    public static final Item ice_dragon_bow = new ItemDragonIceBow(EnumItemBreedTypes.ICE, ModItems.IceDragonScales);
    public static final Item water_dragon_bow = new ItemDragonWaterBow(EnumItemBreedTypes.WATER, ModItems.WaterDragonScales);
    public static final Item sunlight_dragon_bow = new ItemDragonSunlightBow(EnumItemBreedTypes.SUNLIGHT, ModItems.SunlightDragonScales);
//    public static final Item sunlight2_dragon_bow = new ItemDragonBow(EnumItemBreedTypes.SUNLIGHT2, ModItems.SunlightDragonScales2);
    public static final Item enchant_dragon_bow = new ItemDragonEnchantBow(EnumItemBreedTypes.ENCHANT, ModItems.EnchantDragonScales);
    public static final Item storm_dragon_bow = new ItemDragonStormBow(EnumItemBreedTypes.STORM, ModItems.StormDragonScales);
    public static final Item nether_dragon_bow = new ItemDragonNetherBow(EnumItemBreedTypes.NETHER, ModItems.NetherDragonScales);
    public static final Item ender_dragon_bow = new ItemDragonEndBow(EnumItemBreedTypes.END, ModItems.EnderDragonScales);
    public static final Item terra_dragon_bow = new ItemDragonTerraBow(EnumItemBreedTypes.TERRA, ModItems.TerraDragonScales);
//    public static final Item terra2_dragon_bow = new ItemDragonBow(EnumItemBreedTypes.TERRA2, ModItems.TerraDragonScales2);
    public static final Item moonlight_dragon_bow = new ItemDragonMoonlightBow(EnumItemBreedTypes.MOONLIGHT, ModItems.MoonlightDragonScales);
//    public static final Item storm_dragon_bow2 = new ItemDragonMoonlightBow(EnumItemBreedTypes.ST, ModItems.MoonlightDragonScales);
    public static final Item skelton_dragon_bow = new ItemDragonSkeletonBow(EnumItemBreedTypes.SKELETON, Items.BONE);
    public static final Item zombie_dragon_bow = new ItemDragonZombieBow(EnumItemBreedTypes.ZOMBIE, Items.BONE);

    public static final Item item_dragon_shulker = new ItemDragonShulker(ModBlocks.dragonshulker);
    /**
     * @WolfShotz Registering OreDictionary for vanilla Fish, since Forge doesn't do it on its own for some stupid reason...
     */
    public void register() {
        OreDictionary.registerOre(listAllfishraw, new ItemStack(Items.FISH, 1, OreDictionary.WILDCARD_VALUE));
    }

    //Setting String for oredict entry
    public static final String listAllfishraw = "listAllfishraw";

}
