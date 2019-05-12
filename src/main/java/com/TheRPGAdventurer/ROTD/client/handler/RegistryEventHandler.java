package com.TheRPGAdventurer.ROTD.client.handler;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.blocks.tileentities.TileEntityDragonShulker;
import com.TheRPGAdventurer.ROTD.blocks.tileentities.TileEntityHandler;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.client.render.TileEntityDragonShulkerRenderer;
import com.TheRPGAdventurer.ROTD.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.initialization.ModArmour;
import com.TheRPGAdventurer.ROTD.initialization.ModBlocks;
import com.TheRPGAdventurer.ROTD.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.initialization.ModTools;
import com.TheRPGAdventurer.ROTD.items.ItemDragonAmulet;
import com.TheRPGAdventurer.ROTD.items.ItemDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class RegistryEventHandler {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
        TileEntityHandler.registerTileEntities();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragonShulker.class, new TileEntityDragonShulkerRenderer());
        
        DMUtils.getLogger().info("Block Registries Successfully Registered");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
        event.getRegistry().registerAll(ModTools.BOWS.toArray(new Item[0]));
        event.getRegistry().registerAll(ModTools.TOOLS.toArray(new Item[0]));
        event.getRegistry().registerAll(ModArmour.ARMOR); //Will work on this later
        
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
        DragonMounts.proxy.registerModel(Item.getItemFromBlock(ModBlocks.DRAGONSHULKER), 0);
        
        ModelLoader.setCustomMeshDefinition(ModItems.Amulet, new ItemDragonAmulet());
        ModelBakery.registerItemVariants(ModItems.Amulet, new ModelResourceLocation[] {
        		new ModelResourceLocation("dragonmounts:dragon_amulet"),			new ModelResourceLocation("dragonmounts:ice_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:aether_dragon_amulet"),		new ModelResourceLocation("dragonmounts:moonlight_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:enchant_dragon_amulet"),	new ModelResourceLocation("dragonmounts:nether_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:end_dragon_amulet"),		new ModelResourceLocation("dragonmounts:nether2_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:fire_dragon_amulet"),		new ModelResourceLocation("dragonmounts:storm_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:fire2_dragon_amulet"),		new ModelResourceLocation("dragonmounts:storm2_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:forest_dragon_amulet"),		new ModelResourceLocation("dragonmounts:sunlight_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:sunlight2_dragon_amulet"),	new ModelResourceLocation("dragonmounts:terra_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:terra2_dragon_amulet"),		new ModelResourceLocation("dragonmounts:sylphid_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:wither_dragon_amulet"),		new ModelResourceLocation("dragonmounts:zombie_dragon_amulet"),
        		new ModelResourceLocation("dragonmounts:skeleton_dragon_amulet"),
        });

        for (Block block : ModBlocks.BLOCKS) {
        	if (block instanceof IHasModel) {
        		((IHasModel) block).RegisterModels();
        	}
        }

        for (Item item : ModItems.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).RegisterModels();
            }
        }

        for (Item item : ModTools.BOWS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).RegisterModels();
            }
        }
        for (Item item : ModTools.TOOLS) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
        }

        for (Item item : ModArmour.ARMOR) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
        }

        for (Item itemegg : ItemDragonBreedEgg.ITEM_EGG) {
            // register item renderer for dragon egg item variants
            ResourceLocation eggModelItemLoc = new ResourceLocation(DragonMounts.MODID, "dragon_egg");
            Item itemBlockDragonEgg = Item.REGISTRY.getObject(eggModelItemLoc);
            EnumDragonBreed.META_MAPPING.forEach((breed, meta) -> {
                ModelResourceLocation eggModelLoc = new ModelResourceLocation(DragonMounts.MODID + ":dragon_egg", "breed=" + breed.getName());
                ModelLoader.setCustomModelResourceLocation(itemBlockDragonEgg, meta, eggModelLoc);
            });
        }

        for (Block blockegg : BlockDragonBreedEgg.BLOCK_EGG) {
            // register item renderer for dragon egg block variants
            ResourceLocation eggModelItemLoc = new ResourceLocation(DragonMounts.MODID, "dragon_egg");
            Item itemBlockDragonEgg = Item.REGISTRY.getObject(eggModelItemLoc);
            EnumDragonBreed.META_MAPPING.forEach((breed, meta) -> {
                ModelResourceLocation eggModelLoc = new ModelResourceLocation(DragonMounts.MODID + ":dragon_egg", "breed=" + breed.getName());
                ModelLoader.setCustomModelResourceLocation(itemBlockDragonEgg, meta, eggModelLoc);
            });
        }

        DMUtils.getLogger().info("Models Sucessfully Registered");
    }

    public static void preInitRegistries() {
    }

    public static void initRegistries() {
        NetworkRegistry.INSTANCE.registerGuiHandler(DragonMounts.instance, new GuiHandler());
        ModItems.register();
        DMUtils.getLogger().info("Gui's Successfully Registered");
    }
}