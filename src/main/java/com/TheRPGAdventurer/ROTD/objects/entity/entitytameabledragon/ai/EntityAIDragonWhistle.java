package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.network.MessageDragonWhistle;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityAIDragonWhistle extends EntityAIDragonBase {
    MessageDragonWhistle message;

    public EntityAIDragonWhistle(EntityTameableDragon dragon) {
        super(dragon);
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        return dragon.getOwner() != null && dragon.getControllingPlayer() == null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return dragon.getControllingPlayer() == null;
    }

    @Override
    public void startExecuting() {
        //Commands Requiring Flight - if any is true, start flying
        if (!dragon.isFlying() && (dragon.circle() || dragon.follow()) || dragon.firesupport() || (dragon.come() && (dragon.getNavigator().noPath() || dragon.getDistance(dragon.getOwner()) > 12))) {
            dragon.liftOff();
        }

        if (dragon.come() && !dragon.comeToPlayerFlying(dragon.getOwner())) {
            dragon.comeToPlayerFlying(dragon.getOwner());
            if (dragon.getDistance(dragon.getOwner()) < 2 && !dragon.isFlying()) {
                dragon.setnothing(true);
            }
        }

        if (dragon.circle() && dragon.getOwner() != null && !dragon.circleTarget1(dragon.getOwner().getPosition())) {
            dragon.circleTarget1(dragon.getOwner().getPosition());
            dragon.getAISit().setSitting(false);
        } else if (dragon.follow() && !dragon.followPlayerFlying(dragon.getOwner()) && dragon.getOwner() != null) {
            dragon.followPlayerFlying(dragon.getOwner());
            dragon.getAISit().setSitting(false);
        }
        if (dragon.homepos()) {
            BlockPos pos = new BlockPos(dragon);
            dragon.homePos = pos;
            dragon.hasHomePosition = true;
            ((EntityPlayer) dragon.getOwner()).sendStatusMessage(new TextComponentTranslation("dragon.command.new_home", dragon.homePos.getX(), dragon.homePos.getY(), dragon.homePos.getZ()), true);
        } else if (dragon.nothing()) {
            return;
        } else if (dragon.firesupport()) {
            dragon.getAISit().setSitting(false);
        }

        if (dragon.sit()) {
            dragon.getAISit().setSitting(!dragon.isSitting());
            dragon.getNavigator().clearPath();
            dragon.setnothing(true);
        }
    }
}
