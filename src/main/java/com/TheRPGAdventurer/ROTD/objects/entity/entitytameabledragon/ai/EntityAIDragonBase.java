/*
** 2016 MÃ¤rz 15
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static net.minecraft.entity.SharedMonsterAttributes.FOLLOW_RANGE;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public abstract class EntityAIDragonBase extends EntityAIBase {
    
    protected EntityTameableDragon dragon;
    protected World world;
    protected Random random;
    protected EntityPlayer rider;

    public EntityAIDragonBase(EntityTameableDragon dragon) {
        this.dragon = dragon;
        this.world = dragon.world;
        this.random = dragon.getRNG(); 
        rider = dragon.getControllingPlayer();
    }
    
    protected boolean tryMoveToBlockPos(BlockPos pos, double speed) {
        return dragon.getNavigator().tryMoveToXYZ(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, speed);
    }
    
    protected double getFollowRange() {
        return dragon.getAttributeMap().getAttributeInstance(FOLLOW_RANGE).getAttributeValue();
    }
}
