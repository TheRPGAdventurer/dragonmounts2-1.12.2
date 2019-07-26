package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.path;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateNotFlying extends PathNavigate {

    public PathNavigateNotFlying(EntityLiving entityIn, World worldIn) {
        super(entityIn, worldIn);
    }

    @Override
    protected PathFinder getPathFinder() {
        return null;
    }

    @Override
    protected Vec3d getEntityPosition() {
        return null;
    }

    @Override
    protected boolean canNavigate() {
        return false;
    }

    @Override
    protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
        return false;
    }
}
