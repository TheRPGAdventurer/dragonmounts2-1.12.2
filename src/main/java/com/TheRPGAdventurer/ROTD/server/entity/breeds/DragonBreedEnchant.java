package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.item.EntityXPOrb;
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
		
		if(dragon.isSheared()) {
		   if (!world.isRemote && world.getGameRules().getBoolean("doMobLoot")) {
                world.spawnEntity(new EntityXPOrb(world, dragon.posX, dragon.posY, dragon.posZ, 4000));           
		    }
        }
	}
}
