package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.inventory.ContainerDragonWand;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDragonWand extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(DragonMounts.MODID, "textures/gui/dragon.png");
	private static final ResourceLocation gender = new ResourceLocation(DragonMounts.MODID, "textures/gui/gender.png");
	private IInventory playerInventory;
	private IInventory dragonStats;
	private EntityTameableDragon dragon;
	private float mousePosX;
	private float mousePosY;
	private GuiButton SIT;

	public GuiDragonWand(IInventory playerInv, EntityTameableDragon dragon) {
		super(new ContainerDragonWand(dragon, Minecraft.getMinecraft().player));
		this.playerInventory = playerInv;
		this.dragonStats = dragon.dragonStats;
		this.dragon = dragon;
		this.allowUserInput = false;
		this.ySize = 214;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the
	 * items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(dragon.hasCustomName() ? dragon.getCustomNameTag() : "Dragon Wand", 8, 6, dragon.getBreed().getColor());
		this.fontRenderer.drawString(dragon.isMale() ? "M" : "FM", 156, 6, dragon.isMale() ?  0x0079be : 0Xff8b8b);
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
		GuiInventory.drawEntityOnScreen(x + 88, y + 65, (int) (13 / dragon.getScale()), x + 51 - this.mousePosX, y + 75 - 50 - this.mousePosY,
				this.dragon);

	}

	// @Override
	// public void initGui() {
	// this.buttonList.clear();
	// this.SIT = this.addButton(new GuiButton(0, 100, 100 , 5, 5, "SIT!"));
	// if(this.SIT.enabled && !dragon.isSitting()) {
	// dragon.getAISit().setSitting(true);
	// } else if(!this.SIT.enabled && dragon.isSitting()) {
	// dragon.getAISit().setSitting(false);
	// }
	// }

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.mousePosX = mouseX;
		this.mousePosY = mouseY;
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

}