package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonEssence;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class DragonBreedEnchant extends DragonBreed {

	DragonBreedEnchant() {
		super("enchant", 0x8359ae);
		
        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);
        
        setHabitatBlock(Blocks.BOOKSHELF);
        setHabitatBlock(Blocks.ENCHANTING_TABLE);
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
	
	@Override
	public ResourceLocation getLootTable(EntityTameableDragon dragon) {
		return DragonMountsLootTables.ENTITIES_DRAGON_ENCHANT;
	}
	
    @Override
    public Item getShearDropitem(EntityTameableDragon dragon) {    	
    	return ModItems.EnchantDragonScales;
    }
}
