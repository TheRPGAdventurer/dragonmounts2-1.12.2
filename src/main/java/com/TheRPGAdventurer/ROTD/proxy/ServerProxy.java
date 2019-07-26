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
import com.TheRPGAdventurer.ROTD.event.VanillaEggHandler;
import com.TheRPGAdventurer.ROTD.network.*;
import com.TheRPGAdventurer.ROTD.objects.entity.entitycarriage.EntityCarriage;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.items.entity.EntityDragonAmulet;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.io.File;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * 2nd @author TheRPGAdventurer
 */
public class ServerProxy {

    public final byte DCM_DISCRIMINATOR_ID = 35;  // arbitrary non-zero ID (non-zero makes troubleshooting easier)
    public final byte DOT_DISCRIMINATOR_ID = 73;  // arbitrary non-zero ID (non-zero makes troubleshooting easier)
    private final int ENTITY_TRACKING_RANGE = 80;
    private final int ENTITY_UPDATE_FREQ = 3;
    private final int ENTITY_ID = 1;
    private final boolean ENTITY_SEND_VELO_UPDATES = true;
    private static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("DragonControls");;

    public SimpleNetworkWrapper getNetwork() {
        return this.network;
    }

    public void PreInitialization(FMLPreInitializationEvent event) {
        DragonMountsConfig.PreInit();
        StartupDebugCommon.preInitCommon();
    }

    @SuppressWarnings("deprecation")
    public void Initialization(FMLInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(new VanillaEggHandler());
//    	MinecraftForge.EVENT_BUS.register(new DragonArmourEnchant.ArmourXPBonus()); Not Currently Functional... >.>
        network.registerMessage(MessageDragonTargetHandlerServer.class, MessageDragonTarget.class, DOT_DISCRIMINATOR_ID, Side.SERVER);

        // I wont touch the network the old devs made, seems redundant yeah I know - rpg
        DragonMounts.NETWORK_WRAPPER.registerMessage(MessageDragonBreath.MessageDragonBreathHandler.class, MessageDragonBreath.class, 0, Side.SERVER);
        DragonMounts.NETWORK_WRAPPER.registerMessage(MessageDragonExtras.MessageDragonExtrasHandler.class, MessageDragonExtras.class, 1, Side.SERVER);
        DragonMounts.NETWORK_WRAPPER.registerMessage(MessageDragonFireSupport.MessageDragonFireSupportHandler.class, MessageDragonFireSupport.class, 2, Side.SERVER);
        DragonMounts.NETWORK_WRAPPER.registerMessage(MessageDragonGui.MessageDragonGuiHandler.class, MessageDragonGui.class, 3, Side.SERVER);
        DragonMounts.NETWORK_WRAPPER.registerMessage(MessageDragonInventory.MessageDragonInventoryHandler.class, MessageDragonInventory.class, 4, Side.SERVER);
        DragonMounts.NETWORK_WRAPPER.registerMessage(MessageDragonSit.MessageDragonSitHandler.class, MessageDragonSit.class, 5, Side.SERVER);
        DragonMounts.NETWORK_WRAPPER.registerMessage(MessageDragonTeleport.MessageDragonTeleportHandler.class, MessageDragonTeleport.class, 6, Side.SERVER);
        DragonMounts.NETWORK_WRAPPER.registerMessage(MessageDragonWhistle.MessageDragonWhistleHandler.class, MessageDragonWhistle.class, 7, Side.SERVER);

        StartupDebugCommon.initCommon();
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
        EntityRegistry.registerModEntity(new ResourceLocation(DragonMounts.MODID, "indestructible"), EntityDragonAmulet.class, "Indestructible Item",
                3, DragonMounts.instance, 32, 5, true);
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

    public boolean getDragonHover() {
        return false;
    }

    public void setDragonHover(boolean hover) {
    }

    public int getDragonLockY() {
        return 0;
    }

    public void setDragonLockY(int yaw) {
    }

    public void registerModel(Item item, int metadata) {
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void registerAmuletRenderer() {
    }

    // get the directory on disk used for storing the game files
    // is different for dedicated server vs client
    public File getDataDirectory() {
        return FMLServerHandler.instance().getSavesDirectory();
    }


}