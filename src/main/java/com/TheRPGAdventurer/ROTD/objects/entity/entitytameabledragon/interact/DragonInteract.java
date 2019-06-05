/*
 ** 2016 April 24
 **
 ** The author disclaims copyright to this source code. In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.interact;

import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.DragonBreed;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonInteract extends DragonInteractBase {

    public DragonInteract(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean interact(EntityPlayer player, ItemStack item) {
        if (dragon.isServer()) {
            if (isAllowed(player)) {

                /*
                 * Riding
                 */
                if (dragon.isTamed() && dragon.isSaddled() && (dragon.isAdult() || dragon.isJuvenile()) && !player.isSneaking() && !hasInteractItemsEquipped(player)) {
                    dragon.setRidingPlayer(player);
                    return true;
                }



                /*
                 * GUI
                 */
                if (player.isSneaking() && dragon.isTamedFor(player) && !DMUtils.hasEquipped(player, ModItems.dragon_whistle)) {
                    // Dragon Inventory
                    if (!dragon.isHatchling()) {
                        dragon.openGUI(player, GuiHandler.GUI_DRAGON);
                        return true;
                    } else player.sendStatusMessage(new TextComponentTranslation("entity.dragon.tooYoung"), true);
                    // Wand Gui
/*        		if (DMUtils.hasEquipped(player, ModItems.dragon_wand)) {
	            		dragon.openGUI(player, GuiHandler.GUI_DRAGON_WAND);
	            		return true;
	            	}
*/
                }
            }



            /*
             * Sit
             */
            if (dragon.isTamed() && (DMUtils.hasEquipped(player, Items.STICK) || DMUtils.hasEquipped(player, Items.BONE)) && dragon.onGround) {
                dragon.getAISit().setSitting(!dragon.isSitting());
                dragon.getNavigator().clearPathEntity();
                return true;
            }



            /*
             * Consume
             */
            if (DMUtils.hasEquippedFood(player)) {
				if (DMUtils.consumeFish(player) || DMUtils.consumeEquippedArray(player, DragonBreed.getFoodItems())) {
                    // Taming
                    if (!dragon.isTamed()) {
                        dragon.tamedFor(player, dragon.getRNG().nextInt(15)==0);
                        eatEvent(player);
                    }
                    // Healing (if Hurt)
                    if (dragon.getHealthRelative() < 1) {
                        dragon.heal(22F / dragon.getScale());
                        eatEvent(player);
                    }

                    // breed
                    if (dragon.isBreedingItem(item) && dragon.isAdult() && !dragon.isInLove()) {
                        eatEvent(player);
                        dragon.setInLove(player);
                    }
                    return true;
                }

                // Stop Growth
                ItemFood shrinking=(ItemFood) DMUtils.consumeEquipped(player, dragon.getBreed().getShrinkingFood());
                if (shrinking!=null) {
                    dragon.setGrowthPaused(true);
                    eatEvent(player);
                    player.sendStatusMessage(new TextComponentTranslation("dragon.growth.paused"), true);
                    return true;
                }
                // Continue growth
                ItemFood growing=(ItemFood) DMUtils.consumeEquipped(player, dragon.getBreed().getGrowingFood());
                if (growing!=null) {
                    dragon.setGrowthPaused(false);
                    eatEvent(player);
                    return true;
                }
            }
        }
        return false;
    }

    private void eatEvent(EntityPlayer player) {
        dragon.playSound(dragon.getEatSound(), 0.6f, 0.75f);
		spawnItemCrackParticles((ItemFood) DMUtils.consumeEquipped(player, DragonBreed.getFoodItems()));
    }

    private void spawnItemCrackParticles(Item item) {
        for (int i=0; i < 15; i++) {
            double motionX=dragon.getRNG().nextGaussian() * 0.07D;
            double motionY=dragon.getRNG().nextGaussian() * 0.07D;
            double motionZ=dragon.getRNG().nextGaussian() * 0.07D;
            Vec3d pos=dragon.getAnimator().getThroatPosition();
            double hx=pos.x;
            double hy=pos.y;
            double hz=pos.z;
            // Spawn calculated particles
            dragon.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, hx, hy, hz, motionX, motionY, motionZ, new int[]{Item.getIdFromItem(item)});
        }
    }
}    
