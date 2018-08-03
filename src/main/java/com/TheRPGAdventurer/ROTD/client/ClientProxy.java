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

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.client.event.DragonViewEvent;
import com.TheRPGAdventurer.ROTD.client.gui.GuiDragonDebug;
import com.TheRPGAdventurer.ROTD.client.handler.DragonEntityWatcher;
import com.TheRPGAdventurer.ROTD.client.initialization.ModKeys;
import com.TheRPGAdventurer.ROTD.client.render.DragonRenderer;
import com.TheRPGAdventurer.ROTD.client.render.breathweaponFX.RenderEnderBreathFX;
import com.TheRPGAdventurer.ROTD.client.render.breathweaponFX.RenderFlameBreathFX;
import com.TheRPGAdventurer.ROTD.client.render.breathweaponFX.RenderIceBreathFX;
import com.TheRPGAdventurer.ROTD.client.render.breathweaponFX.RenderNetherBreathFX;
import com.TheRPGAdventurer.ROTD.client.render.breathweaponFX.RenderWitherBreathFX;
import com.TheRPGAdventurer.ROTD.server.ServerProxy;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breathweapon.EnderBreathFX;
import com.TheRPGAdventurer.ROTD.server.entity.breathweapon.FlameBreathFX;
import com.TheRPGAdventurer.ROTD.server.entity.breathweapon.IceBreathFX;
import com.TheRPGAdventurer.ROTD.server.entity.breathweapon.NetherBreathFX;
import com.TheRPGAdventurer.ROTD.server.entity.breathweapon.WitherBreathFX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * 2nd @author TheRPGAdventurer
 */
public class ClientProxy extends ServerProxy {

    @Override
    public void PreInitialization(FMLPreInitializationEvent event) {
        super.PreInitialization(event);        
        // register dragon entity renderer
        DragonMountsConfig.clientPreInit();
        MinecraftForge.EVENT_BUS.register(new DragonEntityWatcher());
        RenderingRegistry.registerEntityRenderingHandler(EntityTameableDragon.class, DragonRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(FlameBreathFX.class, RenderFlameBreathFX::new);
		RenderingRegistry.registerEntityRenderingHandler(EnderBreathFX.class, RenderEnderBreathFX::new);
		RenderingRegistry.registerEntityRenderingHandler(NetherBreathFX.class, RenderNetherBreathFX::new);
		RenderingRegistry.registerEntityRenderingHandler(WitherBreathFX.class, RenderWitherBreathFX::new);
		RenderingRegistry.registerEntityRenderingHandler(IceBreathFX.class, RenderIceBreathFX::new);
      
    }

    @Override
    public void Initialization(FMLInitializationEvent evt) {
        super.Initialization(evt);
    }

    @Override
    public void PostInitialization(FMLPostInitializationEvent event) {
        super.PostInitialization(event);
        
        if (DragonMountsConfig.isDebug()) { MinecraftForge.EVENT_BUS.register(new GuiDragonDebug());}            
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        MinecraftForge.EVENT_BUS.register(new DragonViewEvent());

    }
    
    @SideOnly(Side.CLIENT)
	@Override
	public void render() {ModKeys.init();}
}
