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

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonInteractEat extends DragonInteract {

    public DragonInteractEat(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean interact(EntityPlayer player, ItemStack item) {
        // eat only if hurt
//        if (dragon.isServer()) {
            if (dragon.getHealthRelative() < 1) {
                ItemFood food = (ItemFood) ItemUtils.consumeEquipped(player,
                        dragon.getBreed().getFoodItems());


                // heal only if the food was actually consumed
                if (food != null) {
                    dragon.heal(6 * dragon.getScale());
                    dragon.playSound(dragon.getSoundManager().getEatSound(), 0.7f, 1);
                    spawnItemCrackParticles(food);
                    return true;
                }
            }

            ItemFood shrinking = (ItemFood) ItemUtils.consumeEquipped(player,
                    dragon.getBreed().getShrinkingFood());
            ItemFood growing = (ItemFood) ItemUtils.consumeEquipped(player,
                    dragon.getBreed().getGrowingFood());

            //.stop growth
            if (shrinking != null) {
                dragon.setGrowthPaused(true);
                dragon.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.7f, 1);
                player.sendStatusMessage(new TextComponentTranslation("dragon.growth.paused"), true);
                return true;
            }
            //.continue growth
            if (growing != null) {
                dragon.setGrowthPaused(false);
                dragon.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.7f, 1);
                return true;
            }
//        }

        return false;
    }

    public void spawnItemCrackParticles(Item item) {
        for (int i = 0; i < 15; i++) {
            double hx, hy, hz;
            double motionX = dragon.getRNG().nextGaussian() * 0.07D;
            double motionY = dragon.getRNG().nextGaussian() * 0.07D;
            double motionZ = dragon.getRNG().nextGaussian() * 0.07D;
            DragonHeadPositionHelper pos = dragon.getAnimator().getDragonHeadPositionHelper();
            boolean isMoving = dragon.motionX != 0 && dragon.motionY != 0 && dragon.motionZ != 0;

            float angle = (((dragon.renderYawOffset + 0) * 3.14159265F) / 180F);
            hx = dragon.posX - MathHelper.sin(angle) * 3.0 - pos.head.rotateAngleX * dragon.getScale();
            double yChange = !isMoving && dragon.isFlying() ? 2.6 : 3.6 * dragon.getScale();
            hy = dragon.posY + 25 * dragon.getScale();
            hz = dragon.posZ + MathHelper.cos(angle) * 3.0 + pos.head.rotateAngleZ * dragon.getScale();

            if (dragon.world.isRemote) {
                dragon.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, hx, hy, hz, motionX, motionY,
                        motionZ, Item.getIdFromItem(item));
            }
        }
    }

}
