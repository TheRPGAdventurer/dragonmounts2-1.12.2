package com.TheRPGAdventurer.ROTD.server.entity.ai;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class EntityAIWanderOld extends EntityAIDragonBase {
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private static final String __OBFID = "CL_00001608";
    private boolean mustUpdate;

    public EntityAIWanderOld(EntityTameableDragon dragon, double p_i1648_2_)
    {
        super(dragon);
        this.speed = p_i1648_2_; 
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.dragon.isEgg())
        {
            return false;
        }
        else 
        	if (this.dragon.getRNG().nextInt(120) != 0)
        {
            return false;
        }
        else
        {
            Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.dragon, 10, 7);

            if (vec3d == null)
            {
                return false;
            }
            else
            {
                this.xPosition = vec3d.x;
                this.yPosition = vec3d.y;
                this.zPosition = vec3d.z;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.dragon.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.dragon.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
    
    public void makeUpdate()
    {
        this.mustUpdate = true;
    }
}