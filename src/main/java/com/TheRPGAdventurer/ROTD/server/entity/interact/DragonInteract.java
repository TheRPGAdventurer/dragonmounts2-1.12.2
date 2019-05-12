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

import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.initialization.ModTools;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
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
    		
    		/*
    		 * Riding
    		 */
    		if (dragon.isTamed() && dragon.isSaddled() && !player.isSneaking() && !hasInteractItemsEquipped(player)) {
    			dragon.setRidingPlayer(player);
    			return true;
    		}
    		
    		
    		
    		/*
    		 * GUI
    		 */
    		if (player.isSneaking() && dragon.isTamed() /*&& isAllowed(player)*/) {
    			// Dragon Inventory
    			if (!dragon.isHatchling()) {
    				dragon.openGUI(player, GuiHandler.GUI_DRAGON);
    				return true;
    			} else	player.sendStatusMessage(new TextComponentTranslation("entity.dragon.tooYoung"), true);
    			// Wand Gui
/*        		if (ItemUtils.hasEquipped(player, ModItems.dragon_wand)) {
            		dragon.openGUI(player, GuiHandler.GUI_DRAGON_WAND);
            		return true;
            	}
*/
    		}
    		
    		
    		
    		/*
    		 * Sit
    		 */
    		if (dragon.isTamed() && (ItemUtils.hasEquipped(player, Items.STICK) || ItemUtils.hasEquipped(player, Items.BONE)) && dragon.onGround) {
    			dragon.getAISit().setSitting(!dragon.isSitting());
    			dragon.getNavigator().clearPathEntity();
    			return true;
    		}
    		
    		
    		
    		/*
    		 * Consume
    		 */
    		if (ItemUtils.hasEquippedFood(player)) {
    	        if (ItemUtils.consumeFish(player) || ItemUtils.consumeEquippedArray(player, dragon.getBreed().getFoodItems())) {
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
    	        // Stop Growth
    	    	ItemFood shrinking = (ItemFood) ItemUtils.consumeEquipped(player, dragon.getBreed().getShrinkingFood());
    	    	if (shrinking != null) {
    	    		dragon.setGrowthPaused(true);
    	    		eatEvent(player);
    	    		player.sendStatusMessage(new TextComponentTranslation("dragon.growth.paused"), true);
    	    		return true;
    	    	}
    	    	// Continue growth
    	    	ItemFood growing = (ItemFood) ItemUtils.consumeEquipped(player, dragon.getBreed().getGrowingFood());
    	    	if (growing != null) {
    	    		dragon.setGrowthPaused(false);
    	    		eatEvent(player);
    	    		return true;
    	    	}
    		}
    	}
		return false;
    }
    
    private void eatEvent(EntityPlayer player) {
        dragon.playSound(dragon.getSoundManager().getEatSound(), 0.6f, 0.75f);
        spawnItemCrackParticles((ItemFood) ItemUtils.consumeEquipped(player, dragon.getBreed().getFoodItems()));
    }
    
    private void spawnItemCrackParticles(Item item) {
    	for (int i = 0; i < 15; i++) {
    		double motionX = dragon.getRNG().nextGaussian() * 0.07D;
    		double motionY = dragon.getRNG().nextGaussian() * 0.07D;
    		double motionZ = dragon.getRNG().nextGaussian() * 0.07D;
    		Vec3d pos = dragon.getAnimator().getThroatPosition();
    		boolean isMoving = dragon.motionX != 0 && dragon.motionY != 0 && dragon.motionZ != 0;
    		float angle = (((dragon.renderYawOffset + 0) * (float) Math.PI) / 180F);
    		double hx = pos.x;
    		double yChange = !isMoving && dragon.isFlying() ? 2.6 : 3.6 * dragon.getScale();
    		double hy = pos.y;
    		double hz = pos.z;
    		// Spawn calculated particles
    		dragon.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, hx, hy, hz, motionX, motionY, motionZ, new int[]{Item.getIdFromItem(item)});
            }
        }
}    
