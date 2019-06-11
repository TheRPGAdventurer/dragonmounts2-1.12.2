package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumDifficulty;

public class EntityTameableDragonStats {
    /**
     * The player's food saturation.
     */
    private float foodSaturationLevel=5.0F;
    /**
     * The player's food exhaustion.
     */
    private float foodExhaustionLevel;
    /**
     * The player's food timer value.
     */
    private int foodTimer;
    private int prevFoodLevel=20;

    public void onUpdate(EntityTameableDragon dragon) {
        EnumDifficulty enumdifficulty=dragon.world.getDifficulty();

        if (foodSaturationLevel > 0 && dragon.shouldHeal() && dragon.getHunger() >= 150) {
            ++foodTimer;
            float f=Math.min(this.foodSaturationLevel, 6.0F);
            dragon.heal(f / 6.0F);
            this.addExhaustion(f);
            this.foodTimer=0;
        } else if (dragon.getHunger() >= 18 && dragon.shouldHeal()) {
            ++this.foodTimer;

            if (this.foodTimer >= 80) {
                dragon.heal(1.0F);
                this.addExhaustion(6.0F);
                this.foodTimer=0;
            }
        } else if (dragon.getHunger() <= 0) {
            ++this.foodTimer;

            if (this.foodTimer >= 80) {
                if (dragon.getHealth() > 10.0F || enumdifficulty==EnumDifficulty.HARD || dragon.getHealth() > 1.0F && enumdifficulty==EnumDifficulty.NORMAL) {
                    dragon.attackEntityFrom(DamageSource.STARVE, 1.0F);
                }

                this.foodTimer=0;
            }
        } else {
            this.foodTimer=0;
        }
    }

    /**
     * Reads the food data for the dragon.
     */
    public void readNBT(NBTTagCompound compound) {
        if (compound.hasKey("foodLevel", 99)) {
            this.foodTimer=compound.getInteger("foodTickTimer");
            this.foodSaturationLevel=compound.getFloat("foodSaturationLevel");
            this.foodExhaustionLevel=compound.getFloat("foodExhaustionLevel");
        }
    }

    /**
     * Writes the food data for the player.
     */
    public void writeNBT(NBTTagCompound compound) {
        compound.setInteger("foodTickTimer", this.foodTimer);
        compound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
        compound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
    }

    /**
     * adds input to foodExhaustionLevel to a max of 40
     */
    public void addExhaustion(float exhaustion) {
        this.foodExhaustionLevel=Math.min(this.foodExhaustionLevel + exhaustion, 40.0F);
    }


}
