package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inventory.ContainerDragon;
import com.TheRPGAdventurer.ROTD.network.MessageDragonGui;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiDragon extends GuiContainer {

    public static final ResourceLocation lockOpen = new ResourceLocation(DragonMounts.MODID, "textures/gui/lock_1.png");
    public static final ResourceLocation lockLocked = new ResourceLocation(DragonMounts.MODID, "textures/gui/lock_2.png");
    public static final ResourceLocation lockDisabled = new ResourceLocation(DragonMounts.MODID, "textures/lock_3.png");
    private static final ResourceLocation mainGui = new ResourceLocation(DragonMounts.MODID, "textures/gui/dragon.png");
    private static final ResourceLocation offhand = new ResourceLocation(DragonMounts.MODID, "textures/gui/offhand.png");
    private static final ResourceLocation hunger_full = new ResourceLocation(DragonMounts.MODID, "textures/gui/hunger_full.png");
    private static final ResourceLocation dismountTex = new ResourceLocation(DragonMounts.MODID, "textures/items/carriage/carriage_oak.png");
    private EntityTameableDragon dragon;
    private float mousePosX;
    private float mousePosY;
    private LockButton lock;
    //    private GuiButton dismount;
    private GuiButton sit;
    private EntityPlayer player;

    public GuiDragon(IInventory playerInv, EntityTameableDragon dragon) {
        super(new ContainerDragon(dragon, Minecraft.getMinecraft().player));
        this.player = Minecraft.getMinecraft().player;
        this.dragon = dragon;
        this.allowUserInput = false;
        this.ySize = 214;
        this.xSize = 176;
    }

    /**
     * Draws an entity on the screen looking toward the cursor.
     */
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the
     * items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(dragon.hasCustomName() ? dragon.getCustomNameTag() : "Dragon Inventory", 8, 6, dragon.getBreed().getColor());
        this.fontRenderer.drawString(dragon.isMale() ? "M" : "FM", 155, 6, dragon.isMale() ? 0x0079be : 0Xff8b8b);
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.6, 0.6, 0.6);
        this.fontRenderer.drawString(dragon.getHunger() + "/100", 60, 106, 0Xe99e0c);
        GlStateManager.popMatrix();
    }

    private void hunger(int x, int y) {
        this.mc.getTextureManager().bindTexture(hunger_full);
        drawModalRectWithCustomSizedTexture(x + 26, y + 60, 0.0F, 0.0F, 9, 9, 9, 9);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(mainGui);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if (dragon.isChested()) this.drawTexturedModalRect(x, y + 73, 0, 130, 170, 55);

        hunger(x, y);

        this.mc.getTextureManager().bindTexture(offhand);
        drawModalRectWithCustomSizedTexture(x - 18, y + 184, 0.0F, 0.0F, 22, 28, 22, 28);

        int size = 0;
        switch (dragon.getLifeStageHelper().getLifeStage()) {
            case EGG:
                size = 140;
                break;
            case HATCHLING:
                size = 55;
                break;
            case INFANT:
                size = 45;
                break;
            case PREJUVENILE:
                size = 18;
                break;
            case JUVENILE:
                size = 8;
                break;
            case ADULT:
                size = 7;
                break;
        }

        //draw dragon entity
        drawEntityOnScreen(x + 90, y + 60, size, x + 125 - this.mousePosX, y + 28 - this.mousePosY, this.dragon);
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        lock = new LockButton(0, width / 2 + 66, height / 2 - 53, 18, 14, dragon);
        sit = new GuiButton(1, width / 2 + 47, height / 2 - 53, 18, 14, "SIT");

        buttonList.add(lock);
        buttonList.add(sit);
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        boolean sit = button == this.sit;
        boolean lock = button == this.lock;
        if (sit) {
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonGui(dragon.getUniqueID(), 1));
        } else if (lock) {
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonGui(dragon.getUniqueID(), 2));
        }
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