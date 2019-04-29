/*
 ** 2016 M�rz 14
 **
 ** The author disclaims copyright to this source code. In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.helper;

import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.math.MathX;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonSoundManager extends DragonHelper {

    public DragonSoundManager(EntityTameableDragon dragon) {
        super(dragon);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    public SoundEvent getLivingSound() {
        if (dragon.isEgg() || dragon.isUsingBreathWeapon()) { // isFlying
            return null;
        } else {
            return dragon.getBreed().getLivingSound();
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    public SoundEvent getHurtSound() {
        if (dragon.isEgg()) {
            return ModSounds.DRAGON_HATCHING;
        } else {
            return dragon.getBreed().getHurtSound();
        }
    }

    /**
     * Returns the sound this mob makes on death.
     */
    public SoundEvent getDeathSound() {
        if (dragon.isEgg()) {
            return ModSounds.DRAGON_HATCHED;
        } else {
            return dragon.getBreed().getDeathSound();
        }
    }

    public SoundEvent getWingsSound() {
        return dragon.getBreed().getWingsSound();
    }

    public SoundEvent getStepSound() {
        return dragon.getBreed().getStepSound();
    }

    public SoundEvent getEatSound() {
        return dragon.getBreed().getEatSound();
    }

    public SoundEvent getAttackSound() {
        return dragon.getBreed().getAttackSound();
    }

    /**
     * Plays living's sound at its position
     */
    public void playLivingSound() {
        SoundEvent sound = getLivingSound();
        if (sound == null && !dragon.isEgg()) {
            return;
        }

        playSound(sound, 0.7f, 1);
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval() {
        return 240;
    }

    /**
     * Client side method for wing animations. Plays wing flapping sounds.
     *
     * @param speed wing animation playback speed
     */
    public void onWingsDown(float speed) {
        if (!dragon.isInWater() && dragon.isFlying()) {
            // play wing sounds
            float pitch = (1);
            float volume = 1.8f + (1 - speed);
            playSound(getWingsSound(), volume, pitch, false);
        }
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    public void playStepSound(BlockPos entityPos, Block block) {
        // no sounds for eggs or underwater action
        if (dragon.isEgg() || dragon.isInWater() || dragon.isOverWater()) {
            return;
        }

        if (dragon.isFlying()) {
            return;
        }

        // override sound type if the top block is snowy
        SoundType soundType;
        if (dragon.world.getBlockState(entityPos.up()).getBlock() == Blocks.SNOW_LAYER) {
            soundType = Blocks.SNOW_LAYER.getSoundType();
        } else {
            soundType = block.getSoundType();
        }

        // play stomping for bigger dragons
        SoundEvent stepSound;
        if (dragon.isHatchling()) {
            stepSound = soundType.getStepSound();
        } else {
            stepSound = getStepSound();
        }

        playSound(stepSound, soundType.getVolume(), soundType.getPitch());
    }

    public void playSound(SoundEvent sound, float volume, float pitch, boolean local) {
        if (sound == null || dragon.isSilent()) {
            return;
        }

        volume *= getVolume(sound);
        pitch *= getPitch();// sound

        if (local) {
            dragon.world.playSound(dragon.posX, dragon.posY, dragon.posZ,
                    sound, dragon.getSoundCategory(), volume, pitch, false);
        } else {
            dragon.world.playSound(null, dragon.posX, dragon.posY, dragon.posZ,
                    sound, dragon.getSoundCategory(), volume, pitch);
        }
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        playSound(sound, volume, pitch, false);
    }

    /**
     * Returns the volume for a sound to play.
     */
    public float getVolume(SoundEvent sound) {
        return MathX.clamp(dragon.getScale(), 0, 1) * dragon.getBreed().getSoundVolume(sound);
    }

    /**
     * Returns the pitch for a sound to play.
     */
//    public float getPitch(SoundEvent sound) {
//    	float pitch = (2.0f - MathX.clamp(dragon.getScale(), 0.0f, 1.0f) * dragon.getBreed().getSoundPitch(sound));
//        return pitch;
//    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    public float getPitch() {
        return dragon.isChild() ? (dragon.getRNG().nextFloat() - dragon.getRNG().nextFloat()) * 0.2F + 1.5F : (dragon.getRNG().nextFloat() - dragon.getRNG().nextFloat()) * 0.2F + 1.0F;
    }
}