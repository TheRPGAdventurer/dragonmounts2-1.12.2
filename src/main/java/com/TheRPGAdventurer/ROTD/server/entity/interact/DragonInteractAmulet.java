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
        if (ItemUtils.hasEquipped(player, ModItems.AmuletEmpty) && dragon.isServer() && dragon.isTamedFor(player)) {

            ItemStack amulet = new ItemStack(dragon.dragonAmulet());
            if (dragon.hasCustomName()) {
                amulet.setStackDisplayName(dragon.getCustomNameTag());
            }
            amulet.setTagCompound(new NBTTagCompound());
            player.setHeldItem(player.getActiveHand(), amulet);
            dragon.setDead();
            dragon.writeEntityToNBT(amulet.getTagCompound());
//            amulet.getTagCompound().setUniqueId("dragonUUID", dragon.getUniqueID());
            player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1, 0.77f);
            return true;
        }
        return false;
    }
}
