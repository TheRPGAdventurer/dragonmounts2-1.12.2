/*
** 2016 March 13
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.path;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Based on PathNavigateSwimmer but for air blocks.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class PathNavigateFlying extends PathNavigateSwimmer {
	
	public double posX;
    public double posY;
    public double posZ;

    public PathNavigateFlying(EntityLiving entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    @Override
    protected PathFinder getPathFinder() {
        return new PathFinder(new NodeProcessorFlying());
    }

    @Override
    protected boolean canNavigate() {
        return !isInLiquid();
    }

    public void setVector(Vec3d vec) {
        posX = vec.x;
        posY = vec.y;
        posZ = vec.z;
    }

}
