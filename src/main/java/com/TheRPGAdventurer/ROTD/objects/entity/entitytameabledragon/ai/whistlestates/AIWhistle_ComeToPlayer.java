package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.whistlestates;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.EntityAIDragonBase;
import net.minecraft.entity.EntityLivingBase;

public class AIWhistle_ComeToPlayer extends EntityAIDragonBase {

    private EntityLivingBase owner;

    public AIWhistle_ComeToPlayer(EntityTameableDragon dragon) {
        super(dragon);
    }

    @Override
    public boolean shouldExecute() {
        this.owner = dragon.getOwner(); // Repeatedly set this in case it changes
        if (owner != null)
            dragon.setBoosting(dragon.getDistance(owner) > 2);

        dragon.setnothing(true); // Check failed, Don't keep executing
        return dragon.nothing() && owner != null && dragon.come();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldExecute();
    }

    @Override
    public void startExecuting() {
        dragon.setSitting(false);
        if (dragon.getDistance(owner) > 8 || dragon.getNavigator().getPath()==null) dragon.liftOff();

        tryMoveToBlockPos(owner.getPosition(), 1);
        dragon.setnothing(true);
    }

}
