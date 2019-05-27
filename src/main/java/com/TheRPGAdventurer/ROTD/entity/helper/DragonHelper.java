/*
 ** 2013 October 27
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
<<<<<<< HEAD:src/main/java/com/TheRPGAdventurer/ROTD/entity/helper/DragonHelper.java
package com.TheRPGAdventurer.ROTD.entity.helper;
=======
package com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.helper;
>>>>>>> 487f066b... changes:src/main/java/com/TheRPGAdventurer/ROTD/entity/entitytameabledragon/helper/DragonHelper.java

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.EntityDataManager;

import java.util.Random;

<<<<<<< HEAD:src/main/java/com/TheRPGAdventurer/ROTD/entity/helper/DragonHelper.java
=======
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;

>>>>>>> 487f066b... changes:src/main/java/com/TheRPGAdventurer/ROTD/entity/entitytameabledragon/helper/DragonHelper.java
/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public abstract class DragonHelper {

    protected final EntityTameableDragon dragon;
    protected final EntityDataManager dataWatcher;
    protected final Random rand;

    public DragonHelper(EntityTameableDragon dragon) {
        this.dragon = dragon;
        this.dataWatcher = dragon.getDataManager();
        this.rand = dragon.getRNG();
    }
    
    public void writeToNBT(NBTTagCompound nbt) {}
    public void readFromNBT(NBTTagCompound nbt) {}
    public void applyEntityAttributes() {}
    public void onLivingUpdate() {}
    public void onDeathUpdate() {}
    public void onDeath() {}
}
