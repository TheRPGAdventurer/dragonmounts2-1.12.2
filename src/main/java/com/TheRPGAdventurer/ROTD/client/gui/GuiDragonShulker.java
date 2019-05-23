package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inventory.ContainerDragonShulker;
import com.TheRPGAdventurer.ROTD.objects.tileentities.TileEntityDragonShulker;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiDragonShulker extends GuiContainer {
    private static final ResourceLocation GUI_DRAGON_SHULKER=new ResourceLocation(DragonMounts.MODID + ":textures/gui/dragon_core.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityDragonShulker te;

    public GuiDragonShulker(InventoryPlayer playerInventory, TileEntityDragonShulker shulkerInventory, EntityPlayer player) {
        super(new ContainerDragonShulker(playerInventory, shulkerInventory, player));
        this.playerInventory=playerInventory;
        this.te=shulkerInventory;

        this.xSize=175;
        this.ySize=165;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(this.te.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96, 4210742);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GUI_DRAGON_SHULKER);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }


}
