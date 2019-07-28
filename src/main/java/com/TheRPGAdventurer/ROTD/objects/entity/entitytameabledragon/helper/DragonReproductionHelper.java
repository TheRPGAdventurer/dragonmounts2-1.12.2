/*
 ** 2013 October 28
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.google.common.base.Optional;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonReproductionHelper extends DragonHelper {

    public static final String NBT_BREEDER = "HatchedByUUID";
    public static final String NBT_REPRO_TIME = "ReproTime";
    // old NBT keys
    public static final String NBT_BREEDER_OLD = "HatchedBy";
    // how long is this using ticks as timer dragon before going to breed
    public static final int MAX_BREED_TIME = 10000;
    private static final Logger L = LogManager.getLogger();
    private final DataParameter<Optional<UUID>> DATA_BREEDER;
    private final DataParameter<Integer> DATA_REPRO_TIME;

    public DragonReproductionHelper(EntityTameableDragon dragon,
                                    DataParameter<Optional<UUID>> DATA_BREEDER,
                                    DataParameter<Integer> DATA_REPRO_TIME) {
        super(dragon);

        this.DATA_BREEDER = DATA_BREEDER;
        this.DATA_REPRO_TIME = DATA_REPRO_TIME;

        dataWatcher.register(DATA_BREEDER, Optional.absent());
        dataWatcher.register(DATA_REPRO_TIME, 0);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        Optional<UUID> breederID = getBreederID();
        if (breederID.isPresent()) {
            nbt.setUniqueId(NBT_BREEDER, breederID.get());
        }
        nbt.setInteger(NBT_REPRO_TIME, getReproTicks());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.setReproTicks(nbt.getInteger(NBT_REPRO_TIME));

        if (nbt.hasKey(NBT_BREEDER)) {
            setBreederID(nbt.getUniqueId(NBT_BREEDER));
        } else if (nbt.hasKey(NBT_BREEDER_OLD)) {
            // lookup breeder name on server and convert to UUID
            // if we're lucky, it'll work. if not... well, it's not really being
            // used right now anyway...
            String breederName = nbt.getString(NBT_BREEDER_OLD);
            EntityPlayer breeder = dragon.world.getPlayerEntityByName(breederName);
            setBreeder(breeder);
        }
    }

    public int getReproTicks() {
        return dataWatcher.get(DATA_REPRO_TIME);
    }

    public void setReproTicks(int reproTicks) {
        L.trace("setReproTicks({})", reproTicks);
        dataWatcher.set(DATA_REPRO_TIME, Math.min(MAX_BREED_TIME, reproTicks));
    }

    public void addBreedTimer() {
        setReproTicks(getReproTicks() + MAX_BREED_TIME);
    }

    public boolean canReproduce() {
        return dragon.isTamed() && getReproTicks() == 0;
    }

    @Override
    public void onLivingUpdate() {
        if (getReproTicks() > 0) {
            setReproTicks(getReproTicks() - 1);
        } else if (getReproTicks() < 0) {
            setReproTicks(0);
        }
    }

    public Optional<UUID> getBreederID() {
        return dataWatcher.get(DATA_BREEDER);
    }

    public void setBreederID(UUID breederID) {
        L.trace("setBreederUUID({})", breederID);
        dataWatcher.set(DATA_BREEDER, Optional.fromNullable(breederID));
    }

    public EntityPlayer getBreeder() {
        Optional<UUID> breederID = getBreederID();
        if (breederID.isPresent()) {
            return dragon.world.getPlayerEntityByUUID(breederID.get());
        } else {
            return null;
        }
    }

    public void setBreeder(EntityPlayer breeder) {
        setBreederID(breeder != null ? breeder.getUniqueID() : null);
    }

    public boolean canMateWith(EntityAnimal mate) {
        if (mate == dragon) {
            // No. Just... no.
            return false;
        } else if (!(mate instanceof EntityTameableDragon)) {
            return false;
        } else if (!canReproduce()) {
            return false;
        }

        EntityTameableDragon dragonMate = (EntityTameableDragon) mate;

        if ((dragonMate.isMale() && !dragon.isMale()) || (!dragonMate.isMale() && dragon.isMale())) {
            return true;
        } else if (!dragonMate.isTamed()) {
            return false;
        } else {
            return dragon.isInLove() && dragonMate.isInLove();
        }
    }

    public EntityAgeable createChild(EntityAgeable mate) {
        if (!(mate instanceof EntityTameableDragon)) {
            throw new IllegalArgumentException("The mate isn't a dragon");
        }

        EntityTameableDragon parent1 = dragon;
        EntityTameableDragon parent2 = (EntityTameableDragon) mate;
        EntityTameableDragon baby = new EntityTameableDragon(dragon.world);

        // mix the custom names in case both parents have one
        if (parent1.hasCustomName() && parent2.hasCustomName()) {
            String p1Name = parent1.getCustomNameTag();
            String p2Name = parent2.getCustomNameTag();
            String babyName;

            if (p1Name.contains(" ") || p2Name.contains(" ")) {
                // combine two words with space
                // "Tempor Invidunt Dolore" + "Magna"
                // = "Tempor Magna" or "Magna Tempor"
                String[] p1Names = p1Name.split(" ");
                String[] p2Names = p2Name.split(" ");

                p1Name = fixChildName(p1Names[rand.nextInt(p1Names.length)]);
                p2Name = fixChildName(p2Names[rand.nextInt(p2Names.length)]);

                babyName = rand.nextBoolean() ? p1Name + " " + p2Name : p2Name + " " + p1Name;
            } else {
                // scramble two words
                // "Eirmod" + "Voluptua"
                // = "Eirvolu" or "Volueir" or "Modptua" or "Ptuamod" or ...
                if (rand.nextBoolean()) {
                    p1Name = p1Name.substring(0, (p1Name.length() - 1) / 2);
                } else {
                    p1Name = p1Name.substring((p1Name.length() - 1) / 2);
                }

                if (rand.nextBoolean()) {
                    p2Name = p2Name.substring(0, (p2Name.length() - 1) / 2);
                } else {
                    p2Name = p2Name.substring((p2Name.length() - 1) / 2);
                }

                p2Name = fixChildName(p2Name);

                babyName = rand.nextBoolean() ? p1Name + p2Name : p2Name + p1Name;
            }

            baby.setCustomNameTag(babyName);
        }

        // inherit the baby's breed from its parents
        baby.getBreedHelper().inheritBreed(parent1, parent2);

        // increase reproduction counter
        parent1.getReproductionHelper().addBreedTimer();
        parent2.getReproductionHelper().addBreedTimer();

        return baby;
    }

    private String fixChildName(String nameOld) {
        if (nameOld == null || nameOld.isEmpty()) {
            return nameOld;
        }

        // create all lower-case char array
        char[] chars = nameOld.toLowerCase().toCharArray();

        // convert first char to upper-case
        chars[0] = Character.toUpperCase(chars[0]);

        String nameNew = new String(chars);

        if (!nameOld.equals(nameNew)) {
            L.debug("Fixed child name {} -> {}");
        }

        return nameNew;
    }
}