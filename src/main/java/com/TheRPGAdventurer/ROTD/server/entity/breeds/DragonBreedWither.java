package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathAffectedArea;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class DragonBreedWither extends DragonBreed {
	
	public BreathAffectedArea breathAffectedAreaWither;

    DragonBreedWither() {
        super("wither", 0x50260a);
        
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);
        
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {}

    @Override
    public void onDisable(EntityTameableDragon dragon) {}

    @Override
    public void onDeath(EntityTameableDragon dragon) {}
    
    @Override
    public SoundEvent getLivingSound() {
        if (rand.nextInt(3) == 0) {
            return SoundEvents.ENTITY_SKELETON_AMBIENT;
        } else {
        	return ModSounds.ENTITY_SKELETON_DRAGON_GROWL;
        }
    }
    
	@Override
	public boolean canChangeBreed() {
		return false;
	}
	
	@Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {     
        dragon.getBreathHelper().getbreathAffectedAreaWither().continueBreathing(world, origin, endOfLook, power);
        dragon.getBreathHelper().getbreathAffectedAreaWither().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforWitherDragon(world, power, tickCounter);
    }
	
	@Override
	public ResourceLocation getLootTable() {
		return DragonMountsLootTables.ENTITIES_DRAGON_SKELETON;
	}
    
}
	
