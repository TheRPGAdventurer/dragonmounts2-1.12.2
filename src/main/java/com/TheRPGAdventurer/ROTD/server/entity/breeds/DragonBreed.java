package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonEssence;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.client.sound.SoundEffectNames;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breathweapon.FlameBreathFX;
import com.TheRPGAdventurer.ROTD.server.entity.helper.EnumDragonLifeStage;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/**
 * Base class for dragon breeds.
 * 
 */
public abstract class DragonBreed {
    
    private final String skin;
    private final int color;
    private final Set<String> immunities = new HashSet<>();
    private final Set<Block> breedBlocks = new HashSet<>();
    private final Set<Biome> biomes = new HashSet<>();
    protected final Random rand = new Random();
    public static SoundEffectNames [] soundEffectNames;
    
    DragonBreed(String skin, int color) {
        this.skin = skin;
        this.color = color;
        
        // ignore suffocation damage
        setImmunity(DamageSource.DROWN);
        setImmunity(DamageSource.IN_WALL);
        
        setImmunity(DamageSource.ON_FIRE);
        setImmunity(DamageSource.IN_FIRE);
        setImmunity(DamageSource.LAVA);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.HOT_FLOOR);
        
        // assume that cactus needles don't do much damage to animals with horned scales
        setImmunity(DamageSource.CACTUS);        
        
