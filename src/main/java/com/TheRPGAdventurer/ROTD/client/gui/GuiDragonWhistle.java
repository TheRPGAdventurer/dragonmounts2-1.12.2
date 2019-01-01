package com.TheRPGAdventurer.ROTD.client.gui;

import java.util.UUID;

import net.minecraft.client.gui.GuiRepair;
import org.lwjgl.input.Keyboard;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonWhistle;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class GuiDragonWhistle extends GuiScreen {

	private final MessageDragonWhistle dcw = new MessageDragonWhistle();
	private float mousePosX;
	private float mousePosY;
	World world;
	
	ItemStack whistle;
	UUID uuid; GuiRepair
	GuiButton nothing;
	GuiButton circle;
	GuiButton followFlying;
	GuiButton come;
	GuiButton sit;

	boolean newState;

	byte state;

	public GuiDragonWhistle(World world, UUID uuid, ItemStack whistle) {
		super();
		this.whistle = whistle;
		this.world = world;
		this.uuid = uuid;
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

	private byte getState() {
		return  state;
	}

    /* 0 nothing
       1 follow
       2 circle
       3 come
     */
	private void setStateField(int state, boolean newState) {
	    byte prevState = getState();
	    if(newState) {
	        this.state = (byte) (prevState | (1 << state));
        } else {
	        this.state = (byte) (prevState & ~(1 << state));
        }
	}

    public void nothing(boolean nothing) {
        setStateField(0, nothing);
    }

    public void follow(boolean follow) {
        setStateField(1, follow);
    }

    public void circle(boolean circle) {
        setStateField(2, circle);
    }

    public void come(boolean come) {
        setStateField(3, come);
    }

	@Override
	protected void actionPerformed(GuiButton button) {
		if(uuid != null) {
		   byte previousState = getState();
		   nothing(button == nothing);
		   follow(button == followFlying);
		   come(button == come);
		   circle(button == circle);
		   this.mc.player.world.playSound(this.mc.player, this.mc.player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 5, 1);
		   byte controlState = getState();
		   if (controlState != previousState) {
               DMUtils.getLogger().info("Current State casted by gui is " + controlState);
               DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonWhistle(uuid, controlState));
		    }
		  //   }
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
