package com.TheRPGAdventurer.ROTD;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.HashSet;
import java.util.Set;

import static com.TheRPGAdventurer.ROTD.DragonMountsLootTables.RegistrationHandler.create;

public class DragonMountsLootTables {
	
	public static final ResourceLocation ENTITIES_DRAGON_WATER = create("water");
	public static final ResourceLocation ENTITIES_DRAGON_FIRE = create("fire");
	public static final ResourceLocation ENTITIES_DRAGON_FIRE2 = create("fire2");
	public static final ResourceLocation ENTITIES_DRAGON_FOREST = create("forest");
	public static final ResourceLocation ENTITIES_DRAGON_FOREST2 = create("forest2");
	public static final ResourceLocation ENTITIES_DRAGON_ICE = create("ice");
	public static final ResourceLocation ENTITIES_DRAGON_AETHER = create("aether");
	public static final ResourceLocation ENTITIES_DRAGON_END = create("ender");
	public static final ResourceLocation ENTITIES_DRAGON_NETHER = create("nether");
	public static final ResourceLocation ENTITIES_DRAGON_NETHER2 = create("nether2");
	public static final ResourceLocation ENTITIES_DRAGON_SKELETON = create("skeleton");
	public static final ResourceLocation ENTITIES_DRAGON_SUNLIGHT = create("sunlight");
	public static final ResourceLocation ENTITIES_DRAGON_SUNLIGHT2 = create("sunlight2");
	public static final ResourceLocation ENTITIES_DRAGON_STORM = create("storm");
	public static final ResourceLocation ENTITIES_DRAGON_STORM2 = create("storm2");
	public static final ResourceLocation ENTITIES_DRAGON_ENCHANT = create("enchant");
	public static final ResourceLocation ENTITIES_DRAGON_ZOMBIE = create("zombie");
	public static final ResourceLocation ENTITIES_DRAGON_TERRA = create("terra");
	public static final ResourceLocation ENTITIES_DRAGON_TERRA2 = create("terra2");
	public static final ResourceLocation ENTITIES_DRAGON_MOONLIGHT = create("moonlight");
//	public static final ResourceLocation ENTITIES_DRAGON_LIGHT = create("light");
//	public static final ResourceLocation ENTITIES_DRAGON_DARK = create("DARK");
//	public static final ResourceLocation ENTITIES_DRAGON_SPECTER = create("specter");
	
	/**
	 * Register this mod's {@link LootTable}s.
	 */
	public static void registerLootTables() {
		RegistrationHandler.LOOT_TABLES.forEach(LootTableList::register);
	}

	public static class RegistrationHandler {
		
		/**
		 * Stores the IDs of this mod's {@link LootTable}s.
		 */
		private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();

		/**
		 * Create a {@link LootTable} ID.
		 *
		 * @param id The ID of the LootTable without the testmod3: prefix
		 * @return The ID of the LootTable
		 */
		protected static ResourceLocation create(String id) {
			final ResourceLocation lootTable = new ResourceLocation(DragonMounts.MODID, id);
			RegistrationHandler.LOOT_TABLES.add(lootTable);
			return lootTable;
		}
	}
}	