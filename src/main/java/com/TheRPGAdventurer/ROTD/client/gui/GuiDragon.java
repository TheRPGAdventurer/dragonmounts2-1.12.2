package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inventory.ContainerDragon;
import com.TheRPGAdventurer.ROTD.network.MessageDragonGuiLock;
import com.TheRPGAdventurer.ROTD.network.MessageDragonGuiSit;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiDragon extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(DragonMounts.MODID, "textures/gui/dragon.png");
    private static final ResourceLocation offhand = new ResourceLocation(DragonMounts.MODID, "textures/gui/offhand.png");
    private static final ResourceLocation hunger_empty = new ResourceLocation(DragonMounts.MODID, "textures/gui/hunger_empty.png");
    private static final ResourceLocation hunger_full = new ResourceLocation(DragonMounts.MODID, "textures/gui/hunger_full.png");

    private EntityTameableDragon dragon;
    private float mousePosX;
    private float mousePosY;
    private LockButton lock;
    private GuiButton sit;
    public static ResourceLocation lockOpen;
    public static ResourceLocation lockLocked;
    public static ResourceLocation lockDisabled;
    private EntityPlayer player;

    public GuiDragon(IInventory playerInv, EntityTameableDragon dragon) {
        super(new ContainerDragon(dragon, Minecraft.getMinecraft().player));
        this.player = Minecraft.getMinecraft().player;
        this.dragon = dragon;
        this.allowUserInput = false;
        this.ySize = 214;
        this.xSize = 176;
        lockLocked = new ResourceLocation(DragonMounts.MODID, "textures/gui/lock_2.png");
        lockOpen = new ResourceLocation(DragonMounts.MODID, "textures/gui/lock_1.png");
        lockDisabled = new ResourceLocation(DragonMounts.MODID, "textures/lock_3.png");
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the
     * items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(fontRenderer, dragon.hasCustomName() ? dragon.getCustomNameTag() : "Dragon Inventory", 8, 6, dragon.getBreed().getColor());
        drawString(fontRenderer, dragon.isMale() ? "M" : "FM", 155, 6, dragon.isMale() ? 0x0079be : 0Xff8b8b);
        drawString(fontRenderer, dragon.getHunger() + "/150", 37, 60, 0xe99e0c);
    }

    private void hunger(int x, int y) {
        this.mc.getTextureManager().bindTexture(hunger_full);
        drawModalRectWithCustomSizedTexture(x + 26, y + 60, 0.0F, 0.0F, 9, 9, 9, 9);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if (dragon.isChested()) this.drawTexturedModalRect(x, y + 73, 0, 130, 170, 55);

        hunger(x, y);

/*            // Draw Player Entity
            GuiInventory.drawEntityOnScreen(x - 38, y + 123, 20, x - 38 - this.mousePosX, y + 90 - this.mousePosY, this.player);
            this.mc.getTextureManager().bindTexture(texture1);
            drawModalRectWithCustomSizedTexture(x - 96, y + 78, 0.0F, 0.0F, 99, 51, 99, 51);
        } else {
            GuiInventory.drawEntityOnScreen(x + 90, y + 113, 20, x + 90 - this.mousePosX, y + 90 - this.mousePosY, this.player);
            this.mc.getTextureManager().bindTexture(texture1);
            drawModalRectWithCustomSizedTexture(x + 32, y + 69, 0.0F, 0.0F, 99, 51, 99, 51);
        }

        GuiInventory.drawEntityOnScreen(x + 85, y + 65, (int) (13 / dragon.getScale()), x + 51 - this.mousePosX, y + 75 - 50 - this.mousePosY,
                this.dragon);

        // players poition
        GuiInventory.drawEntityOnScreen(x - 38, y + 123, 20, x + 51 - this.mousePosX, y + 75 - 50 - this.mousePosY,
                this.player);

        // extra different textures for the player and armor
        this.mc.getTextureManager().bindTexture(texture1);
        drawModalRectWithCustomSizedTexture(x - 96, y + 78, 0.0F, 0.0F, 99, 51, 99, 51);
        }
*/
        this.mc.getTextureManager().bindTexture(offhand);
        drawModalRectWithCustomSizedTexture(x - 18, y + 184, 0.0F, 0.0F, 22, 28, 22, 28);

        //draw dragon entity
        GuiInventory.drawEntityOnScreen(x + 90, y + 60, (int) (dragon.isBaby() ? 35 : 7) , x + 90 - this.mousePosX, y + 28 - this.mousePosY, this.dragon);
    }


    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        lock = new LockButton(0, width / 2 + 66, height / 2 - 53, 18, 14, dragon); // I18n.format("gui.allowothers", new Object[0])
        sit = new GuiButton(1, width / 2 + 47, height / 2 - 53, 18, 14, "SIT");

        buttonList.add(lock);
        buttonList.add(sit);
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        boolean sit = button == this.sit;
        boolean lock = button == this.lock;
        if (lock) DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonGuiLock(dragon.getEntityId()));
        if (sit) DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonGuiSit(dragon.getEntityId()));
    }

    public void updateScreen() {
        lock.enabled = (player == dragon.getOwner());
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

        private EntityTameableDragon dragon;

        public LockButton(int buttonId, int x, int y, int i, int j, EntityTameableDragon dragon) {
            super(buttonId, x, y, i, j, "");
            this.dragon = dragon;
        }

        /**
         * Draws this button to the screen.
         */
        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (visible) {
                if (dragon.allowedOtherPlayers()) {
                    mc.getTextureManager().bindTexture(lockOpen);
                } else if (!dragon.allowedOtherPlayers()) {
                    mc.getTextureManager().bindTexture(lockLocked);
                } else if (!enabled) {
                    mc.getTextureManager().bindTexture(lockDisabled);
                }

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }
    }
}