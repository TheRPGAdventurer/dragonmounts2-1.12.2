package com.TheRPGAdventurer.ROTD.client.gui;

import org.fusesource.jansi.Ansi.Color;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.inventory.ContainerDragon;
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
import scala.swing.TextComponent.HasColumns;

@SideOnly(Side.CLIENT)
public class GuiDragon extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(DragonMounts.MODID, "textures/gui/dragon.png");
	private IInventory playerInventory;
	private IInventory dragonInv;
	private EntityTameableDragon dragon;
	private float mousePosX;
	private float mousePosY;
	private GuiButton AllowOthers;

	public GuiDragon(IInventory playerInv, EntityTameableDragon dragon) {
		super(new ContainerDragon(dragon, Minecraft.getMinecraft().player));
		this.playerInventory = playerInv;
		this.dragonInv = dragon.dragonInv;
		this.dragon = dragon;
		this.allowUserInput = false;
		this.ySize = 214;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the
	 * items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		float scale = dragon.getScale();
		this.fontRenderer.drawString(dragon.hasCustomName() ? dragon.getCustomNameTag() : "Dragon Inventory", 8, 6, dragon.getBreed().getColor());
		this.fontRenderer.drawString(dragon.isMale() ? "M" : "FM", 156, 6, dragon.isMale() ?  0x0079be : 0Xff8b8b);
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
	//    this.buttonList.clear();
	//    this.AllowOthers = this.addButton(new GuiButton(0, 100, 100 , 5, 5, "ALLOW OTHERS"));	    
	//    if(this.AllowOthers.enabled && !dragon.allowedOtherPlayers()) {
	//       dragon.setToAllowedOtherPlayers(false);;
	//    } else if(!this.AllowOthers.enabled && dragon.allowedOtherPlayers()) {
	//      dragon.setToAllowedOtherPlayers(true);
	//    }
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