/*
 ** 2013 October 27
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModSounds;
import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.nodes.BreathNodeP;
import com.TheRPGAdventurer.ROTD.util.ClientServerSynchronisedTickCount;
import com.TheRPGAdventurer.ROTD.util.math.MathX;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import static net.minecraft.entity.SharedMonsterAttributes.*;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonLifeStageHelper extends DragonHelper {

    private static final Logger L = LogManager.getLogger();

    private static final String NBT_TICKS_SINCE_CREATION = "TicksSinceCreation";
    private static final int TICKS_SINCE_CREATION_UPDATE_INTERVAL = 100;
    private static final float EGG_CRACK_THRESHOLD = 0.9f;
    private static final float EGG_WIGGLE_THRESHOLD = 0.75f;
    private static final float EGG_WIGGLE_BASE_CHANCE = 20;

    private DragonLifeStage lifeStagePrev;
    private int eggWiggleX;
    private int eggWiggleZ;

    // the ticks since creation is used to control the dragon's life stage.  It is only updated by the server occasionally.
    // the client keeps a cached copy of it and uses client ticks to interpolate in the gaps.
    // when the watcher is updated from the server, the client will tick it faster or slower to resynchronise
    private final DataParameter<Integer> dataParam;
//    private final Map<EnumDragonBreed, AtomicInteger> breedPoints = new EnumMap<>(EnumDragonBreed.class);
    private int ticksSinceCreationServer;
    private final ClientServerSynchronisedTickCount ticksSinceCreationClient;

    public DragonLifeStageHelper(EntityTameableDragon dragon, DataParameter<Integer> dataParam) {
        super(dragon);

        this.dataParam = dataParam;
        dataWatcher.register(dataParam, ticksSinceCreationServer);

        if (dragon.isClient()) {
            ticksSinceCreationClient = new ClientServerSynchronisedTickCount(TICKS_SINCE_CREATION_UPDATE_INTERVAL);
            ticksSinceCreationClient.reset(ticksSinceCreationServer);
        } else {
            ticksSinceCreationClient = null;
        }
    }

    @Override
    public void applyEntityAttributes() {
        applyScaleModifier(MAX_HEALTH);
        applyScaleModifier(ATTACK_DAMAGE);
        applyScaleModifierArmor(ARMOR);
    }

    private void applyScaleModifier(IAttribute attribute) {
        IAttributeInstance instance = dragon.getEntityAttribute(attribute);
        AttributeModifier oldModifier = instance.getModifier(DragonScaleModifier.ID);
        if (oldModifier != null) {
            instance.removeModifier(oldModifier);
        }
        instance.applyModifier(new DragonScaleModifier(MathX.clamp(getScale(), 0.1, 1)));
    }

    private void applyScaleModifierArmor(IAttribute attribute) {
        IAttributeInstance instance = dragon.getEntityAttribute(attribute);
        AttributeModifier oldModifier = instance.getModifier(DragonScaleModifier.ID);
        if (oldModifier != null) {
            instance.removeModifier(oldModifier);
        }        instance.applyModifier(new DragonScaleModifier(MathX.clamp(getScale(), 0.1, 1.2)));
    }

    /**
     * Generates some egg shell particles and a breaking sound.
     */
    public void playEggCrackEffect() {
        // dragon.world.playEvent(2001, dragon.getPosition(), Block.getIdFromBlock(BlockDragonBreedEgg.DRAGON_BREED_EGG));
        this.playEvent(dragon.getPosition());
    }

    public void playEvent(BlockPos blockPosIn) {
        dragon.world.playSound(null, blockPosIn, ModSounds.DRAGON_HATCHING, SoundCategory.BLOCKS, +1.0F, 1.0F);
    }

    public int getEggWiggleX() {
        return eggWiggleX;
    }

    public int getEggWiggleZ() {
        return eggWiggleZ;
    }

    /**
     * Returns the current life stage of the dragon.
     *
     * @return current life stage
     */
    public DragonLifeStage getLifeStage() {
        int age = getTicksSinceCreation();
        return DragonLifeStage.getLifeStageFromTickCount(age);
    }

