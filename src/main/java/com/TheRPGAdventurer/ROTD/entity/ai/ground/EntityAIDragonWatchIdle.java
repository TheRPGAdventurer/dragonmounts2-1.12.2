/*
 ** 2012 October 26
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
<<<<<<< HEAD:src/main/java/com/TheRPGAdventurer/ROTD/entity/ai/ground/EntityAIDragonWatchIdle.java
package com.TheRPGAdventurer.ROTD.entity.ai.ground;
=======
package com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.ai.ground;

import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;
>>>>>>> 487f066b... changes:src/main/java/com/TheRPGAdventurer/ROTD/entity/entitytameabledragon/ai/ground/EntityAIDragonWatchIdle.java

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import net.minecraft.entity.ai.EntityAILookIdle;
/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class EntityAIDragonWatchIdle extends EntityAILookIdle {

    private EntityTameableDragon dragon;

    public EntityAIDragonWatchIdle(EntityTameableDragon par1EntityLiving) {
        super(par1EntityLiving);
        this.dragon = par1EntityLiving;
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute() {
        if(dragon.circle()) {
            return false;
        }
        return super.shouldExecute();
    }
}
