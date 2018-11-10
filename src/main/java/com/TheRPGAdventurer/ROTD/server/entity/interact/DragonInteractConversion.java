package com.TheRPGAdventurer.ROTD.server.entity.interact;

import java.util.UUID;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class DragonInteractConversion extends DragonInteract {

	public DragonInteractConversion(EntityTameableDragon dragon) {
		super(dragon);
	}

	@Override
	public boolean interact(EntityPlayer player, ItemStack item) {
		if(dragon.getBreedType() == EnumDragonBreed.SKELETON) {
           if (ItemUtils.consumeEquipped(player, Items.ROTTEN_FLESH) && dragon.isPotionActive(MobEffects.STRENGTH)) {
                if (!dragon.world.isRemote) {
                     this.startConverting(dragon.getRNG().nextInt(40) == 0);
                     dragon.playSound(dragon.getSoundManager().getEatSound(), 0.7f, 1.0F);
                     return true;
               }
            }
        } 
		     return false;       
	}
	
    protected void startConverting( boolean sucessful) {
        if(sucessful) {      	
            dragon.setBreedType(EnumDragonBreed.ZOMBIE);
        }
    }

}
