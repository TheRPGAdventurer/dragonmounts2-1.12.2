/*
 ** 2012 August 27
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.client;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.client.gui.GuiDragonDebug;
import com.TheRPGAdventurer.ROTD.client.handler.DragonEntityWatcher;
import com.TheRPGAdventurer.ROTD.client.handler.DragonViewEvent;
import com.TheRPGAdventurer.ROTD.client.render.RenderCarriage;
import com.TheRPGAdventurer.ROTD.client.render.TileEntityDragonShulkerRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breathweaponFX.*;
import com.TheRPGAdventurer.ROTD.server.ServerProxy;
import com.TheRPGAdventurer.ROTD.server.blocks.tileentities.TileEntityDragonShulker;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breathweapon.*;
import com.TheRPGAdventurer.ROTD.server.initialization.ModKeys;
import com.TheRPGAdventurer.ROTD.server.items.entity.ImmuneEntityItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
<<<<<<< HEAD
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
=======
import scala.actors.threadpool.Arrays;
>>>>>>> e66eb930c0e73ae27d84e99dd2388fb85cbf0abe

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * 2nd @author TheRPGAdventurer
 */
public class ClientProxy extends ServerProxy {

    private int thirdPersonViewDragon = 0;
    private int followYaw = 0;
    private int hover = 0;
    private ModMetadata metadata;

