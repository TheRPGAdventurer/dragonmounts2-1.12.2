package com.TheRPGAdventurer.ROTD.client.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.inventory.ContainerDragon;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDragon extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(DragonMounts.MODID, "textures/gui/dragon.png");
	private IInventory playerInventory;
	private IInventory dragonInv;
	private EntityTameableDragon dragon;
	private float mousePosX;
	private float mousePosY;
	private LockButton AllowOthers;
	public static ResourceLocation lockOpen;
	public static ResourceLocation lockLocked;

	public GuiDragon(IInventory playerInv, EntityTameableDragon dragon) {
		super(new ContainerDragon(dragon, Minecraft.getMinecraft().player));
		this.playerInventory = playerInv;
		this.dragonInv = dragon.dragonInv;
		this.dragon = dragon;
		this.allowUserInput = false;
		this.ySize = 214;
		
		lockOpen = new ResourceLocation(DragonMounts.MODID, "textures/gui/lock_1");
		lockLocked = new ResourceLocation(DragonMounts.MODID, "textures/gui/lock_2");
		
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the
	 * items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		float scale = dragon.getScale();
		this.fontRenderer.drawString(dragon.hasCustomName() ? dragon.getCustomNameTag() : "Dragon Inventory", 8, 6, dragon.getBreed().getColor());
		this.fontRenderer.drawString(dragon.isMale() ? "M" : "FM", 160, 6, dragon.isMale() ?  0x0079be : 0Xff8b8b);
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
		GuiInventory.drawEntityOnScreen(x + 80, y + 65, (int) (13 / dragon.getScale()), x + 51 - this.mousePosX, y + 75 - 50 - this.mousePosY,
				this.dragon);
		
  this.buttonList.clear();
  this.AllowOthers = this.addButton(new LockButton(45, 275, 70, 1, 3, I18n.format("gui.allowothers", new Object[0]), dragon));
  buttonList.add(AllowOthers);

	}

	// @Override
//	 public void initGui() {
	//}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == AllowOthers) {
			dragon.setToAllowedOtherPlayers(!dragon.allowedOtherPlayers());
			
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.mousePosX = mouseX;
		this.mousePosY = mouseY;
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	static class LockButton extends GuiButton {
		
		private boolean isLocked;
		private EntityTameableDragon dragon;

		public LockButton(int buttonId, int x, int y, int i, int j, String buttonText, EntityTameableDragon dragon) {
			super(buttonId, x, y, buttonText);
			this.dragon = dragon;
		}
		
		/**
   * Draws this button to the screen.
   */
  @Override
  public void drawButton(Minecraft mc, int parX, int parY, float partialTicks) {
      if (visible) {
          boolean isButtonPressed = (parX >= x 
                && parY >= y 
                && parX < x + width 
                && parY < y + height);
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          
       //   if(dragon.allowedOtherPlayers()) {
      //    mc.getTextureManager().bindTexture(lockOpen);
      //    } else {
          	mc.getTextureManager().bindTexture(lockLocked);
     //     }
          int textureX = 0;
          int textureY = 192;

          if (isButtonPressed)
          {
              textureX += 23;
          }

      //    if (!isNextButton)
       //   {
        //      textureY += 13;
        //  }

          drawTexturedModalRect(x, y, 
                textureX, textureY, 
                23, 13);
   }
  }
	}
}