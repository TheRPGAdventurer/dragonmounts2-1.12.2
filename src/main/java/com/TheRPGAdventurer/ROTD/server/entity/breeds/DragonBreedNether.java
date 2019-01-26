package com.TheRPGAdventurer.ROTD.server.entity.breeds;


import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonEssence;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;


public class DragonBreedNether extends DragonBreed {

    DragonBreedNether() {
        super("nether", 0xe5b81b);
        
        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);          
        
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {
        dragon.getBrain().setAvoidsWater(true);
    }

    @Override
    public void onDisable(EntityTameableDragon dragon) {
        dragon.getBrain().setAvoidsWater(false);
   }

	@Override
	public void onDeath(EntityTameableDragon dragon) {
		
	}
    
    @Override
    public SoundEvent getLivingSound() {
        if (rand.nextInt(3) == 0) {
            return ModSounds.ENTITY_NETHER_DRAGON_GROWL;
        } else {
            return ModSounds.ENTITY_NETHER_DRAGON_GROWL;
        }
    }
    
	@Override
	public boolean canChangeBreed() {
		return false;
	}
	
	@Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
		dragon.getBreathHelper().getBreathAffectedAreaNether().continueBreathing(world, origin, endOfLook, power);
		dragon.getBreathHelper().getBreathAffectedAreaNether().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforNetherDragon(world, power, tickCounter);
    }
	
	@Override
	public ResourceLocation getLootTable(EntityTameableDragon dragon) {
		return dragon.isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_NETHER : DragonMountsLootTables.ENTITIES_DRAGON_NETHER2;
	}
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {
		World world = dragon.world;
		if (world instanceof WorldServer && dragon.isWet() &&  !dragon.isEgg()) {
			((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) dragon.posX + 0.5D,
					(double) dragon.posY + dragon.getEyeHeight(), (double) dragon.posZ + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);
		}
		
		if (world instanceof WorldServer && !dragon.isDead && !dragon.isEgg()) {
			((WorldServer) world).spawnParticle(EnumParticleTypes.DRIP_LAVA, (double) dragon.posX,
					(double) dragon.posY + dragon.getEyeHeight(), (double) dragon.posZ, 1, 0.5D, 0.25D, 0.5D, 0.0D);
		}
	}
	
	@Override
	public boolean isInfertile() {
		return true;
	}
	
	@Override
	public Item getShearDropitem(EntityTameableDragon dragon) {		
		return dragon.isMale() ? ModItems.NetherDragonScales : ModItems.NetherDragonScales2;
	}

	@Override
	public double getHealth() {
		return 190;
	}
    
}
