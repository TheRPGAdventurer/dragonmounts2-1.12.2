package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.network.MessageDragonSit;
import com.TheRPGAdventurer.ROTD.network.MessageDragonTeleport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.UUID;

public class GuiDragonWhistle extends GuiScreen {

    World world;
    UUID uuid;

    GuiButton teleport;
    GuiButton sit;


    public GuiDragonWhistle(World world, UUID uuid) {
        super();
        this.world = world;
        this.uuid = uuid;
    }

    @Override
    public void initGui() {

        buttonList.clear();

        Keyboard.enableRepeatEvents(true);

        teleport = new GuiButton(0, width / 2-50, height / 2 - 25, 98, 20, I18n.format("gui.cometoPlayer"));
        sit = new GuiButton(0, width / 2 -50, height / 2, 98, 20, I18n.format("gui.sit"));

        buttonList.add(sit);
        buttonList.add(teleport);

    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (uuid != null) {


            if (button == sit)
                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonSit(uuid));

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
