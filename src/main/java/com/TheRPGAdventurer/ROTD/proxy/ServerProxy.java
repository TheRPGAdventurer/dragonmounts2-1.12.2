/*
 ** 2012 August 27
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.proxy;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.cmd.CommandDragon;
import com.TheRPGAdventurer.ROTD.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.handler.DragonEggBlockEvents;
import com.TheRPGAdventurer.ROTD.items.entity.ImmuneEntityItem;
import com.TheRPGAdventurer.ROTD.util.debugging.StartupDebugCommon;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.io.File;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * 2nd @author TheRPGAdventurer
 */
public class ServerProxy {

    private SimpleNetworkWrapper network;
    public final byte DCM_DISCRIMINATOR_ID = 35;  // arbitrary non-zero ID (non-zero makes troubleshooting easier)
    public final byte DOT_DISCRIMINATOR_ID = 73;  // arbitrary non-zero ID (non-zero makes troubleshooting easier)

    private final int ENTITY_TRACKING_RANGE = 80;
    private final int ENTITY_UPDATE_FREQ = 3; // 3
    private final int ENTITY_ID = 1;
    private final boolean ENTITY_SEND_VELO_UPDATES = true;

    public SimpleNetworkWrapper getNetwork() {
        return this.network;
    }

    public void PreInitialization(FMLPreInitializationEvent event) {
        DragonMountsConfig.PreInit();
      if (DragonMountsConfig.isDebug()) {
        StartupDebugCommon.preInitCommon();
      }

    }


    public void Initialization(FMLInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(new DragonEggBlockEvents());
        network = NetworkRegistry.INSTANCE.newSimpleChannel("DragonControls");
      if (DragonMountsConfig.isDebug()) {
        StartupDebugCommon.initCommon();
      }
    }

    public void PostInitialization(FMLPostInitializationEvent event) {
        registerEntities();
      if (DragonMountsConfig.isDebug()) {
        StartupDebugCommon.postInitCommon();
      }
    }

    public void ServerStarting(FMLServerStartingEvent evt) {
        MinecraftServer server = evt.getServer();
        ServerCommandManager cmdman = (ServerCommandManager) server.getCommandManager();
        cmdman.registerCommand(new CommandDragon());
    }

    public void ServerStopped(FMLServerStoppedEvent evt) {
    }

    private void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation(DragonMounts.MODID, "dragon"), EntityTameableDragon.class, "DragonMount",
                ENTITY_ID, DragonMounts.instance, ENTITY_TRACKING_RANGE, ENTITY_UPDATE_FREQ,
                ENTITY_SEND_VELO_UPDATES);
        EntityRegistry.registerModEntity(new ResourceLocation(DragonMounts.MODID, "carriage"), EntityCarriage.class, "DragonCarriage",
                2, DragonMounts.instance, 32, ENTITY_UPDATE_FREQ,
                ENTITY_SEND_VELO_UPDATES);
        EntityRegistry.registerModEntity(new ResourceLocation(DragonMounts.MODID, "indestructible"), ImmuneEntityItem.class, "Indestructible Item",
                3, DragonMounts.instance, 32, 5, true);

        //        GameRegistry.registerTileEntity(TileEntityDragonShulker.class, new ResourceLocation(DragonMounts.MODID, "dragon_shulker"));

    }

    public void render() {
    }

    public int getDragon3rdPersonView() {
        return 0;
    }

    public void setDragon3rdPersonView(int view) {
    }

    public boolean getDragonFollowYaw() {
        return false;
    }

    public void setDragonFollowYaw(boolean yaw) {
    }

    public int getDragonLockY() {
        return 0;
    }

    public void setDragonLockY(int yaw) {
    }

    public boolean getDragonHover() {
        return false;
    }

    public void setDragonHover(boolean hover) {
    }

    public void registerModel(Item item, int metadata)
    {
    }

    public void registerItemRenderer(Item item, int meta, String id)
    {
    }

    // get the directory on disk used for storing the game files
    // is different for dedicated server vs client
    public File getDataDirectory() {
    return FMLServerHandler.instance().getSavesDirectory();
  }


}