    @Override
    public void PreInitialization(FMLPreInitializationEvent event) {
        super.PreInitialization(event);
        // register dragon entity renderer
        DragonMountsConfig.clientPreInit();
        MinecraftForge.EVENT_BUS.register(new DragonEntityWatcher());
        RenderingRegistry.registerEntityRenderingHandler(EntityTameableDragon.class, DragonRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(HydroBreathFX.class, RenderHydroBreathFX::new);
        RenderingRegistry.registerEntityRenderingHandler(FlameBreathFX.class, RenderFlameBreathFX::new);
        RenderingRegistry.registerEntityRenderingHandler(EnderBreathFX.class, RenderEnderBreathFX::new);
        RenderingRegistry.registerEntityRenderingHandler(NetherBreathFX.class, RenderNetherBreathFX::new);
        RenderingRegistry.registerEntityRenderingHandler(WitherBreathFX.class, RenderWitherBreathFX::new);
        RenderingRegistry.registerEntityRenderingHandler(IceBreathFX.class, RenderIceBreathFX::new);
        RenderingRegistry.registerEntityRenderingHandler(PoisonBreathFX.class, RenderPoisonBreathFX::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCarriage.class, RenderCarriage::new);
<<<<<<< HEAD

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragonShulker.class, new TileEntityDragonShulkerRenderer());

        metadata = event.getModMetadata();
        metadata.name = "Â§3Â§lDragon Mounts";
        metadata.credits =
                "\nÂ§aBarracudaATA4Â§r - Â§bThe Original Owner" +
                        "\n\nÂ§aFlaemWingÂ§r - Â§bfor new nest block textures and dragonarmor item textures, new tool textures" +
                        "\n\nÂ§aMerpou/Kingdomall/Masked_AresÂ§r - Â§bmore textures much help, First Dev for Dragon Mounts, Overall Second Dev :D Thanks Man... (just found out shes a girl BTW O_O)" +
                        "\n\nÂ§aGundunUkan/Lord UkanÂ§r - Â§bfor new fire texures, sunlight textures, and more.... I Hope he finishes his university hes a hardworking working student" +
                        "\n\nÂ§aAlexThe666Â§r - Â§bfor open source code, Ice and Fire owner, Older Matured and more experience than me" +
                        "\n\nÂ§aShannieanneÂ§r - Â§bZombie Textures, Terra textures, Texture Fixes, Overall Second Dev" +
                        "\n\nÂ§aMajty/Guinea OwlÂ§r - Â§bfor amulet textures" +
                        "\n\nÂ§aWolfÂ§r - Â§bSecond Coder, started making small fixes then started doing big ones, I hope his dreams of becoming a computer engineer succeeds\n";
        metadata.authorList = Arrays.asList(StringUtils.split("Â§6Â§lTheRpgAdventurerÂ§r,Â§6Â§lBarracudaATAÂ§r,Â§6Â§lKingdomallÂ§r,Â§6Â§lShannieanneÂ§r,Â§6Â§lWolfShotzÂ§r", ','));
        metadata.description =
                "\nÂ§c1.Â§r Don't forget to right click the egg to start the hatching process\n" +
                        "Â§c2.Â§r Also water dragon needs to be struck by lightning to become a storm dragon\n" +
                        "Â§c3.Â§r You can't hatch eggs in the End Dimension\n" +
                        "Â§c4.Â§r You can press Â§octrlÂ§r to enable boost flight\n" +
                        "Â§c5.Â§r Dragons need to be of opposite genders to breed";
=======
        
//      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragonShulker.class, new TileEntityDragonShulkerRenderer(new ModelShulker()));
        
        //Override mcmod.info - This looks cooler :)
        metadata = event.getModMetadata();
        metadata.name = "§3§lDragon Mounts";
        metadata.credits = 
        		"\n§aBarracudaATA4§r - §bThe Original Owner" +
        		"\n\n§aFlaemWing§r - §bfor new nest block textures and dragonarmor item textures, new tool textures" + 
        		"\n\n§aMerpou/Kingdomall/Masked_Ares§r - §bmore textures much help, First Dev for Dragon Mounts, Overall Second Dev :D Thanks Man... (just found out shes a girl BTW O_O)" +
        		"\n\n§aGundunUkan/Lord Ukan§r - §bfor new fire texures, sunlight textures, and more.... I Hope he finishes his university hes a hardworking working student" +
        		"\n\n§aAlexThe666§r - §bfor open source code, Ice and Fire owner, Older Matured and more experience than me" +
        		"\n\n§aShannieanne§r - §bZombie Textures, Terra textures, Texture Fixes, Overall Second Dev" +
        		"\n\n§aMajty/Guinea Owl§r - §bfor amulet textures" +
        		"\n\n§aWolf§r - §bSecond Coder, started making small fixes then started doing big ones, I hope his dreams of becoming a computer engineer succeeds\n";
        metadata.authorList = Arrays.asList(StringUtils.split("§6§lTheRpgAdventurer§r,§6§lBarracudaATA§r,§6§lKingdomall§r,§6§lShannieanne§r,§6§lWolfShotz§r", ','));
        metadata.description =
        		"\n§c1.§r Don't forget to right click the egg to start the hatching process\n" +
        		"§c2.§r Also water dragon needs to be struck by lightning to become a storm dragon\n" +
        		"§c3.§r You can't hatch eggs in the End Dimension\n" +
        		"§c4.§r You can press §octrl§r to enable boost flight\n" +
        		"§c5.§r Dragons need to be of opposite genders to breed";
>>>>>>> e66eb930c0e73ae27d84e99dd2388fb85cbf0abe
    }
    
    @Override
    public void Initialization(FMLInitializationEvent evt) {
        super.Initialization(evt);
    }

    @Override
    public void PostInitialization(FMLPostInitializationEvent event) {
        super.PostInitialization(event);

        if (DragonMountsConfig.isDebug()) {
            MinecraftForge.EVENT_BUS.register(new GuiDragonDebug());
        }
        MinecraftForge.EVENT_BUS.register(new ModKeys());
        MinecraftForge.EVENT_BUS.register(new DragonViewEvent());
        MinecraftForge.EVENT_BUS.register(ImmuneEntityItem.EventHandler.instance);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void render() {
        ModKeys.init();
    }

    public int getDragon3rdPersonView() {
        return thirdPersonViewDragon;
    }

    public void setDragon3rdPersonView(int view) {
        thirdPersonViewDragon = view;
    }

    public int getDragonFollowYaw() {
        return followYaw;
    }

    public void setDragonFollowYaw(int followYaw) {
        this.followYaw = followYaw;
    }

    @Override
    public int getDragonHover() {
        return hover;
    }

    @Override
    public void setDragonHover(int hover) {
        this.hover = hover;
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
}
