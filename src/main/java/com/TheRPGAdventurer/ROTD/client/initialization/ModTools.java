package com.TheRPGAdventurer.ROTD.client.initialization;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.items.ItemDiamondShears;
import com.TheRPGAdventurer.ROTD.client.items.gemset.ItemDragonAxe;
import com.TheRPGAdventurer.ROTD.client.items.gemset.ItemDragonHoe;
import com.TheRPGAdventurer.ROTD.client.items.gemset.ItemDragonPickAxe;
import com.TheRPGAdventurer.ROTD.client.items.gemset.ItemDragonShovel;
import com.TheRPGAdventurer.ROTD.client.items.gemset.ItemDragonSword;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class ModTools {
	
	static float damage = 10.0F; static float speed = -2.9F; static float netherDamage = 12.0F; static float netherSpeed = -3.0F; static float enderDamage = 9.0F; static float enderSpeed = -3.3F;

	public static final ToolMaterial ForestDragonScaleMaterial     = EnumHelper.addToolMaterial(DragonMounts.MODID + ":forestdragonscales", 4, 1700, 8.0F, 5.0F, 11);
	public static final ToolMaterial FireDragonScaleMaterial     = EnumHelper.addToolMaterial(DragonMounts.MODID + ":icedragonscales", 4, 1700, 8.0F, 5.0F, 11);
	public static final ToolMaterial AetherDragonScaleMaterial   = EnumHelper.addToolMaterial(DragonMounts.MODID + ":aetherdragonscales", 4, 1700, 8.0F, 5.0F, 11);
	public static final ToolMaterial IceDragonScaleMaterial = EnumHelper.addToolMaterial(DragonMounts.MODID + ":firedragonscales", 4, 1700, 8.0F, 5.0F, 11);
	public static final ToolMaterial WaterDragonScaleMaterial = EnumHelper.addToolMaterial(DragonMounts.MODID + ":waterdragonscales", 4, 1700, 8.0F, 5.0F, 11);
	public static final ToolMaterial NetherDragonScaleMaterial   = EnumHelper.addToolMaterial(DragonMounts.MODID + ":netherdragonscales", 5, 1770, 8.0F, 7.0F, 11);
	public static final ToolMaterial EnderDragonScaleMaterial    = EnumHelper.addToolMaterial(DragonMounts.MODID + ":enderdragonscales", 5, 2000, 8.0F, 7.0F, 11);
	
	public static ItemDragonPickAxe forestDragonPickaxe = new ItemDragonPickAxe(ForestDragonScaleMaterial, "forest_dragon_pickaxe", EnumItemBreedTypes.FOREST);
	public static ItemDragonAxe  forestDragonAxe = new ItemDragonAxe(ForestDragonScaleMaterial, "forest_dragon_axe", damage, speed, EnumItemBreedTypes.FOREST);
	public static ItemDragonShovel forestDragonShovel = new ItemDragonShovel(ForestDragonScaleMaterial, "forest_dragon_shovel", EnumItemBreedTypes.FOREST);
	public static ItemDragonSword forestDragonSword = new ItemDragonSword(ForestDragonScaleMaterial, "forest_dragon_sword", EnumItemBreedTypes.FOREST);
	
	public static ItemDragonPickAxe aetherDragonPickaxe = new ItemDragonPickAxe(AetherDragonScaleMaterial, "aether_dragon_pickaxe", EnumItemBreedTypes.AETHER);
	public static ItemDragonAxe aetherDragonAxe = new ItemDragonAxe(AetherDragonScaleMaterial, "aether_dragon_axe", damage, speed, EnumItemBreedTypes.AETHER);
	public static ItemDragonShovel aetherDragonShovel = new ItemDragonShovel(AetherDragonScaleMaterial, "aether_dragon_shovel", EnumItemBreedTypes.AETHER);
	public static ItemDragonSword aetherDragonSword = new ItemDragonSword(AetherDragonScaleMaterial, "aether_dragon_sword", EnumItemBreedTypes.AETHER);
	
	public static ItemDragonPickAxe waterDragonPickaxe = new ItemDragonPickAxe(WaterDragonScaleMaterial, "water_dragon_pickaxe", EnumItemBreedTypes.WATER);
	public static ItemDragonAxe waterDragonAxe = new ItemDragonAxe(WaterDragonScaleMaterial, "water_dragon_axe", damage, damage, EnumItemBreedTypes.WATER);
	public static ItemDragonShovel waterDragonShovel = new ItemDragonShovel(WaterDragonScaleMaterial, "water_dragon_shovel", EnumItemBreedTypes.WATER);
	public static ItemDragonSword waterDragonSword = new ItemDragonSword(WaterDragonScaleMaterial, "water_dragon_sword", EnumItemBreedTypes.WATER);
	public static ItemDragonHoe waterDragonHoe = new ItemDragonHoe(WaterDragonScaleMaterial, "water_dragon_hoe", EnumItemBreedTypes.WATER);
    
	public static ItemDragonPickAxe fireDragonPickaxe = new ItemDragonPickAxe(FireDragonScaleMaterial, "fire_dragon_pickaxe", EnumItemBreedTypes.FIRE);
	public static ItemDragonAxe fireDragonAxe = new ItemDragonAxe(FireDragonScaleMaterial, "fire_dragon_axe", damage, damage, EnumItemBreedTypes.FIRE);
	public static ItemDragonShovel fireDragonShovel = new ItemDragonShovel(FireDragonScaleMaterial, "fire_dragon_shovel", EnumItemBreedTypes.FIRE);
	public static ItemDragonSword fireDragonSword = new ItemDragonSword(FireDragonScaleMaterial, "fire_dragon_sword", EnumItemBreedTypes.FIRE);
	public static ItemDragonHoe fireDragonHoe = new ItemDragonHoe(FireDragonScaleMaterial, "fire_dragon_hoe", EnumItemBreedTypes.FIRE);
	
	public static ItemDragonPickAxe iceDragonPickaxe = new ItemDragonPickAxe(IceDragonScaleMaterial, "ice_dragon_pickaxe", EnumItemBreedTypes.ICE);
	public static ItemDragonAxe iceDragonAxe = new ItemDragonAxe(IceDragonScaleMaterial, "ice_dragon_axe", damage, damage, EnumItemBreedTypes.ICE);
	public static ItemDragonShovel iceDragonShovel = new ItemDragonShovel(IceDragonScaleMaterial, "ice_dragon_shovel", EnumItemBreedTypes.ICE);
	public static ItemDragonSword iceDragonSword = new ItemDragonSword(IceDragonScaleMaterial, "ice_dragon_sword", EnumItemBreedTypes.ICE);
	public static ItemDragonHoe iceDragonHoe = new ItemDragonHoe(IceDragonScaleMaterial, "ice_dragon_hoe", EnumItemBreedTypes.ICE);
	
	public static ItemDragonPickAxe netherDragonPickaxe = new ItemDragonPickAxe(NetherDragonScaleMaterial, "nether_dragon_pickaxe", EnumItemBreedTypes.NETHER);
	public static ItemDragonAxe netherDragonAxe = new ItemDragonAxe(NetherDragonScaleMaterial, "nether_dragon_axe", netherDamage, netherSpeed, EnumItemBreedTypes.NETHER);
	public static ItemDragonShovel netherDragonShovel = new ItemDragonShovel(NetherDragonScaleMaterial, "nether_dragon_shovel", EnumItemBreedTypes.NETHER);
	public static ItemDragonSword netherDragonSword = new ItemDragonSword(NetherDragonScaleMaterial, "nether_dragon_sword", EnumItemBreedTypes.NETHER);
	
	public static ItemDragonPickAxe enderDragonPickaxe = new ItemDragonPickAxe(EnderDragonScaleMaterial, "ender_dragon_pickaxe", EnumItemBreedTypes.END);
	public static ItemDragonAxe enderDragonAxe = new ItemDragonAxe(EnderDragonScaleMaterial, "ender_dragon_axe", enderDamage, enderSpeed, EnumItemBreedTypes.END);
	public static ItemDragonShovel enderDragonShovel = new ItemDragonShovel(EnderDragonScaleMaterial, "ender_dragon_shovel", EnumItemBreedTypes.END);
	public static ItemDragonSword enderDragonSword = new ItemDragonSword(EnderDragonScaleMaterial, "ender_dragon_sword", EnumItemBreedTypes.END);
	
	public static ItemDiamondShears diamond_shears = new ItemDiamondShears(ToolMaterial.DIAMOND, "diamond_shears");
	
	public static final Item[] TOOLS = {
		forestDragonPickaxe, forestDragonAxe, forestDragonShovel, forestDragonSword,
		iceDragonPickaxe, iceDragonAxe, iceDragonShovel, iceDragonSword, iceDragonHoe,
	    fireDragonAxe, fireDragonPickaxe, fireDragonShovel, fireDragonSword, fireDragonHoe,
		aetherDragonAxe, aetherDragonPickaxe, aetherDragonShovel,  aetherDragonSword,
	    waterDragonAxe, waterDragonPickaxe, waterDragonShovel, waterDragonSword, waterDragonHoe,
	    netherDragonAxe, netherDragonPickaxe, netherDragonShovel, netherDragonSword,
	    enderDragonAxe, enderDragonPickaxe, enderDragonShovel, enderDragonSword,
	    diamond_shears 
	};
	
	public static void initRepairs() {
		IceDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.IceDragonScales));
		AetherDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.AetherDragonScales));
		ForestDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.ForestDragonScales));
		FireDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.FireDragonScales));
		WaterDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.WaterDragonScales));
		NetherDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.NetherDragonScales));
		EnderDragonScaleMaterial.setRepairItem(new ItemStack(ModItems.EnderDragonScales));

	}
	
}