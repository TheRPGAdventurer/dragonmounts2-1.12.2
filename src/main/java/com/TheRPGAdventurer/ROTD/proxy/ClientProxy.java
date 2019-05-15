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

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.blocks.tileentities.TileEntityDragonShulker;
import com.TheRPGAdventurer.ROTD.client.gui.GuiDragonDebug;
import com.TheRPGAdventurer.ROTD.client.handler.DragonViewEvent;
import com.TheRPGAdventurer.ROTD.client.render.RenderCarriage;
import com.TheRPGAdventurer.ROTD.client.render.TileEntityDragonShulkerRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breathweaponFX.*;
import com.TheRPGAdventurer.ROTD.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breath.effects.*;
import com.TheRPGAdventurer.ROTD.event.DragonEntityWatcher;
import com.TheRPGAdventurer.ROTD.inits.ModKeys;
import com.TheRPGAdventurer.ROTD.items.entity.ImmuneEntityItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
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
import org.apache.commons.lang3.StringUtils;
import scala.actors.threadpool.Arrays;

//import java.util.Arrays;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * 2nd @author TheRPGAdventurer
 */
public class ClientProxy extends ServerProxy {

    private int thirdPersonViewDragon = 0;
    private int lockY = 0;
    private boolean followYaw = false;
    private boolean hover = false;
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

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragonShulker.class, new TileEntityDragonShulkerRenderer());
        
        //Override mcmod.info - This looks cooler :)
        TextFormatting g = TextFormatting.GREEN, r = TextFormatting.RESET, a = TextFormatting.AQUA, gd = TextFormatting.GOLD;
        metadata = event.getModMetadata();
        metadata.name = TextFormatting.DARK_AQUA + "Dragon Mounts";
        metadata.credits = "\n" +
        		g + "BarracudaATA4" + r +" - "+ a + "The Original Owner\n\n" +
                g + "Merpou/Kingdomall/Masked_Ares" + r + " - " + a + "more textures much help, First Dev for Dragon Mounts, Overall Second Dev :D Thanks Man... (just found out shes a girl BTW O_O)\n\n" +
        		g + "Shannieanne" + r + " - " + a + "Zombie Textures, Terra textures, Texture Fixes, Overall Second Dev\n\n" +
        		g + "GundunUkan/Lord Ukan" + r + " - " + a + "for new fire texures, sunlight textures, and more.... I Hope he finishes his university hes a hardworking working student\n\n" +
        		g + "Wolf" + r + " - " + a + "Second Coder, started making small fixes then started doing big ones, I hope his dreams of becoming a computer engineer succeeds\n\n" +
        		g + "FlaemWing" + r + " - " + a + "for new nest block textures and dragonarmor item textures, new tool textures\n\n" + 
        		g + "AlexThe666" + r + " - " + a + "for open source code, Ice and Fire owner, Older Matured and more experience than me\n\n" +
        		g + "Majty/Guinea Owl" + r + " - " + a + "for amulet textures\n";
        metadata.authorList = Arrays.asList(StringUtils.split(gd + "TheRpgAdventurer," + gd + "BarracudaATA," + gd + "Kingdomall," + gd + "Shannieanne," + gd + "WolfShotz", ','));
        metadata.description =
        		"\nTips:\n" +
        		"1. Don't forget to right click the egg to start the hatching process\n" +
        		"2. Also water dragon needs to be struck by lightning to become a storm dragon\n" +
        		"3. You can't hatch eggs in the End Dimension\n" +
        		"4. You can press " + TextFormatting.ITALIC + "ctrl" + r + " to enable boost flight\n" +
        		"5. Dragons need to be of opposite genders to breed";
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

    public boolean getDragonFollowYaw() {
        return followYaw;
    }

    public void setDragonFollowYaw(boolean followYaw) {
        this.followYaw = followYaw;
    }

    public int getDragonLockY() {
        return lockY;
    }

    public void setDragonLockY(int lockY) {
        this.followYaw = followYaw;
    }

    @Override
    public boolean getDragonHover() {
        return hover;
    }

    @Override
    public void setDragonHover(boolean hover) {
        this.hover = hover;
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
}
