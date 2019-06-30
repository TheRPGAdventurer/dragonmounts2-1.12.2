package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.render.dragon.breathweaponFX.BreathWeaponFXEmitter;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.nodes.BreathNodeFactory;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.nodes.BreathNodeP;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.nodes.BreathProjectileFactory;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.sound.SoundController;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.sound.SoundEffectBreathWeaponP;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.weapons.BreathWeaponP;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.DragonBreed;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.DragonHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by TGG on 8/07/2015.
 * Responsible for
 * - retrieving the player's selected target (based on player's input from Dragon Orb item)
 * - synchronising the player-selected target between server AI and client copy - using datawatcher
 * - rendering the breath weapon on the client
 * - performing the effects of the weapon on the server (eg burning blocks, causing damage)
 * The selection of an actual target (typically - based on the player desired target), navigation of dragon to the appropriate range,
 *   turning the dragon to face the target, is done by targeting AI.
 * DragonBreathHelper is also responsible for
 *  - tracking the current breath state (IDLE, STARTING, SUSTAINED BREATHING, STOPPING)
 *  - sound effects
 *  - adding delays for jaw open / breathing start
 *  - interrupting the beam when the dragon is facing the wrong way / the angle of the beam mismatches the head angle
 *  Usage:
 *  1) Create instance, providing the parent dragon entity and a datawatcher index to use for breathing
 *  2) call onLivingUpdate(), onDeath(), onDeathUpdate(), readFromNBT() and writeFromNBT() from the corresponding
 *     parent entity methods
 *  3a) The AI task responsible for targeting should call getPlayerSelectedTarget() to find out what the player wants
 *     the dragon to target.
 *  3b) Once the target is in range and the dragon is facing the correct direction, the AI should use setBreathingTarget()
 *      to commence breathing at the target
 *  4) getCurrentBreathState() and getBreathStateFractionComplete() should be called by animation routines for
 *     the dragon during breath weapon (eg jaw opening)
 */
public class DragonBreathHelperP extends DragonHelper
{
  public DragonBreathHelperP(EntityTameableDragon dragon, DataParameter<String> i_dataParamBreathWeaponTarget,
                                DataParameter<Integer> i_dataParamBreathWeaponMode) {
    super(dragon);
    dataParamBreathWeaponTarget = i_dataParamBreathWeaponTarget;
    dataParamBreathWeaponMode = i_dataParamBreathWeaponMode;
    refreshBreed(dragon);
  }

  private DataParameter<String> dataParamBreathWeaponTarget;
  private DataParameter<Integer> dataParamBreathWeaponMode;

  // changes the breath weapon after the breed is changed
  public void refreshBreed(EntityTameableDragon dragon)
  {
    DragonBreed newBreed = dragon.getBreed();
    if (currentBreed == newBreed) return;
    currentBreed = newBreed;

    switch (currentBreed.getBreathWeaponSpawnType(dragon)) {
      case NODES: {
        BreathWeaponP breathWeapon = currentBreed.getBreathWeapon(dragon);
        breathAffectedArea = new BreathAffectedArea(breathWeapon);
        breathNodeFactory = currentBreed.getBreathNodeFactory(dragon);
        if (dragon.isClient()) {
          breathWeaponFXEmitter = currentBreed.getBreathWeaponFXEmitter(dragon);
        }
        break;
      }
      case PROJECTILE: {
        breathProjectileFactory = currentBreed.getBreathProjectileFactory(dragon);
        break;
      }
      default: {
        DragonMounts.loggerLimit.error_once(
                "Unknown BreathWeaponSpawnType:" + dragon.getBreed().getBreathWeaponSpawnType(dragon));
        return;
      }
    }
    if (dragon.isClient()) {
      refreshBreedClientOnly(dragon);
    }

  }

  public void refreshBreedClientOnly(EntityTameableDragon dragon)
  {
    soundEffectBreathWeapon = dragon.getBreed().getSoundEffectBreathWeapon(getSoundController(dragon.getEntityWorld()), weaponInfoLink);
  }


  public enum  BreathState {
    IDLE, STARTING, SUSTAIN, STOPPING
    }

  public BreathState getCurrentBreathState() {return currentBreathState;}