//    public DragonLifeStage getLifeStageP() {
//        int age = getTicksSinceCreation();
//        return DragonLifeStage.getLifeStageFromTickCount(age);
//    }

    public int getTicksSinceCreation() {
        if (dragon.isServer()) {
            return ticksSinceCreationServer;
        } else {
            return ticksSinceCreationClient.getCurrentTickCount();
        }
    }

    public void setTicksSinceCreation(int ticksSinceCreation) {
        if (dragon.isServer()) {
            ticksSinceCreationServer = ticksSinceCreation;
        } else {
            ticksSinceCreationClient.updateFromServer(ticksSinceCreationServer);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger(NBT_TICKS_SINCE_CREATION, getTicksSinceCreation());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        int ticksRead = nbt.getInteger(NBT_TICKS_SINCE_CREATION);
        ticksRead = DragonLifeStage.clipTickCountToValid(ticksRead);
        ticksSinceCreationServer = ticksRead;
        dataWatcher.set(dataParam, ticksSinceCreationServer);
    }

    /**
     * Returns the size multiplier for the current age.
     *
     * @return size
     */
    public float getScale() {
        return DragonLifeStage.getScaleFromTickCount(getTicksSinceCreation());
    }

    /**
     * Transforms the dragon to an egg (item form)
     */
    public void transformToEgg() {
        if (dragon.getHealth() <= 0) {
            // no can do
            return;
        }

        L.debug("transforming to egg");

        float volume = 3;
        float pitch = 1;
        dragon.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, volume, pitch);

        if (dragon.isSaddled()) {
            dragon.dropItem(Items.SADDLE, 1);
        }

        dragon.entityDropItem(new ItemStack(BlockDragonBreedEgg.DRAGON_BREED_EGG),
                dragon.getBreedType().getMeta());

        dragon.setDead();
    }

    /**
     * Sets a new life stage for the dragon.
     *
     * @param lifeStage
     */
    public final void setLifeStage(DragonLifeStage lifeStage) {
        L.trace("setLifeStage({})", lifeStage);
        if (dragon.isServer()) {
            ticksSinceCreationServer = lifeStage.getStartTickCount();
            dataWatcher.set(dataParam, ticksSinceCreationServer);
        } else {
            L.error("setLifeStage called on Client");
        }
        updateLifeStage();
    }

    /**
     * Called when the dragon enters a new life stage.
     */
    private void onNewLifeStage(DragonLifeStage lifeStage, DragonLifeStage prevLifeStage) {
        L.trace("onNewLifeStage({},{})", prevLifeStage, lifeStage);

        if (dragon.isClient()) {
            // play particle and sound effects when the dragon hatches
            if (prevLifeStage != null && prevLifeStage.isEgg() && !lifeStage.isBaby()) {
                playEggCrackEffect();
                dragon.world.playSound(dragon.posX, dragon.posY, dragon.posZ, ModSounds.DRAGON_HATCHED, SoundCategory.BLOCKS, 4, 1, false);
            }
        } else {
            // update AI
            dragon.getBrain().updateAITasks();

            // update attribute modifier
            applyEntityAttributes();

            // heal dragon to updated full health
            dragon.setHealth(dragon.getMaxHealth());
        }
    }

    @Override
    public void onLivingUpdate() {
        // if the dragon is not an adult pr paused, update its growth ticks
    	if (dragon.isServer()) {
    		if (!isFullyGrown() && !dragon.isGrowthPaused()) {
    			ticksSinceCreationServer++;
    			if (ticksSinceCreationServer % TICKS_SINCE_CREATION_UPDATE_INTERVAL == 0) dataWatcher.set(dataParam, ticksSinceCreationServer);
    		}
    	} else {
    		ticksSinceCreationClient.updateFromServer(dataWatcher.get(dataParam));
    		if (!isFullyGrown()) ticksSinceCreationClient.tick();
    	}

        updateLifeStage();
        updateEgg();
        updateScale();
    }

    public EntityDataManager getDataWatcher() {
        return dataWatcher;
    }

    private void updateLifeStage() {
        // trigger event when a new life stage was reached
        DragonLifeStage lifeStage = getLifeStage();
        if (lifeStagePrev != lifeStage) {
            onNewLifeStage(lifeStage, lifeStagePrev);
            lifeStagePrev = lifeStage;
        }
    }

    private void updateEgg() {
        if (!isEgg()) {
            return;
        }

        // animate egg wiggle based on the time the eggs take to hatch
        float progress = DragonLifeStage.getStageProgressFromTickCount(getTicksSinceCreation());

        // wait until the egg is nearly hatched
        if (progress > EGG_WIGGLE_THRESHOLD) {
            float wiggleChance = (progress - EGG_WIGGLE_THRESHOLD) / EGG_WIGGLE_BASE_CHANCE * (1 - EGG_WIGGLE_THRESHOLD);

            if (eggWiggleX > 0) {
                eggWiggleX--;
            } else if (rand.nextFloat() < wiggleChance) {
                eggWiggleX = rand.nextBoolean() ? 10 : 20;
                if (progress > EGG_CRACK_THRESHOLD) {
                    playEggCrackEffect();
                }
            }

            if (eggWiggleZ > 0) {
                eggWiggleZ--;
            } else if (rand.nextFloat() < wiggleChance) {
                eggWiggleZ = rand.nextBoolean() ? 10 : 20;
                if (progress > EGG_CRACK_THRESHOLD) {
                    playEggCrackEffect();
                }
            }
        }

        // spawn generic particles
        double px = dragon.posX + (rand.nextDouble() - 0.3);
        double py = dragon.posY + (rand.nextDouble() - 0.3);
        double pz = dragon.posZ + (rand.nextDouble() - 0.3);
        double ox = (rand.nextDouble() - 0.3) * 2;
        double oy = (rand.nextDouble() - 0.3) * 2;
        double oz = (rand.nextDouble() - 0.3) * 2;
        dragon.world.spawnParticle(this.getEggParticle(), px, py, pz, ox, oy, oz);

    }

    protected EnumParticleTypes getEggParticle() {
        switch (dragon.getBreedType()) {
            case END:
                return EnumParticleTypes.PORTAL;
            case NETHER:
                return EnumParticleTypes.DRIP_LAVA;
            //All Eggs without special particles:
            default:
                return EnumParticleTypes.TOWN_AURA;
        }
    }

    private void updateScale() {
        dragon.setScalePublic(getScale());
    }

    @Override
    public void onDeath() {
        if (dragon.isClient() && isEgg()) {
            playEggCrackEffect();
        }
    }

    public boolean isEgg() { return getLifeStage().isEgg(); }

  public boolean isFullyGrown() { return getLifeStage().isFullyGrown();  }
  /**
   * Does this life stage act like a minecraft baby?
   * @return
   */
    public boolean isBaby() {return getLifeStage().isBaby();}

  public boolean isOldEnoughToBreathe() { return getLifeStage().isOldEnoughToBreathe(); }

