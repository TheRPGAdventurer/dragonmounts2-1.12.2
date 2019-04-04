package com.TheRPGAdventurer.ROTD.server.entity.interact;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;

public class DragonInteractAmulet extends DragonInteract {

    public DragonInteractAmulet(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean interact(EntityPlayer player, ItemStack item) {
        if (ItemUtils.hasEquipped(player, ModItems.AmuletEmpty) && dragon.isServer()) {
            if (dragon.isTamedFor(player)) {
                if (dragon.isUsingBreathWeapon()) dragon.setUsingBreathWeapon(false);
                ItemStack amulet = new ItemStack(dragon.dragonAmulet());
                amulet.setTagCompound(new NBTTagCompound());
                player.setHeldItem(player.getActiveHand(), amulet);
                amulet.getTagCompound().setUniqueId("amuletID", dragon.getUniqueID());
                if (dragon.hasCustomName()) {
                    amulet.setStackDisplayName(dragon.getCustomNameTag());
                }
                dragon.setDead();
                dragon.writeEntityToNBT(amulet.getTagCompound());
                return true;
            } else {
                player.sendStatusMessage(new TextComponentTranslation("item.whistle.notOwned"), true);
            }
            dragon.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1, 1f);


        }
        return false;
    }
}