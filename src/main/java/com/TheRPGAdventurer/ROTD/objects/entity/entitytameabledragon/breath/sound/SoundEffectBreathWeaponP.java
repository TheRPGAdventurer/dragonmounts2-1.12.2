package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.sound;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.DragonLifeStage;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by TheGreyGhost on 8/10/14.
 *
 * Used to create sound effects for the breath weapon  - start up, sustained loop, and wind-down
 * The sound made by the dragon's head
 *   1) initial startup
 *   2) looping while breathing
 *   3) stopping when done
 *  Sometimes the head sound doesn't layer properly on the first try.  I don't know why.  I have implemented a preload
 *    which seems to help.
 *
 * The SoundEffectBreathWeapon corresponds to the breath weapon of a single dragon.  Typical usage is:
 * 1) create an instance, and provide a callback function (WeaponSoundUpdateLink)
 * 2) startPlaying(), startPlayingIfNotAlreadyPlaying(), stopPlaying() to start or stop the sounds completely
 * 3) once per tick, call performTick().
 *   3a) performTick() will call the WeaponSoundUpdateLink.refreshWeaponSoundInfo(), which should return the
 *       current data relevant to the sound (eg whether the dragon is breathing, and the location of the beam)
 *
 * Is subclassed for the different breath weapons.
 *
 */
public abstract class SoundEffectBreathWeaponP
{
  public SoundEffectBreathWeaponP(SoundController i_soundController, WeaponSoundUpdateLink i_weaponSoundUpdateLink)
  {
    soundController = i_soundController;
    weaponSoundUpdateLink = i_weaponSoundUpdateLink;
  }
private final float HEAD_MIN_VOLUME = 0.02F;

  public void startPlaying(EntityPlayerSP entityPlayerSP)
  {
    stopAllSounds();
    currentWeaponState = WeaponSoundInfo.State.IDLE;
    performTick(entityPlayerSP);
  }

  public void stopPlaying()
  {
    stopAllSounds();
  }

  private void stopAllSounds()
  {
    stopAllHeadSounds();
  }

  private void stopAllHeadSounds()
  {
    if (headStartupSound != null) {
      soundController.stopSound(headStartupSound);
      headStartupSound = null;
    }
    if (headLoopSound != null) {
      soundController.stopSound(headLoopSound);
      headLoopSound = null;
    }

    if (headStoppingSound != null) {
      soundController.stopSound(headStoppingSound);
      headStoppingSound = null;
    }
  }

  private void setAllStopFlags()
  {
    if (headStartupSound != null) { headStartupSound.setDonePlaying();}
    if (headLoopSound != null) { headLoopSound.setDonePlaying();}
    if (headStoppingSound != null) { headStoppingSound.setDonePlaying();}
  }

