package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.network.MessageDragonWhistle;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;

import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityAIDragonWhistle extends EntityAIDragonBase {
    MessageDragonWhistle message;

    public EntityAIDragonWhistle(EntityTameableDragon dragon) {
        super(dragon);
        setMutexBits(0);
    }

    @Override
    public boolean shouldExecute() {
        if (dragon.getOwner()==null || dragon.getControllingPlayer()!=null) {
            return false;
        }
        return !dragon.nothing();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !dragon.isFlying() && dragon.getControllingPlayer()==null && !dragon.getNavigator().noPath() && !dragon.nothing();
    }

    private boolean circleFlag() {
        EntityMoveHelper entitymovehelper=dragon.getMoveHelper();

        double d0=entitymovehelper.getX() - dragon.posX;
        double d1=entitymovehelper.getY() - dragon.posY;
        double d2=entitymovehelper.getZ() - dragon.posZ;
        double d3=d0 * d0 + d1 * d1 + d2 * d2;
        return (d3 < 1.0D) || (d3 > 3600.0D);
    }

    @Override
    public void startExecuting() {
        //Commands Requiring Flight - if any is true, start flying
        if (!dragon.isFlying() && (dragon.circle() || dragon.follow()) || dragon.firesupport()) {
            dragon.liftOff();
        }

        if (dragon.isFlying()) {
            if (dragon.circle() && dragon.getOwner()!=null && !dragon.circleTarget1(dragon.getOwner().getPosition())) {
                dragon.circleTarget1(dragon.getOwner().getPosition());
                dragon.getAISit().setSitting(false);
            } else if (dragon.follow() && !dragon.followPlayerFlying(dragon.getOwner()) && dragon.getOwner()!=null) {
                dragon.followPlayerFlying(dragon.getOwner());
                dragon.getAISit().setSitting(false);
            } else if (dragon.homepos()) {
                BlockPos pos=new BlockPos(dragon);
                dragon.homePos=pos;
                dragon.hasHomePosition=true;
                ((EntityPlayer) dragon.getOwner()).sendStatusMessage(new TextComponentTranslation("dragon.command.new_home", dragon.homePos.getX(), dragon.homePos.getY(), dragon.homePos.getZ()), true);
            } else if (dragon.nothing()) {
                return;
            } else if(dragon.firesupport()) {
                dragon.getAISit().setSitting(false);
            }

        } else if (dragon.sit()) {
            dragon.getAISit().setSitting(!dragon.isSitting());
            dragon.getNavigator().clearPath();
            dragon.setnothing(true);
        }
    }
}
