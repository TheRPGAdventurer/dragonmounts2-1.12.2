package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.network.MessageDragonFireSupport;
import com.TheRPGAdventurer.ROTD.network.MessageDragonTeleport;
import com.TheRPGAdventurer.ROTD.network.MessageDragonWhistle;
import com.TheRPGAdventurer.ROTD.network.MessageDragonWhistleSit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.UUID;

public class GuiDragonWhistle extends GuiScreen {

    World world;
    ItemStack whistle;
    UUID uuid;

    GuiButton nothing;
    GuiButton circle;
    GuiButton followFlying;
    GuiButton teleport;
    GuiButton sit;
    GuiButton firesupport;

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

        nothing = new GuiButton(0, width / 2 - 50, height / 2 - 60, 98, 20, I18n.format("gui.nothing"));
        sit = new GuiButton(0, width / 2 - 100, height / 2 - 35, 98, 20, I18n.format("gui.sit"));
        teleport = new GuiButton(0, width / 2, height / 2 - 35, 98, 20, I18n.format("gui.teleport"));
        firesupport = new GuiButton(0, width / 2 - 150, height / 2 - 10, 98, 20, TextFormatting.RED + I18n.format("gui.firesupport"));
        circle = new GuiButton(0, width / 2 + 50, height / 2-10, 98, 20, I18n.format("gui.circle"));
        followFlying = new GuiButton(0, width / 2 - 50, height / 2-10, 98, 20, I18n.format("gui.followFlying"));

        buttonList.add(nothing);
        buttonList.add(sit);
        buttonList.add(teleport);
        buttonList.add(firesupport);
        buttonList.add(circle);
        buttonList.add(followFlying);
    }


    //
    private byte getState() {
        return state;
    }

    /* 0 nothing
       1 follow
       2 circle
     */
    private void setStateField(int state, boolean newState) {
        byte prevState = getState();
        if (newState) {
            this.state = (byte) state;
        } else {
            this.state = prevState;
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

    @Override
    protected void actionPerformed(GuiButton button) {
        if (uuid != null) {
            byte previousState = getState();
            nothing(button == nothing);
            follow(button == followFlying);
            circle(button == circle);
            byte controlState = getState();

            if (controlState != previousState)
                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonWhistle(uuid, controlState));

            if (button == firesupport)
                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonFireSupport(uuid));

            if (button == sit)
                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonWhistleSit(uuid));


            if (button == teleport)
                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonTeleport(uuid));

            //Close GUI when option is selected
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
