package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiDragonRide extends Gui {

    Minecraft mc = Minecraft.getMinecraft();
    EntityTameableDragon dragon;

    public GuiDragonRide(EntityTameableDragon dragon) {
        this.dragon = dragon;
        ScaledResolution scaled = new ScaledResolution(mc);
        int i = scaled.getScaledWidth();
        int j = scaled.getScaledHeight();
        int k1 = i / 2 - 91;
        renderDragonBoostHotbar(scaled, k1);
    }

    public void renderDragonBoostHotbar(ScaledResolution scaledRes, int x) {
        if (mc.gameSettings.thirdPersonView == 0) {
            this.mc.getTextureManager().bindTexture(Gui.ICONS);
            float f = dragon.boostTicks;
            int i = 182;
            int j = (int) (f * 183.0F);
            int k = scaledRes.getScaledHeight() - 32 + 3;
            this.drawTexturedModalRect(x, k, 0, 84, 182, 5);

            if (j > 0) {
                this.drawTexturedModalRect(x, k, 0, 89, j, 5);
            }
        }
    }
}
