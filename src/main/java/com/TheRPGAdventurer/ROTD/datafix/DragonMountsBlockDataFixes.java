package com.TheRPGAdventurer.ROTD.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class DragonMountsBlockDataFixes implements IFixableData {


    public DragonMountsBlockDataFixes() {
    }

    @Override
    public int getFixVersion() {
        return 1;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        if ("dragonmounts:dragon_egg.1".equals(compound.getString("id"))) {
            compound.setString("id", "dragonmounts:fire_dragonEgg");
        }

        return compound;
    }

}