//    public boolean isHatchling() {
//        return getLifeStage() == HATCHLING;
//    }
//
//    public boolean isInfant() {
//        return getLifeStage() == INFANT;
//    }
//
//    public boolean isPreJuvenile() {
//        return getLifeStage() == PREJUVENILE;
//    }
//
//    public boolean isJuvenile() {
//        return getLifeStage() == JUVENILE;
//    }
//
//    public boolean isAdult() {
//        return getLifeStage() == ADULT;
//    }

//    public boolean isGiga() {
//        return getLifeStage() == GIGA;
//    }
//
//    public boolean isAdjudicator() {
//        return getLifeStage() == ADJUDICATOR;
//    }

  public static final Map<DragonLifeStage, BreathNode.Power> BREATHNODE_POWER_BY_STAGE =
          ImmutableMap.<DragonLifeStage, BreathNode.Power> builder()
                  .put(DragonLifeStage.EGG, BreathNode.Power.SMALL)           // dummy
                  .put(DragonLifeStage.HATCHLING, BreathNode.Power.SMALL)     // dummy
                  .put(DragonLifeStage.INFANT, BreathNode.Power.SMALL)        // dummy
                  .put(DragonLifeStage.PREJUVENILE, BreathNode.Power.SMALL)
                  .put(DragonLifeStage.JUVENILE, BreathNode.Power.MEDIUM)
                  .put(DragonLifeStage.ADULT, BreathNode.Power.LARGE)
                  .build();

  public static final Map<DragonLifeStage, BreathNodeP.Power> BREATHNODEP_POWER_BY_STAGE =
          ImmutableMap.<DragonLifeStage, BreathNodeP.Power> builder()
                  .put(DragonLifeStage.EGG, BreathNodeP.Power.SMALL)           // dummy
                  .put(DragonLifeStage.HATCHLING, BreathNodeP.Power.SMALL)     // dummy
                  .put(DragonLifeStage.INFANT, BreathNodeP.Power.SMALL)        // dummy
                  .put(DragonLifeStage.PREJUVENILE, BreathNodeP.Power.SMALL)
                  .put(DragonLifeStage.JUVENILE, BreathNodeP.Power.MEDIUM)
                  .put(DragonLifeStage.ADULT, BreathNodeP.Power.LARGE)
                  .build();

    public BreathNode.Power getBreathPower() {
      BreathNode.Power power = BREATHNODE_POWER_BY_STAGE.get(getLifeStage());
      if (power == null) {
        DragonMounts.loggerLimit.error_once("Illegal lifestage in getBreathPower():" + getLifeStage());
        power = BreathNode.Power.SMALL;
      }
      return power;
    }

  public BreathNodeP.Power getBreathPowerP() {
    BreathNodeP.Power power = BREATHNODEP_POWER_BY_STAGE.get(getLifeStage());
    if (power == null) {
      DragonMounts.loggerLimit.error_once("Illegal lifestage in getBreathPowerP():" + getLifeStage());
      power = BreathNodeP.Power.SMALL;
    }
    return power;
  }
}