package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.initialization.ModTools;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class DragonBreedEnchant extends DragonBreed {

	DragonBreedEnchant() {
		super("enchant", 0x8359ae);
		
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);
        
        addHabitatBlock(Blocks.BOOKSHELF);
        addHabitatBlock(Blocks.ENCHANTING_TABLE);
	}

	@Override
	public void onEnable(EntityTameableDragon dragon) {
		
	}

	@Override
	public void onDisable(EntityTameableDragon dragon) {
		
	}

	@Override
	public void onDeath(EntityTameableDragon dragon) {
		
	}
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {
		World world = dragon.world;
		if (world instanceof WorldServer && !dragon.isDead && !dragon.isEgg()) {
			((WorldServer) world).spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, (double) dragon.posX,
					(double) dragon.posY + dragon.getEyeHeight() + 0.7, (double) dragon.posZ, 12, 0.5D, 0.25D, 0.5D, 0.0D);
		}
	}
}
