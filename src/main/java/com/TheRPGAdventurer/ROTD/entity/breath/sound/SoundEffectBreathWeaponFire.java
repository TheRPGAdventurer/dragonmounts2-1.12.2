package com.TheRPGAdventurer.ROTD.entity.breath.sound;

import com.TheRPGAdventurer.ROTD.entity.helper.EnumDragonLifeStage;

/**
 * Created by TheGreyGhost on 24/6/16.
 * Sounds effects for the dragon's mouth for fire
 */
public class SoundEffectBreathWeaponFire extends SoundEffectBreathWeapon {
    public SoundEffectBreathWeaponFire(SoundController i_soundController, WeaponSoundUpdateLink i_weaponSoundUpdateLink) {
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
        final SoundEffectNames hatchling[]={SoundEffectNames.HATCHLING_BREATHE_FIRE_START, SoundEffectNames.HATCHLING_BREATHE_FIRE_LOOP, SoundEffectNames.HATCHLING_BREATHE_FIRE_STOP};

        final SoundEffectNames juvenile[]={SoundEffectNames.JUVENILE_BREATHE_FIRE_START, SoundEffectNames.JUVENILE_BREATHE_FIRE_LOOP, SoundEffectNames.JUVENILE_BREATHE_FIRE_STOP};

        final SoundEffectNames adult[]={SoundEffectNames.ADULT_BREATHE_FIRE_START, SoundEffectNames.ADULT_BREATHE_FIRE_LOOP, SoundEffectNames.ADULT_BREATHE_FIRE_STOP};

        SoundEffectNames[] soundEffectNames;
        switch (lifeStage) {
            case HATCHLING: {
                soundEffectNames=hatchling;
                break;
            }
            case JUVENILE: {
                soundEffectNames=juvenile;
                break;
            }
            case ADULT: {
                soundEffectNames=adult;
                break;
            }
            default: {
                System.err.println("Unknown lifestage:" + lifeStage + " in weaponHeadSound()");
                soundEffectNames=hatchling; // dummy
            }
        }
        return soundEffectNames[soundPart.ordinal()];
    }


}
