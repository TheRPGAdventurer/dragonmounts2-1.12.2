package com.TheRPGAdventurer.ROTD.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.inventory.ContainerDragonWand;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.util.math.Interpolation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDragonWand extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(DragonMounts.MODID, "textures/gui/wand.png");
	private static final ResourceLocation gender = new ResourceLocation(DragonMounts.MODID, "textures/gui/gender.png");
	private IInventory playerInventory;
	private IInventory dragonStats;
	private EntityTameableDragon dragon;
	
	private EnumDragonBreed currentBreed;
	private EnumDragonBreed newBreed;
	
	private float mousePosX;
	private float mousePosY;
	
	private GuiTextField genderText;
	
	private GuiButton nextbreed;
	private GuiButton prevbreed;
	
	private GuiButton genderButton;
	
	private GuiButton owner;

	public GuiDragonWand(IInventory playerInv, EntityTameableDragon dragon) {
		super(new ContainerDragonWand(dragon, Minecraft.getMinecraft().player));
		this.playerInventory = playerInv;
		this.dragonStats = dragon.dragonStats;
		this.dragon = dragon;
		this.allowUserInput = false;
		currentBreed = dragon.getBreedType();
		this.ySize = 214;
		
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the
	 * items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(dragon.hasCustomName() ? dragon.getCustomNameTag() : "Dragon Wand", 8, 6, dragon.getBreed().getColor());
		this.mc.getTextureManager().bindTexture(gender);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		if (dragon.isChested()) {
			this.drawTexturedModalRect(x + 0, y + 73, 0, 130, 170, 55);
		}
		GuiInventory.drawEntityOnScreen(x + 88, y + 80, (int) (13 / dragon.getScale()), x + 51 - this.mousePosX, y + 75 - 50 - this.mousePosY,
				this.dragon);
	}
	
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		
		drawEditorGui(); 
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (!button.enabled) {
			return;
		} else if(button == genderButton) {
			if(dragon.isMale()) {
				dragon.setMale(false);
			} else if(!dragon.isMale()) {
				dragon.setMale(true);
			}
			drawEditorGui();
	   } else if(button == nextbreed) {
		   EnumDragonBreed[] values = currentBreed.values();
		   currentBreed = values[(currentBreed.ordinal() + 1) % values.length];
		   dragon.setBreedType(currentBreed);
		   drawEditorGui();
	   } 
	}
	
	private void drawEditorGui() {
		buttonList.clear();
		buttonList.add(genderButton = new GuiButton(0, width / 2 + -5, height / 2 - -1, 20, 20, dragon.isMale() ? "M" : "FM"));
		buttonList.add(nextbreed = new GuiButton(1, width / 2 + 25, height / 2 - -1, 20, 20, dragon.getBreedType().toString()));
	//	buttonList.add(owner = new GuiButton(2, width / 2, height / 2, 10, 20, "OWNER"));
	} 
	
	private EnumDragonBreed cycleThroughBreeds() {
		return newBreed;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.mousePosX = mouseX;
		this.mousePosY = mouseY;
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

}