        // ignore damage from vanilla ender dragon
        setImmunity(DamageSource.DRAGON_BREATH); // I kinda disabled this because it would'nt make any sense, feel free to re enable
    }
  
    public String getSkin() {
        return skin;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    public int getColor() {
        return color;
    }
    
    public float getColorR() {
        return ((color >> 16) & 0xFF) / 255f;
    }
    
    public float getColorG() {
        return ((color >> 8) & 0xFF) / 255f;
    }
    
    public float getColorB() {
        return (color & 0xFF) / 255f;
    }
    
    protected final void setImmunity(DamageSource dmg) {
        immunities.add(dmg.damageType);
    }
    
    public boolean isImmuneToDamage(DamageSource dmg) {
        if (immunities.isEmpty()) {
            return false;
        }
        
        return immunities.contains(dmg.damageType);
    }
    
    protected final void setHabitatBlock(Block block) {
        breedBlocks.add(block);
    }
    
    public boolean isHabitatBlock(Block block) {
        return breedBlocks.contains(block);
    }
    
    protected final void setHabitatBiome(Biome biome) {
        biomes.add(biome);
    }
    
    public boolean isHabitatBiome(Biome biome) {
        return biomes.contains(biome);
    }
    
    public boolean isHabitatEnvironment(EntityTameableDragon dragon) {
        return false;
    }
   
    public Item[] getFoodItems() {
        return new Item[] { Items.PORKCHOP, Items.BEEF, Items.CHICKEN, Items.ROTTEN_FLESH, 
        		Items.WHEAT, Items.BEETROOT, Items.RABBIT, Items.CARROT, Items.COOKED_FISH,
        		Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP,
        		Items.COOKED_RABBIT, Items.FISH, Items.COOKED_FISH, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS,
        		Items.MELON_SEEDS, Items.MUTTON, Items.RABBIT_STEW};
    }
    
    public Item getBreedingItem() {
        return Items.FISH;
    }
    
    public void onUpdate(EntityTameableDragon dragon) {
        placeFootprintBlocks(dragon);
    }
    
    protected void placeFootprintBlocks(EntityTameableDragon dragon) {
        // only apply on server
        if (!dragon.isServer()) {
            return;
        }
        
        // only apply on adult dragons that don't fly
        if (!dragon.isAdult() || dragon.isFlying()) {
            return;
        }
        
        // only apply if footprints are enabled
        float footprintChance = getFootprintChance();
        if (footprintChance == 1) {
            return;
        }
        
        // footprint loop, from EntitySnowman.onLivingUpdate with slight tweaks
        World world = dragon.world;
        for (int i = 0; i < 4; i++) {
            // place only if randomly selected
            if (world.rand.nextFloat() > footprintChance) {
                continue;
            }

            // get footprint position
            double bx = dragon.posX + (i % 2 * 2 - 1) * 0.25;
            double by = dragon.posY + 0.5;
            double bz = dragon.posZ + (i / 2 % 2 * 2 - 1) * 0.25;
            BlockPos pos = new BlockPos(bx, by, bz);

            // footprints can only be placed on empty space
            if (world.isAirBlock(pos)) {
                continue;
            }

            placeFootprintBlock(dragon, pos);
        }
    }
    
    protected void placeFootprintBlock(EntityTameableDragon dragon, BlockPos blockPos) {
    
    }
    
    protected float getFootprintChance() {
        return 1;
    }
    
    public abstract void onEnable(EntityTameableDragon dragon);
    
    public abstract void onDisable(EntityTameableDragon dragon);
    
    public abstract void onDeath(EntityTameableDragon dragon);
    
    public SoundEvent getLivingSound() {
        if (rand.nextInt(3) == 0) {
            return ModSounds.ENTITY_DRAGON_GROWL;
        } else {
        	return ModSounds.ENTITY_DRAGON_BREATHE;
        }
    }
    
    public SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_ENDERDRAGON_HURT;
    }
    
    public SoundEvent getDeathSound() {
        return ModSounds.ENTITY_DRAGON_DEATH;
    }
    
    public SoundEvent getWingsSound() {
        return SoundEvents.ENTITY_ENDERDRAGON_FLAP;
    }
    
    public SoundEvent getStepSound() {
        return ModSounds.ENTITY_DRAGON_STEP;
    }
    
    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_GENERIC_EAT;
    }
    
    public SoundEvent getAttackSound() {
        return SoundEvents.ENTITY_GENERIC_EAT;
    }

    public float getSoundPitch(SoundEvent sound) {
        return 1;
    }

    public float getSoundVolume(SoundEvent sound) {
        return 2.5f;
    }
    
    public boolean canChangeBreed() {
    	return true;
    }
    
    public boolean canUseBreathWeapon() {
    	return true;
    }
    
    public boolean useColdSound() {
    	return false;
    }
    
    public Entity getEntityBreath(World world, double x, double y, double z, Vec3d motion,
            BreathNode i_breathNode) {
    	FlameBreathFX FX = new FlameBreathFX(world, z, z, z, motion, i_breathNode);
    	return FX;

    }
    
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {    
    	dragon.getBreathHelper().getBreathAffectedArea().continueBreathing(world, origin, endOfLook, power);
    	dragon.getBreathHelper().getBreathAffectedArea().updateTick(world);
    }
    
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticles(world, power, tickCounter); 
    }
    
    public SoundEffectNames[] getBreathWeaponSoundEffects(EnumDragonLifeStage stage) {
    	final SoundEffectNames hatchling[] = {SoundEffectNames.HATCHLING_BREATHE_FIRE_START,
                SoundEffectNames.HATCHLING_BREATHE_FIRE_LOOP,
                SoundEffectNames.HATCHLING_BREATHE_FIRE_STOP};

        final SoundEffectNames juvenile[] = {SoundEffectNames.JUVENILE_BREATHE_FIRE_START,
                SoundEffectNames.JUVENILE_BREATHE_FIRE_LOOP,
                SoundEffectNames.JUVENILE_BREATHE_FIRE_STOP};

        final SoundEffectNames adult[] = {SoundEffectNames.ADULT_BREATHE_FIRE_START,
            SoundEffectNames.ADULT_BREATHE_FIRE_LOOP,
            SoundEffectNames.ADULT_BREATHE_FIRE_STOP};
    	
    	switch(stage) {
		case ADULT:
			soundEffectNames = adult;
			break;
		case EGG:
			break;
		case HATCHLING:
			soundEffectNames = hatchling;
			break;
		case JUVENILE:
			soundEffectNames = juvenile;       
			break;
		default:
			break;    	
    	}
    	
		return soundEffectNames;
    
   }   
    
    @Nullable
    public ResourceLocation getLootTable(EntityTameableDragon dragon) {
    	return null;
    }

	public void onLivingUpdate(EntityTameableDragon dragon) {	
		
	}
	
	public boolean isInfertile() {
		return false;
	}
	
	@Nullable
	public Item getShearDropitem(EntityTameableDragon dragon) {
		return null;
	}
	
	public SoundEvent getSneezeSound() {
		return ModSounds.DRAGON_SNEEZE;
	}
	
	public EnumParticleTypes getSneezeParticle() {
		return EnumParticleTypes.SMOKE_LARGE;
	}
	
	@Nullable
	public ItemDragonEssence dragonEssence() {
		return ModItems.EssenceEnd;
	}
	
	public double getHealth() {
		return 170;
	}

}

