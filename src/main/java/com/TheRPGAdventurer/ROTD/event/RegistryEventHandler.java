package com.TheRPGAdventurer.ROTD.event;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.inits.ModBlocks;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.inits.ModTools;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.objects.items.ItemDragonAmuletNEW;
import com.TheRPGAdventurer.ROTD.objects.tileentities.TileEntityHandler;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class RegistryEventHandler {
	
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
        TileEntityHandler.registerTileEntities();
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragonShulker.class, new TileEntityDragonShulkerRenderer());
        
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
    public static void registerModels(ModelRegistryEvent event) {
        DragonMounts.proxy.registerModel(Item.getItemFromBlock(ModBlocks.DRAGONSHULKER), 0);
        
        // Register item render for amulet item variants
        ModelLoader.setCustomMeshDefinition(ModItems.Amulet, new ItemDragonAmuletNEW());
        ModelBakery.registerItemVariants(ModItems.Amulet, new ModelResourceLocation("dragonmounts:dragon_amulet"));
        EnumDragonBreed.META_MAPPING.forEach((breed, meta) -> {
        	ModelBakery.registerItemVariants(ModItems.Amulet, new ModelResourceLocation("dragonmounts:" + breed.getName() + "_dragon_amulet"));
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