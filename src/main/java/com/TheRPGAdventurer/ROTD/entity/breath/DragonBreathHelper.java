package com.TheRPGAdventurer.ROTD.entity.breath;

import com.TheRPGAdventurer.ROTD.client.render.dragon.breathweaponFX.BreathWeaponEmitter;
import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breath.sound.SoundController;
import com.TheRPGAdventurer.ROTD.entity.breath.sound.SoundEffectBreathWeapon;
import com.TheRPGAdventurer.ROTD.entity.breath.weapons.*;
import com.TheRPGAdventurer.ROTD.entity.helper.DragonHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
 * turning the dragon to face the target, is done by targeting AI.
 * DragonBreathHelper is also responsible for
 * - tracking the current breath state (IDLE, STARTING, SUSTAINED BREATHING, STOPPING)
 * - sound effects
 * - adding delays for jaw open / breathing start
 * - interrupting the beam when the dragon is facing the wrong way / the angle of the beam mismatches the head angle
 * Usage:
 * 1) Create instance, providing the parent dragon entity and a datawatcher index to use for breathing
 * 2) call onLivingUpdate(), onDeath(), onDeathUpdate(), readFromNBT() and writeFromNBT() from the corresponding
 * parent entity methods
 * 3a) The AI task responsible for targeting should call getPlayerSelectedTarget() to find out what the player wants
 * the dragon to target.
 * 3b) Once the target is in range and the dragon is facing the correct direction, the AI should use setBreathingTarget()
 * to commence breathing at the target
 * 4) getCurrentBreathState() and getBreathStateFractionComplete() should be called by animation routines for
 * the dragon during breath weapon (eg jaw opening)
 */
public class DragonBreathHelper extends DragonHelper {

    private final DataParameter<String> dataParam;
    private final int BREATH_START_DURATION=5; // ticks
    private final int BREATH_STOP_DURATION=5; // ticks
    private BreathState currentBreathState=BreathState.IDLE;
    private int transitionStartTick;
    private int tickCounter=0;
    protected BreathWeaponEmitter breathWeaponEmitter;
    public BreathAffectedArea breathAffectedArea;
    public BreathAffectedArea breathAffectedAreaEnder;
    public BreathAffectedArea breathAffectedAreaNether;
    public BreathAffectedArea breathAffectedAreaIce;
    public BreathAffectedArea breathAffectedAreaHydro;
    public BreathAffectedArea breathAffectedAreaWither;
    public BreathAffectedArea breathAffectedAreaPoison;
    public BreathAffectedArea breathAffectedAreaAether;
    private static final Logger L=LogManager.getLogger();

    public DragonBreathHelper(EntityTameableDragon dragon, DataParameter<String> dataParam) {
        super(dragon);
        if (dragon.isClient()) {
            breathWeaponEmitter=new BreathWeaponEmitter();
        }
        this.dataParam=dataParam;
        dataWatcher.register(dataParam, "");
        breathAffectedArea=new BreathAffectedArea(new BreathWeapon(dragon));
        breathAffectedAreaNether=new BreathAffectedArea(new BreathWeaponNether(dragon));
        breathAffectedAreaIce=new BreathAffectedArea(new BreathWeaponIce(dragon));
        breathAffectedAreaHydro=new BreathAffectedArea(new BreathWeaponHydro(dragon));
        breathAffectedAreaEnder=new BreathAffectedArea(new BreathWeaponEnder(dragon));
        breathAffectedAreaWither=new BreathAffectedArea(new BreathWeaponWither(dragon));
        breathAffectedAreaPoison=new BreathAffectedArea(new BreathWeaponPoison(dragon));
        breathAffectedAreaAether=new BreathAffectedArea(new BreathWeaponAether(dragon));
    }

    public enum BreathState {IDLE, STARTING, SUSTAIN, STOPPING}

    public BreathState getCurrentBreathState() {
        return currentBreathState;
    }

    public float getBreathStateFractionComplete() {
        switch (currentBreathState) {
            case IDLE: {
                return 0.0F;
            }
            case STARTING: {
                int ticksSpentStarting=tickCounter - transitionStartTick;
                return MathHelper.clamp(ticksSpentStarting / (float) BREATH_START_DURATION, 0.0F, 1.0F);
            }
            case SUSTAIN: {
                return 0.0F;
            }
            case STOPPING: {
                int ticksSpentStopping=tickCounter - transitionStartTick;
                return MathHelper.clamp(ticksSpentStopping / (float) BREATH_STOP_DURATION, 0.0F, 1.0F);
            }
            default: {
                System.err.println("Unknown currentBreathState:" + currentBreathState);
                return 0.0F;
            }
        }
    }

    @Override
    public void onLivingUpdate() {
        ++tickCounter;
        if (dragon!=null) {
            if (dragon.isClient()) {
                onLivingUpdateClient();
            } else {
                onLivingUpdateServer();
            }
        }
    }

    private void onLivingUpdateServer() {
        updateBreathState(dragon.isUsingBreathWeapon());

        if (dragon.isUsingBreathWeapon()) {
            Vec3d origin=dragon.getAnimator().getThroatPosition();
            Vec3d lookDirection=dragon.getLook(1.0f);
            Vec3d endOfLook=origin.addVector(lookDirection.x, lookDirection.y, lookDirection.z);
            BreathNode.Power power=dragon.getLifeStageHelper().getBreathPower();
            if (endOfLook!=null && currentBreathState==BreathState.SUSTAIN) {
                dragon.getBreed().continueAndUpdateBreathing(dragon.getEntityWorld(), origin, endOfLook, power, dragon);
            }
        }
    }

