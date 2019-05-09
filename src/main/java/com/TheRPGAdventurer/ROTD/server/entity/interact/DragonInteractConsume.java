/*
 ** 2016 April 24
 **
 ** The author disclaims copyright to this source code. In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.interact;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonHeadPositionHelper;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * @
 */
public class DragonInteractConsume extends DragonInteract {

    /**
     * Handles dragon taming and healing regarding food consumption
     */
    public DragonInteractConsume(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean interact(EntityPlayer player, ItemStack item) {
        // eat only if hurt
//        if (dragon.isServer()) {
        if (dragon.isServer() && (ItemUtils.consumeFish(player) || ItemUtils.consumeEquippedArray(player, dragon.getBreed().getFoodItems()))) {
            // Taming
            if (!dragon.isTamed()) {
                dragon.tamedFor(player, dragon.getRNG().nextInt(15) == 0);
                eatEvent(player);
            }
            // Healing (if Hurt)
            if (dragon.getHealthRelative() < 1) {
                dragon.heal(6 * dragon.getScale());
                eatEvent(player);
            }
            return true;
        }

        // Stop growth
        ItemFood shrinking = (ItemFood) ItemUtils.consumeEquipped(player, dragon.getBreed().getShrinkingFood());
        if (shrinking != null) {
            dragon.setGrowthPaused(true);
            dragon.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.7f, 1);
            player.sendStatusMessage(new TextComponentTranslation("dragon.growth.paused"), true);
            return true;
        }

        // Continue growth
        ItemFood growing = (ItemFood) ItemUtils.consumeEquipped(player, dragon.getBreed().getGrowingFood());
        if (growing != null) {
            dragon.setGrowthPaused(false);
            dragon.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.7f, 1);
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    private void eatEvent(EntityPlayer player) {
        dragon.playSound(dragon.getSoundManager().getEatSound(), 0.6f, 0.75f);
        spawnItemCrackParticles((ItemFood) ItemUtils.consumeEquipped(player, dragon.getBreed().getFoodItems()));
    }

    @SideOnly(Side.CLIENT)
    private void spawnItemCrackParticles(Item item) {
        for (int i = 0; i < 15; i++) {
            double hx, hy, hz;
            double motionX = dragon.getRNG().nextGaussian() * 0.07D;
            double motionY = dragon.getRNG().nextGaussian() * 0.07D;
            double motionZ = dragon.getRNG().nextGaussian() * 0.07D;
            DragonHeadPositionHelper pos = dragon.getAnimator().getDragonHeadPositionHelper();
            boolean isMoving = dragon.motionX != 0 && dragon.motionY != 0 && dragon.motionZ != 0;

            float angle = (((dragon.renderYawOffset + 0) * 3.14159265F) / 180F);
            hx = dragon.getAnimator().getThroatPosition().x;
            double yChange = !isMoving && dragon.isFlying() ? 2.6 : 3.6 * dragon.getScale();
            hy = dragon.getAnimator().getThroatPosition().y;
            hz = dragon.getAnimator().getThroatPosition().z;

            if (dragon.world.isRemote) {
                dragon.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, hx, hy, hz, motionX, motionY, motionZ, new int[]{Item.getIdFromItem(item)});
            }
        }
    }
}
