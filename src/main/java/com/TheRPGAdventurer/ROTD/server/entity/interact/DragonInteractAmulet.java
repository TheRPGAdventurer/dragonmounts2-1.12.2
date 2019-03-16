package com.TheRPGAdventurer.ROTD.server.entity.interact;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DragonInteractAmulet extends DragonInteract {

    public DragonInteractAmulet(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean interact(EntityPlayer player, ItemStack item) {
        if (ItemUtils.hasEquipped(player, ModItems.AmuletEmpty) && dragon.isServer()) {
            player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            ItemStack amulet = new ItemStack(dragon.dragonAmulet());
            amulet.setTagCompound(new NBTTagCompound());
            player.setHeldItem(player.getActiveHand(), amulet);
            dragon.setDead();
            dragon.writeEntityToNBT(amulet.getTagCompound());
            return true;
        }
        return false;
    }
}