    private void onLivingUpdateClient() {
        updateBreathState(dragon.isUsingBreathWeapon());

        if (dragon.isUsingBreathWeapon()) {
            Vec3d origin=dragon.getAnimator().getThroatPosition();
            Vec3d lookDirection=dragon.getLook(1.0f);
            Vec3d endOfLook=origin.addVector(lookDirection.x, lookDirection.y, lookDirection.z);
            if (endOfLook!=null && currentBreathState==BreathState.SUSTAIN && dragon.getBreed().canUseBreathWeapon()) {
                BreathNode.Power power=dragon.getLifeStageHelper().getBreathPower();
                dragon.getBreed().spawnBreathParticles(dragon.getEntityWorld(), power, tickCounter, origin, endOfLook, dragon);
            }
        }

        if (soundController==null) {
            soundController=new SoundController();
        }
        if (soundEffectBreathWeapon==null) {
            soundEffectBreathWeapon=new SoundEffectBreathWeapon(soundController, weaponInfoLink);
        }
        soundEffectBreathWeapon.performTick(Minecraft.getMinecraft().player, dragon);
    }
    public void refreshBreedClientOnly(EntityTameableDragon dragon)
    {
        soundEffectBreathWeapon = dragon.getBreed().getSoundEffectBreathWeapon(getSoundController(dragon.getEntityWorld()), weaponInfoLink);
    }


    private void updateBreathState(boolean isBreathing) {
        switch (currentBreathState) {
            case IDLE: {
                if (isBreathing==true) {
                    transitionStartTick=tickCounter;
                    currentBreathState=BreathState.STARTING;
                }
                break;
            }
            case STARTING: {
                int ticksSpentStarting=tickCounter - transitionStartTick;
                if (ticksSpentStarting >= BREATH_START_DURATION) {
                    transitionStartTick=tickCounter;
                    currentBreathState=(isBreathing==true) ? BreathState.SUSTAIN : BreathState.STOPPING;
                }
                break;
            }
            case SUSTAIN: {
                if (isBreathing==false) {
                    transitionStartTick=tickCounter;
                    currentBreathState=BreathState.STOPPING;
                }
                break;
            }
            case STOPPING: {
                int ticksSpentStopping=tickCounter - transitionStartTick;
                if (ticksSpentStopping >= BREATH_STOP_DURATION) {
                    currentBreathState=BreathState.IDLE;
                }
                break;
            }
            default: {
                System.err.println("Unknown currentBreathState:" + currentBreathState);
                return;
            }
        }
    }

    private SoundController soundController;
    private SoundEffectBreathWeapon soundEffectBreathWeapon;
    private WeaponInfoLink weaponInfoLink=new WeaponInfoLink();

    // Callback link to provide the Sound generator with state information
    public class WeaponInfoLink implements SoundEffectBreathWeapon.WeaponSoundUpdateLink {

        @Override
        public boolean refreshWeaponSoundInfo(SoundEffectBreathWeapon.WeaponSoundInfo infoToUpdate) {
            Vec3d origin=dragon.getAnimator().getThroatPosition();
            infoToUpdate.dragonHeadLocation=origin;
            infoToUpdate.relativeVolume=dragon.getScale();
            infoToUpdate.lifeStage=dragon.getLifeStageHelper().getLifeStage();

            boolean isUsingBreathweapon=false;
            if (dragon.isUsingBreathWeapon()) {
                Vec3d lookDirection=dragon.getLook(1.0f);
                Vec3d endOfLook=origin.addVector(lookDirection.x, lookDirection.y, lookDirection.z);
                if (endOfLook!=null && currentBreathState==BreathState.SUSTAIN && dragon.getBreed().canUseBreathWeapon()) {
                    isUsingBreathweapon=true;
                }
            }

            infoToUpdate.breathingState=isUsingBreathweapon ? SoundEffectBreathWeapon.WeaponSoundInfo.State.BREATHING : SoundEffectBreathWeapon.WeaponSoundInfo.State.IDLE;
            return true;
        }
    }

    public BreathWeaponEmitter getEmitter() {
        return breathWeaponEmitter;
    }

    public BreathAffectedArea getBreathAffectedArea() {
        return breathAffectedArea;
    }

    public BreathAffectedArea getBreathAffectedAreaNether() {
        return breathAffectedAreaNether;
    }

    public BreathAffectedArea getBreathAffectedAreaIce() {
        return breathAffectedAreaIce;
    }

    public BreathAffectedArea getBreathAffectedAreaEnd() {
        return breathAffectedAreaEnder;
    }

    public BreathAffectedArea getbreathAffectedAreaHydro() {
        return breathAffectedAreaHydro;
    }

    public BreathAffectedArea getbreathAffectedAreaWither() {
        return breathAffectedAreaWither;
    }

    public BreathAffectedArea getbreathAffectedAreaPoison() {
        return breathAffectedAreaPoison;
    }

    public BreathAffectedArea getbreathAffectedAreaAether() {
        return breathAffectedAreaAether;
    }

}