  public float getBreathStateFractionComplete() {
    switch (currentBreathState) {
      case IDLE: {
        return 0.0F;
      }
      case STARTING: {
        int ticksSpentStarting = tickCounter - transitionStartTick;
        return MathHelper.clamp(ticksSpentStarting / (float) BREATH_START_DURATION, 0.0F, 1.0F);
      }
      case SUSTAIN: {
        return 0.0F;
      }
      case STOPPING: {
        int ticksSpentStopping = tickCounter - transitionStartTick;
        return MathHelper.clamp(ticksSpentStopping / (float) BREATH_STOP_DURATION, 0.0F, 1.0F);
      }
      default: {
        DragonMounts.loggerLimit.error_once("Unknown currentBreathState:" + currentBreathState);
        return 0.0F;
      }
    }
  }

  /** set the target currently being breathed at.
   * server only.
   * @param target the new target the dragon is breathing at, null = no target
    */
  public void setBreathingTarget(BreathWeaponTarget target)
  {
    if (dragon.isServer()) {
      targetBeingBreathedAt = target;
      boolean updateDataWatcher = false;
      if (lastBreathTargetSent == null) {
        updateDataWatcher = true;
      } else {
        updateDataWatcher = !lastBreathTargetSent.approximatelyMatches(target);
      }
      if (updateDataWatcher) {
        lastBreathTargetSent = target;
        if (target == null) {
          dataWatcher.set(dataParamBreathWeaponTarget, "");
        } else {
          dataWatcher.set(dataParamBreathWeaponTarget, target.toEncodedString());
        }
      }
    } else {
      L.warn("setBreathingTarget is only valid on server");
    }

    updateBreathState(target);
  }

  /** gets the target that the movement AI should move towards (or away from) to move to the optimal breathing distance
  */
   public BreathWeaponTarget getBreathTargetForMoving()
  {
    return breathWeaponTarget;
  }

  /** sets the target that the movement AI should move towards (or away from) to move to the optimal breathing distance
   * @param targetForMoving the new target - NULL for no target
   */
  public void setBreathTargetForMoving(BreathWeaponTarget targetForMoving)
  {
    breathWeaponTarget = targetForMoving;
  }

  /**
   * check if the dragon has a breath target that it should move towards (or away from)
   * @return true if the dragon has a movement target
   */
  public boolean hasBreathTargetForMoving() { return breathWeaponTarget != null;}

  /**
   * Get the current mode of the breath weapon (only relevant for some breath weapon types)
   * 1) On the client, from the datawatcher
   * 2) On the server- previously set by others
   * @return the current breath weapon mode
   */
  public DragonBreathMode getBreathMode()
  {
    if (dragon.isClient()) {
      return DragonBreathMode.createFromDataParameter(dataWatcher, dataParamBreathWeaponMode);
    } else {
      return breathWeaponMode;
    }
  }


  /** set the breath weapon mode (only relevant for some breath weapon types)
   * server only.
   * @param newMode - new breath weapon mode (meaning depends on breath weapon type)
   */

  public void setBreathMode(DragonBreathMode newMode)
  {
    if (dragon.isServer()) {
      breathWeaponMode = newMode;
      breathWeaponMode.writeToDataWatcher(dataWatcher, dataParamBreathWeaponMode);
    } else {
      L.warn("setBreathMode is only valid on server");
    }
  }

  /**
   * For tamed dragons, returns the target that their controlling player has selected using the DragonOrb.
   * @return the player's selected target, or null if no player target or dragon isn't tamed.
   */
  public BreathWeaponTarget getPlayerSelectedTarget()
  {
    Entity owner = dragon.getOwner();
    if (owner == null) {
      return null;
    }

    if (dragon.isClient()) {
      return getTarget();
    }

    EntityPlayerMP entityPlayerMP = (EntityPlayerMP)owner;
    BreathWeaponTarget breathWeaponTarget = DragonOrbTargets.getInstance().getPlayerTarget(entityPlayerMP);
    return breathWeaponTarget;
  }

