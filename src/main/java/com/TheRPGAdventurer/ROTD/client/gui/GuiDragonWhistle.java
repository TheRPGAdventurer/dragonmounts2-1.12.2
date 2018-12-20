package com.TheRPGAdventurer.ROTD.client.gui;

import java.util.UUID;

import org.lwjgl.input.Keyboard;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonWhistle;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GuiDragonWhistle extends GuiScreen {
	
	EntityTameableDragon dragon = new EntityTameableDragon(Minecraft.getMinecraft().world);
	private final MessageDragonWhistle dcw = new MessageDragonWhistle();
	private float mousePosX;
	private float mousePosY;
	
	ItemStack whistle;
	UUID uuid;
	
	GuiButton nothing;
	GuiButton circle;
	GuiButton followFlying;
	GuiButton come;
	GuiButton sit;
	

	public GuiDragonWhistle(World world, UUID uuid, ItemStack whistle) {
		super();
		this.whistle = whistle;
		if(world instanceof WorldServer) {
	       WorldServer worldServer = (WorldServer) world;
	       dragon = (EntityTameableDragon) worldServer.getEntityFromUuid(uuid);
		}		
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		
		Keyboard.enableRepeatEvents(true);
		
		nothing =      new GuiButton(0, width / 2 - 50, height / 2 + 10, 
	                   98, 20, I18n.format("gui.nothing", new Object[0]));
		
		circle =       new GuiButton(0, width / 2 + 100 - 50, height / 2 + 10, 
	                   98, 20, I18n.format("gui.circle", new Object[0]));
		
		followFlying = new GuiButton(0, width / 2 - 100 - 50, height / 2 + 10, 
	                   98, 20, I18n.format("gui.followFlying", new Object[0]));
		
		come =   new GuiButton(0, width / 2 - 50, height / 2 - 15, 
	                   98, 20, I18n.format("gui.goToPlayer", new Object[0]));
		
		buttonList.add(nothing);
		buttonList.add(circle);
		buttonList.add(followFlying);
		buttonList.add(come);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(dragon != null) {
		   byte previousState = dragon.getWhistleState();
		   dragon.come(button == come);
		   dragon.circle(button == circle);
		   dragon.follow(button == followFlying);
		   dragon.nothing(button == nothing);
		   byte controlState = dragon.getWhistleState();
		   if (controlState != previousState) {
					DragonMounts.NETWORK_WRAPPER
							.sendToServer(new MessageDragonWhistle(dragon.getEntityId(), controlState));
		    }
		} 
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.mousePosX = mouseX;
		this.mousePosY = mouseY;
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
