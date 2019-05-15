package com.TheRPGAdventurer.ROTD.datafix;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class DragonMountsItemDataFixes implements IFixableData {

    @Override
    public int getFixVersion() {
        return 1;
    }

    // is the fixTag proper (I think this is the cause tho) derived from PotionItem.class
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
        if ("end_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragon_amulet");
        }
        if ("dragonmounts:nether_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragon_amulet");
        }
        if ("dragonmounts:skeleton_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragonmounts:dragon_amulet");
        }
        if ("dragonmounts:wither_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragonmounts:dragon_amulet");
        }
        if ("forest_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragon_amulet");
        }
        if ("fire_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragonmounts:dragon_amulet");
        }
        if ("dragonmounts:water_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragonmounts:dragon_amulet");
        }
        if ("dragonmounts:sunlight_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragonmounts:dragon_amulet");
        }
        if ("dragonmounts:enchant_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragonmounts:dragon_amulet");
        }
        if ("dragonmounts:terra_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragonmounts:dragon_amulet");
        }
        if ("dragonmounts:moonlight_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragonmounts:dragon_amulet");
        }

        if ("dragonmounts:storm_dragon_amulet".equals(nbttagcompound.getString("id"))) {
            nbttagcompound.setString("id", "dragonmounts:dragon_amulet");
        }

        return compound;
    }
}