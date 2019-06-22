/*
 ** 2012 August 13
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD;

import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.event.EventLiving;
import com.TheRPGAdventurer.ROTD.event.IItemColorRegistration;
import com.TheRPGAdventurer.ROTD.event.RegistryEventHandler;
import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.inits.ModTools;
import com.TheRPGAdventurer.ROTD.inventory.tabs.ArmoryTab;
import com.TheRPGAdventurer.ROTD.inventory.tabs.CreativeTab;
import com.TheRPGAdventurer.ROTD.network.*;
import com.TheRPGAdventurer.ROTD.proxy.ServerProxy;
import com.TheRPGAdventurer.ROTD.util.MiscPlayerProperties;
import com.TheRPGAdventurer.ROTD.util.debugging.testclasses.LoggerLimit;
import com.TheRPGAdventurer.ROTD.world.DragonMountsWorldGenerator;

import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.ilexiconn.llibrary.server.network.NetworkWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Main control class for Forge.
 */
@Mod(dependencies="required-after:llibrary@[" + DragonMounts.LLIBRARY_VERSION + ",)", modid=DragonMounts.MODID, name=DragonMounts.NAME, version=DragonMounts.VERSION, useMetadata=true, guiFactory=DragonMounts.GUI_FACTORY)
public class DragonMounts {

    @NetworkWrapper({MessageDragonInventory.class, MessageDragonBreath.class, MessageDragonWhistle.class, MessageDragonGuiSit.class, MessageDragonGuiLock.class, MessageDragonTeleport.class, MessageDragonExtras.class})
    public static SimpleNetworkWrapper NETWORK_WRAPPER;

    public static final String NAME="Dragon Mounts";
    public static final String MODID="dragonmounts";
    public static final String VERSION="@VERSION@";
    public static final String LLIBRARY_VERSION="1.7.14";
    public static final String GUI_FACTORY="com.TheRPGAdventurer.ROTD.DragonMountsConfigGuiFactory";

    @SidedProxy(serverSide="com.TheRPGAdventurer.ROTD.proxy.ServerProxy", clientSide="com.TheRPGAdventurer.ROTD.proxy.ClientProxy")
    public static ServerProxy proxy;

    @Instance(value=MODID)
    public static DragonMounts instance;

    private ModMetadata metadata;
    private DragonMountsConfig config;
    public static CreativeTabs mainTab=new CreativeTab("maintab");
    public static CreativeTabs armoryTab=new ArmoryTab("armorytab");

    public DragonMountsConfig getConfig() {
        return config;
    }

    // important for debug in config
    public ModMetadata getMetadata() {
        return metadata;
    }

    public static DamageSource dragons_fire;

  // Add this field to your main class
    public static final Logger logger = LogManager.getLogger(DragonMounts.MODID);
    public static final LoggerLimit loggerLimit = new LoggerLimit(logger);

    @EventHandler
    public void PreInitialization(FMLPreInitializationEvent event) {
        DragonMountsLootTables.registerLootTables();
        MinecraftForge.EVENT_BUS.register(new EventLiving());
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
          MinecraftForge.EVENT_BUS.register(IItemColorRegistration.class);
        }
      proxy.PreInitialization(event);
        metadata=event.getModMetadata();
    }

    @EventHandler
    public void Initialization(FMLInitializationEvent event) {
        proxy.Initialization(event);
        proxy.render();
        ModTools.InitializaRepairs();
        ModArmour.InitializaRepairs();
        EntityPropertiesHandler.INSTANCE.registerProperties(MiscPlayerProperties.class);
        GameRegistry.registerWorldGenerator(new DragonMountsWorldGenerator(), 0);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        initDamageSources();
        RegistryEventHandler.initRegistries();
    }

    @EventHandler
    public void PostInitialization(FMLPostInitializationEvent event) {
        proxy.PostInitialization(event);
    }

    @EventHandler
    public void ServerStarting(FMLServerStartingEvent event) {
        proxy.ServerStarting(event);
    }

    @EventHandler
    public void ServerStopped(FMLServerStoppedEvent event) {
        proxy.ServerStopped(event);
    }

    private void initDamageSources() {
        dragons_fire=new DamageSource("dragons_fire") {
        };
    }
}
