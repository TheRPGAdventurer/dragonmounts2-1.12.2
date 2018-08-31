/*
 ** 2012 August 27
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.server.cmd.CommandDragon;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.handler.DragonEggBlockHandler;
import com.TheRPGAdventurer.ROTD.server.network.DragonBreathMessage;
import com.TheRPGAdventurer.ROTD.server.network.DragonBreathMessageHandlerServer;
import com.TheRPGAdventurer.ROTD.server.network.DragonControlMessage;
import com.TheRPGAdventurer.ROTD.server.network.DragonControlMessageHandler;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * 2nd @author TheRPGAdventurer
 */
public class ServerProxy {
	
    private SimpleNetworkWrapper network;    
    public final byte DCM_DISCRIMINATOR_ID = 35;  // arbitrary non-zero ID (non-zero makes troubleshooting easier)
    public final byte DOT_DISCRIMINATOR_ID = 73;  // arbitrary non-zero ID (non-zero makes troubleshooting easier)
    
    private final int ENTITY_TRACKING_RANGE = 80;
    private final int ENTITY_UPDATE_FREQ = 3;
    private final int ENTITY_ID = 1;
    private final boolean ENTITY_SEND_VELO_UPDATES = true;
    
    public SimpleNetworkWrapper getNetwork() {
        return network;
    }
    
    public void PreInitialization(FMLPreInitializationEvent event) {
    	DragonMountsConfig.PreInit();
    }
    
    
    public void Initialization(FMLInitializationEvent evt) { 
    	Biome biomes[] = {Biomes.BEACH, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.EXTREME_HILLS_WITH_TREES, Biomes.MUTATED_EXTREME_HILLS, Biomes.MUTATED_EXTREME_HILLS_WITH_TREES, Biomes.STONE_BEACH, Biomes.BEACH, Biomes.ICE_PLAINS, Biomes.MUTATED_ICE_FLATS, Biomes.ICE_MOUNTAINS}; 
        MinecraftForge.EVENT_BUS.register(new DragonEggBlockHandler());
//        EntityRegistry.addSpawn(EntityTameableDragon.class, 1, 1, 1, EnumCreatureType.AMBIENT, biomes);
        network = NetworkRegistry.INSTANCE.newSimpleChannel("DragonControls");
        network.registerMessage(DragonControlMessageHandler.class, DragonControlMessage.class, DCM_DISCRIMINATOR_ID, Side.SERVER);
        network.registerMessage(DragonBreathMessageHandlerServer.class, DragonBreathMessage.class, DOT_DISCRIMINATOR_ID, Side.SERVER);

    }

    public void PostInitialization(FMLPostInitializationEvent event) {
		registerEntities();
    	
    }
    
    public void ServerStarting(FMLServerStartingEvent evt) {
        MinecraftServer server = evt.getServer();
        ServerCommandManager cmdman = (ServerCommandManager) server.getCommandManager(); 
        cmdman.registerCommand(new CommandDragon());
    }
    
    public void ServerStopped(FMLServerStoppedEvent evt) {}
    
    private void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation(DragonMounts.MODID, "dragon"), EntityTameableDragon.class, "DragonMount",
                ENTITY_ID, DragonMounts.instance, ENTITY_TRACKING_RANGE, ENTITY_UPDATE_FREQ,
                ENTITY_SEND_VELO_UPDATES, 0Xc30c0c, 0X220e21);        
    } 
    
	public void render() {}
}