  /**
   * Updates all the component sounds according to the state of the breath weapon.
   * @param entityPlayerSP
   */
  public void performTick(EntityPlayerSP entityPlayerSP) {
    WeaponSoundInfo weaponSoundInfo = new WeaponSoundInfo();
    boolean keepPlaying = weaponSoundUpdateLink.refreshWeaponSoundInfo(weaponSoundInfo);
    if (!keepPlaying) {
      setAllStopFlags();
      return;
    }
    checkNotNull(weaponSoundInfo.dragonHeadLocation);
    headSoundSettings.playing = true;
    headSoundSettings.masterVolume = weaponSoundInfo.relativeVolume;
    headSoundSettings.soundEpicentre = weaponSoundInfo.dragonHeadLocation;

    headSoundSettings.playerDistanceToEpicentre =
              (float) weaponSoundInfo.dragonHeadLocation.distanceTo(entityPlayerSP.getPositionVector());

//    final int HEAD_STARTUP_TICKS = 40;
//    final int HEAD_STOPPING_TICKS = 60;

    // if state has changed, stop and start component sounds appropriately

    if (weaponSoundInfo.breathingState != currentWeaponState) {
      switch (weaponSoundInfo.breathingState) {
        case IDLE: {
          stopAllHeadSounds();
          SoundEffectName headStop = weaponHeadSound(SoundPart.STOP, weaponSoundInfo.lifeStage);
          headStoppingSound =
                  ComponentSound.createComponentSound(headStop.getSoundEvent(), SoundCategory.HOSTILE,
                                                    HEAD_MIN_VOLUME, ComponentSound.RepeatType.NO_REPEAT,
                                                    headSoundSettings);
          headStoppingSound.setPlayCountdown(headStop.getDurationInTicks());
          soundController.playSound(headStoppingSound);
          break;
        }
        case BREATHING: {
          stopAllHeadSounds();
          SoundEffectName headStart = weaponHeadSound(SoundPart.START, weaponSoundInfo.lifeStage);
          SoundEffectName headLoop = weaponHeadSound(SoundPart.LOOP, weaponSoundInfo.lifeStage);
          SoundEffectName headStop = weaponHeadSound(SoundPart.STOP, weaponSoundInfo.lifeStage);

          ComponentSound preloadLoop = ComponentSound.createComponentSoundPreload(headLoop.getSoundEvent(), SoundCategory.HOSTILE);
          soundController.playSound(preloadLoop);
          ComponentSound preLoadStop = ComponentSound.createComponentSoundPreload(headStop.getSoundEvent(), SoundCategory.HOSTILE);
          soundController.playSound(preLoadStop);
          headStartupSound = ComponentSound.createComponentSound(headStart.getSoundEvent(), SoundCategory.HOSTILE,
                                                   HEAD_MIN_VOLUME, ComponentSound.RepeatType.NO_REPEAT,
                                                   headSoundSettings);
          headStartupSound.setPlayCountdown(headStart.getDurationInTicks());
          soundController.playSound(headStartupSound);
          break;
        }
        default: {
          DragonMounts.loggerLimit.error_once(
                  "Illegal weaponSoundInfo.breathingState:" + weaponSoundInfo.breathingState + " in " + this
                          .getClass());
        }
      }
      currentWeaponState = weaponSoundInfo.breathingState;
    }

    // update component sound settings based on weapon info and elapsed time

    switch (currentWeaponState) {
      case BREATHING: {
        if (headStartupSound != null && headStartupSound.getPlayCountdown() <= 0) {
          stopAllHeadSounds();
          SoundEffectName headLoop = weaponHeadSound(SoundPart.LOOP, weaponSoundInfo.lifeStage);
          headLoopSound = ComponentSound.createComponentSound(headLoop.getSoundEvent(), SoundCategory.HOSTILE,
                                                HEAD_MIN_VOLUME, ComponentSound.RepeatType.REPEAT, headSoundSettings);
          soundController.playSound(headLoopSound);
        }

        break;
      }
      case IDLE: {
        if (headStoppingSound != null) {
          if (headStoppingSound.getPlayCountdown() <= 0) {   //|| !soundController.isSoundPlaying(headStoppingSound)) {  causes strange bug "channel null in method 'stop'"
            soundController.stopSound(headStoppingSound);
            headStoppingSound = null;
          }
        }
        break;
      }
      default: {
        DragonMounts.loggerLimit.error_once("Unknown currentWeaponState:" + currentWeaponState);
      }
    }
  }

  WeaponSoundInfo.State currentWeaponState = WeaponSoundInfo.State.IDLE;

  private ComponentSound.ComponentSoundSettings headSoundSettings = new ComponentSound.ComponentSoundSettings(0.7F);

  private ComponentSound headStartupSound;
  private ComponentSound headLoopSound;
  private ComponentSound headStoppingSound;

  private SoundController soundController;
  private WeaponSoundUpdateLink weaponSoundUpdateLink;

  /**
   * Used as a callback to update the sound's position and
   */
  public interface WeaponSoundUpdateLink
  {
    boolean refreshWeaponSoundInfo(WeaponSoundInfo infoToUpdate);
  }

  public static class WeaponSoundInfo
  {
    public enum State {IDLE, BREATHING}
    public State breathingState = State.IDLE;
    public Vec3d dragonHeadLocation;  // location of a noise-making dragon's head
    public float relativeVolume; // 0 to 1
    public DragonLifeStage lifeStage;
  }

  protected enum SoundPart {START, LOOP, STOP}

  /**
   * Returns the sound of the dragon's head for the given breed, lifestage, and sound part
   * @param soundPart which part of the breathing sound?
   * @param lifeStage how old is the dragon?
   * @return the resourcelocation corresponding to the desired sound. null for none.
   */
  abstract protected SoundEffectName weaponHeadSound(SoundPart soundPart, DragonLifeStage lifeStage);

}