  @Override
  public void onLivingUpdate() {
    ++tickCounter;
    if (dragon.isClient()) {
      onLivingUpdateClient();
    } else {
      onLivingUpdateServer();
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound nbt) {}

  @Override
  public void readFromNBT(NBTTagCompound nbt) {}

  @Override
  public void applyEntityAttributes() {}

  @Override
  public void onDeathUpdate() {}

  @Override
  public void onDeath() {}
  private static final Logger L = LogManager.getLogger();

  private void updateBreathState(BreathWeaponTarget targetBeingBreathedAt)
  {
    if (targetBeingBreathedAt == null) {
      playerHasReleasedTargetSinceLastBreath = true;
    }
    switch (currentBreathState) {
      case IDLE: {
        if (targetBeingBreathedAt != null && playerHasReleasedTargetSinceLastBreath) {
          transitionStartTick = tickCounter;
          currentBreathState = BreathState.STARTING;
          playerHasReleasedTargetSinceLastBreath = false;
        }
        break;
      }
      case STARTING: {
        int ticksSpentStarting = tickCounter - transitionStartTick;
        if (ticksSpentStarting >= BREATH_START_DURATION) {
          transitionStartTick = tickCounter;
          currentBreathState = (targetBeingBreathedAt != null) ? BreathState.SUSTAIN : BreathState.STOPPING;
        }
        break;
      }
      case SUSTAIN: {
        if (targetBeingBreathedAt == null) {
          forceStop();
        }
        break;
      }
      case STOPPING: {
        int ticksSpentStopping = tickCounter - transitionStartTick;
        if (ticksSpentStopping >= BREATH_STOP_DURATION) {
          currentBreathState = BreathState.IDLE;
        }
        break;
      }
      default: {
        DragonMounts.loggerLimit.error_once("Unknown currentBreathState:" + currentBreathState);
        return;
      }
    }
  }

  private void forceStop()
  {
    transitionStartTick = tickCounter;
    currentBreathState = BreathState.STOPPING;
  }

  private void onLivingUpdateServer()
  {
    refreshBreed(dragon);
    BreathWeaponTarget target = getTarget();
    updateBreathState(target);
    dragon.getBreed().getBreathWeapon(dragon).updateBreathWeaponMode();
    DragonBreathMode dragonBreathMode = dragon.getBreathHelperP().getBreathMode();

    switch (dragon.getBreed().getBreathWeaponSpawnType(dragon)) {
      case NODES: {
        if (target != null) {
          Vec3d origin = dragon.getAnimator().getThroatPosition();
          Vec3d destination = target.getTargetedPoint(dragon.world, origin);
          if (destination != null && currentBreathState == BreathState.SUSTAIN) {
            BreathNodeP.Power power = dragon.getLifeStageHelper().getBreathPowerP();
            breathAffectedArea.continueBreathing(dragon.getEntityWorld(), origin, destination, breathNodeFactory, power, dragonBreathMode);
          }
        }
        breathAffectedArea.updateTick(dragon.world, dragonBreathMode);
        break;
      }
      case PROJECTILE: {
        if (target != null) {
          Vec3d origin = dragon.getAnimator().getThroatPosition();
          Vec3d destination = target.getTargetedPoint(dragon.world, origin);
          if (destination != null && currentBreathState == BreathState.SUSTAIN) {
            BreathNodeP.Power power = dragon.getLifeStageHelper().getBreathPowerP();
            boolean spawned =  breathProjectileFactory.spawnProjectile(dragon.getEntityWorld(), dragon,  // may not spawn anything if a projectile was spawned recently...
                                                    origin, destination, power);
            if (spawned) {
              forceStop();
            }
          }
        }
        breathProjectileFactory.updateTick(currentBreathState);
        break;
      }
      default: {
        DragonMounts.loggerLimit.error_once(
                "Unknown BreathWeaponSpawnType:" + dragon.getBreed().getBreathWeaponSpawnType(dragon));
        return;
      }
    }
  }

  private void onLivingUpdateClient()
  {
    refreshBreed(dragon);
    BreathWeaponTarget target = getTarget();
    updateBreathState(target);

    switch (dragon.getBreed().getBreathWeaponSpawnType(dragon)) {
      case NODES: {
        DragonBreathMode dragonBreathMode = getBreathMode();
        breathWeaponFXEmitter.changeBreathMode(dragonBreathMode);

        if (target != null) {
          Vec3d origin = dragon.getAnimator().getThroatPosition();
          Vec3d destination = target.getTargetedPoint(dragon.world, origin);
          if (destination != null && currentBreathState == BreathState.SUSTAIN) {
            breathWeaponFXEmitter.setBeamEndpoints(origin, destination);
            BreathNodeP.Power power = dragon.getLifeStageHelper().getBreathPowerP();
            breathWeaponFXEmitter.spawnBreathParticles(dragon.getEntityWorld(), power, tickCounter);
          }
        }
        break;
      }
      case PROJECTILE: {
        //nothing to do client side for projectiles; they are normal entities
        // just animate the mouth closed once sustain is reached
        final int SUSTAIN_VISUAL_DELAY = 8;
        if (currentBreathState == BreathState.SUSTAIN
                && tickCounter > transitionStartTick + SUSTAIN_VISUAL_DELAY) {
          forceStop();
        }
        break;
      }
      default: {
        DragonMounts.loggerLimit.error_once(
                "Unknown BreathWeaponSpawnType:" + dragon.getBreed().getBreathWeaponSpawnType(dragon));
        return;
      }
    }

    soundEffectBreathWeapon.performTick(Minecraft.getMinecraft().player);
  }

  public SoundController getSoundController(World world)
  {
    if (!world.isRemote) {
      throw new IllegalArgumentException("getSoundController() only valid for WorldClient");
    }
    if (soundController == null) {
      soundController = new SoundController((WorldClient)world);
    }

    return soundController;
  }

  private SoundController soundController;
  private SoundEffectBreathWeaponP soundEffectBreathWeapon;
  private WeaponInfoLink weaponInfoLink = new WeaponInfoLink();

  // Callback link to provide the Sound generator with state information
  public class WeaponInfoLink implements SoundEffectBreathWeaponP.WeaponSoundUpdateLink {

    @Override
    public boolean refreshWeaponSoundInfo(SoundEffectBreathWeaponP.WeaponSoundInfo infoToUpdate) {
      BreathWeaponTarget target = getTarget();
      Vec3d origin;
      origin = dragon.getAnimator().getThroatPosition();
      infoToUpdate.dragonHeadLocation = origin;
      infoToUpdate.relativeVolume = dragon.getScale();
      infoToUpdate.lifeStage = dragon.getLifeStageHelper().getLifeStage();

      boolean isBreathing = false;
      if (target != null) {
        Vec3d destination = target.getTargetedPoint(dragon.world, origin);
        if (destination != null && currentBreathState == BreathState.SUSTAIN) {
          isBreathing = true;
        }
      }
      infoToUpdate.breathingState = isBreathing ? SoundEffectBreathWeaponP.WeaponSoundInfo.State.BREATHING
                                                : SoundEffectBreathWeaponP.WeaponSoundInfo.State.IDLE;

      return true;
    }
  }

  /**
   * Get the target currently being breathed at, for this dragon:
   * 1) On the client, from the datawatcher
   * 2) On the server- previously set by AI
   * @return the target, or null for none
   */
  private BreathWeaponTarget getTarget()
  {
    if (dragon.isClient()) {
      String targetString = dataWatcher.get(dataParamBreathWeaponTarget);
      BreathWeaponTarget target = BreathWeaponTarget.fromEncodedString(targetString);
      return target;
    } else {
      return targetBeingBreathedAt;
    }
  }


  //  private final int DATA_WATCHER_BREATH_TARGET;
//  private final int DATA_WATCHER_BREATH_MODE;
  private final int BREATH_START_DURATION = 5; // ticks
  private final int BREATH_STOP_DURATION = 5; // ticks
  private BreathWeaponTarget targetBeingBreathedAt = null;  // server: the target currently being breathed at
  private BreathWeaponTarget lastBreathTargetSent = null;   // server: the last target sent to the client thru DataWatcher
  private BreathState currentBreathState = BreathState.IDLE;
  private int transitionStartTick;
  private BreathWeaponFXEmitter breathWeaponFXEmitter = null;
  private int tickCounter = 0;
  private BreathWeaponTarget breathWeaponTarget;
  private boolean playerHasReleasedTargetSinceLastBreath = false;

  private BreathAffectedArea breathAffectedArea;
  private DragonBreed currentBreed = null;
  private BreathProjectileFactory breathProjectileFactory = null;
  private BreathNodeFactory breathNodeFactory = null;
  private DragonBreathMode breathWeaponMode = DragonBreathMode.DEFAULT;
}
