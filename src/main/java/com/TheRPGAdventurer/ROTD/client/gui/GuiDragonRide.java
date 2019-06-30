package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.TheRPGAdventurer.ROTD.util.math.Interpolation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiDragonRide extends Gui {

    Minecraft mc = Minecraft.getMinecraft();
    EntityTameableDragon dragon;

    public GuiDragonRide(EntityTameableDragon dragon) {
        this.dragon = dragon;
    }

    public void renderDragonBoostHotbar() {
        ScaledResolution scaledRes = new ScaledResolution(mc);
        int x = scaledRes.getScaledWidth();
        int k1 = x / 2 - 91;
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
        float f = dragon.boostTicks;
        float j = Interpolation.smoothStep(0,182, f);
                //182 * (int)(-f); // use for texture from the boost
        int k = scaledRes.getScaledHeight() - 32 + 3;
        this.drawTexturedModalRect(k1, k, 0, 84, 182, 5);

        if (f >= 0) {
            this.drawTexturedModalRect(k1, k, 0, 89, (int)j, 5); // 183
        }
    }
}
