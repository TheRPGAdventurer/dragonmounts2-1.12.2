package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonWhistle;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonTeleport;

import java.util.UUID;

public class GuiDragonAmulet extends GuiScreen {

    private final MessageDragonWhistle dcw = new MessageDragonWhistle();
    private float mousePosX;
    private float mousePosY;
    World world;

    ItemStack amulet;
    UUID uuid;
    GuiButton teleport;


    boolean newState;

    byte state;

    public GuiDragonAmulet(World world, UUID uuid, ItemStack amulet) {
        super();
        this.amulet = amulet;
        this.world = world;
        this.uuid = uuid;
    }

    @Override
    public void initGui() {
        buttonList.clear();

        Keyboard.enableRepeatEvents(true);

        teleport =      new GuiButton(0, width / 2, height / 2,
                98, 20, I18n.format(DragonMounts.MODID + ":gui.teleport"));

        buttonList.add(teleport);
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
            this.state = (byte) state;
        } else {
            this.state = prevState;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(uuid != null) {
            if (button == teleport) DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonTeleport(uuid));
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
