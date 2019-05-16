package com.TheRPGAdventurer.ROTD.entity.breath.sound;

import com.TheRPGAdventurer.ROTD.entity.helper.EnumDragonLifeStage;

/**
 * Created by TheGreyGhost on 24/6/16.
 * Sounds effects for the dragon's mouth for air
 */
public class SoundEffectBreathWeaponAir extends SoundEffectBreathWeapon {
    public SoundEffectBreathWeaponAir(SoundController i_soundController, WeaponSoundUpdateLink i_weaponSoundUpdateLink) {
        super(i_soundController, i_weaponSoundUpdateLink);
    }

    /**
     * Returns the sound for the given breed, lifestage, and sound part
     *
     * @param soundPart which part of the breathing sound?
     * @param lifeStage how old is the dragon?
     * @return the resourcelocation corresponding to the desired sound
     */
    @Override
    protected SoundEffectNames weaponHeadSound(SoundPart soundPart, EnumDragonLifeStage lifeStage) {
        //    final SoundEffectNames hatchling[] = {SoundEffectNames.HATCHLING_BREATHE_FIRE_START,
        //                                          SoundEffectNames.HATCHLING_BREATHE_FIRE_LOOP,
        //                                          SoundEffectNames.HATCHLING_BREATHE_FIRE_STOP};
        //
        //    final SoundEffectNames juvenile[] = {SoundEffectNames.JUVENILE_BREATHE_FIRE_START,
        //                                          SoundEffectNames.JUVENILE_BREATHE_FIRE_LOOP,
        //                                          SoundEffectNames.JUVENILE_BREATHE_FIRE_STOP};
        //
        //    final SoundEffectNames adult[] = {SoundEffectNames.ADULT_BREATHE_FIRE_START,
        //                                      SoundEffectNames.ADULT_BREATHE_FIRE_LOOP,
        //                                      SoundEffectNames.ADULT_BREATHE_FIRE_STOP};

        SoundEffectNames soundEffectNames;
        switch (soundPart) {
            case START: {
                soundEffectNames=SoundEffectNames.BREATHE_AIR_START;
                break;
            }
            case LOOP: {
                soundEffectNames=SoundEffectNames.BREATHE_AIR_LOOP;
                break;
            }
            case STOP: {
                soundEffectNames=SoundEffectNames.BREATHE_AIR_STOP;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown SoundPart:" + soundPart);
            }
        }
        return soundEffectNames;
    }


}
