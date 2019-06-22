package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.network.MessageDragonTeleport;
import com.TheRPGAdventurer.ROTD.network.MessageDragonWhistle;
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
    GuiButton come;
    GuiButton sit;
    GuiButton homePos;
    GuiButton firesupport;

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

        nothing = new GuiButton(0, width / 2 - 50, height / 2 - 60, 98, 20, I18n.format("gui.nothing"));

        circle = new GuiButton(0, width / 2, height / 2 + 15, 98, 20, I18n.format("gui.circle"));

        followFlying = new GuiButton(0, width / 2 - 100, height / 2 + 15, 98, 20, I18n.format("gui.followFlying"));

        come = new GuiButton(0, width / 2 - 50, height / 2 - 10, 98, 20, I18n.format("gui.goToPlayer"));

        homePos = new GuiButton(0, width / 2, height / 2 - 35, 98, 20, I18n.format("gui.homePos"));

//        firesupport=new GuiButton(0, width / 2 - 150, height / 2 - 35, 98, 20, I18n.format("gui.firesupport"));
        firesupport = new GuiButton(0, width / 2 - 150, height / 2 - 10, 98, 20, I18n.format(TextFormatting.RED + "gui.firesupport"));

        sit = new GuiButton(0, width / 2 - 100, height / 2 - 35, 98, 20, I18n.format("gui.sit"));

        buttonList.add(nothing);
        buttonList.add(circle);
        buttonList.add(followFlying);
        buttonList.add(come);
        buttonList.add(homePos);
        buttonList.add(sit);
        buttonList.add(firesupport);
    }


    //
    private byte getState() {
        return state;
    }

    /* 0 nothing
       1 follow
       2 circle
       3 come
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

    public void come(boolean come) {
        setStateField(3, come);
    }

    public void homepos(boolean come) {
        setStateField(4, come);
    }

    public void sit(boolean sit) {
        setStateField(5, sit);
    }


    @Override
    protected void actionPerformed(GuiButton button) {
        if (uuid != null) {
            byte previousState = getState();
            nothing(button == nothing);
            follow(button == followFlying);
            come(button == come);
            circle(button == circle);
            sit(button == sit);
            byte controlState = getState();

            if (controlState != previousState) {
                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonWhistle(uuid, controlState, button == firesupport));
            }

            if (button == homePos) {
                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonTeleport(uuid));
            }
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
