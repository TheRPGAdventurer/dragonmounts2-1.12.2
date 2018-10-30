package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.client.initialization.ModArmour;
import com.TheRPGAdventurer.ROTD.client.initialization.ModBlocks;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.initialization.ModTools;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber
public class RegistryEventHandler {
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {	
		event.getRegistry().registerAll(ModBlocks.BLOCKS);
		DMUtils.getLogger().info("Block Registries Successfully Registered!");
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {	
		event.getRegistry().registerAll(ModItems.ITEMS);
		event.getRegistry().registerAll(ModTools.TOOLS);
		event.getRegistry().registerAll(ModArmour.ARMOR);
	
		for (Block block : ModBlocks.BLOCKS) {
			event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
		
		DMUtils.getLogger().info("Item Registries Successfully Registered!");
	}
	
	@SubscribeEvent
	public static void registerDragonEggItem(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(ItemDragonBreedEgg.DRAGON_BREED_EGG.setRegistryName("dragon_egg"));
	}
	
	@SubscribeEvent
	public static void registerDragonnEggBlock(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(BlockDragonBreedEgg.DRAGON_BREED_EGG.setRegistryName("dragon_egg"));
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for (Block block: ModBlocks.BLOCKS) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		
		for (Item item: ModItems.ITEMS) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
		}
		
		for (Item item: ModTools.TOOLS) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
		}
		
		for (Item item: ModArmour.ARMOR) {
		    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
		}
		
		for (Item itemegg: ItemDragonBreedEgg.ITEM_EGG) {
			// register item renderer for dragon egg block variants
	        ResourceLocation eggModelItemLoc = new ResourceLocation(DragonMounts.MODID, "dragon_egg");
	        Item itemBlockDragonEgg = Item.REGISTRY.getObject(eggModelItemLoc);
	        EnumDragonBreed.META_MAPPING.forEach((breed, meta) -> {
	            ModelResourceLocation eggModelLoc = new ModelResourceLocation(DragonMounts.MODID + ":dragon_egg", "breed=" + breed.getName());
	            ModelLoader.setCustomModelResourceLocation(itemBlockDragonEgg, meta, eggModelLoc);
	        });
		}
		
		for (Block blockegg: BlockDragonBreedEgg.BLOCK_EGG) {
			// register item renderer for dragon egg block variants
	        ResourceLocation eggModelItemLoc = new ResourceLocation(DragonMounts.MODID, "dragon_egg");
	        Item itemBlockDragonEgg = Item.REGISTRY.getObject(eggModelItemLoc);
	        EnumDragonBreed.META_MAPPING.forEach((breed, meta) -> {
	            ModelResourceLocation eggModelLoc = new ModelResourceLocation(DragonMounts.MODID + ":dragon_egg", "breed=" + breed.getName());
	            ModelLoader.setCustomModelResourceLocation(itemBlockDragonEgg, meta, eggModelLoc);
	        });
		}
		
		ModTools.initRepairs();
		ModArmour.initRepairs();
		
    	DMUtils.getLogger().info("Models Sucessfully Registered");
	}
  }
	
    @SubscribeEvent
    public void missingMapping(RegistryEvent.MissingMappings<Item> event) {
        for (MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
            if (entry.key.toString().equals("dragonmounts:ender_dragonscales"))  {
                ResourceLocation newTotem = new ResourceLocation("dragonmounts:ender_dragonscales1");
                entry.remap(ForgeRegistries.ITEMS.getValue(newTotem));
            }
        }
    }
}