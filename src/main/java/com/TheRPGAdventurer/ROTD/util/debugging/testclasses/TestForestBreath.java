package com.TheRPGAdventurer.ROTD.util.debugging.testclasses;

import com.TheRPGAdventurer.ROTD.util.debugging.TestRunner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Test the forest breath.  Intended to be run on a blank test world SuperFlat.  Use the TestRunner item (debug config mode)
 * Created by TGG on 24/01/2016.
 */
public class TestForestBreath {

  // dummy test: check the correct functioning of the breath weapons
  // testA - test various ignition sources for generating an explosion when breathed upon

  public boolean test1(World worldIn, EntityPlayer playerIn)
  {
    BlockPos sourceRegionOrigin = new BlockPos(0, 204, 0);
    final int SOURCE_REGION_SIZE_X = 20;
    final int SOURCE_REGION_SIZE_Y = 10;
    final int SOURCE_REGION_SIZE_Z = 5;

    // put long line of dirt blocks to support our test items
    for (int x = 0; x < SOURCE_REGION_SIZE_X; ++x) {
      worldIn.setBlockState(sourceRegionOrigin.add(x, 0, 2), Blocks.DIRT.getDefaultState());
    }

    worldIn.setBlockState(sourceRegionOrigin.add(1, 1, 2), Blocks.OBSIDIAN.getDefaultState());
    worldIn.setBlockState(sourceRegionOrigin.add(2, 1, 2), Blocks.LAVA.getDefaultState());
    worldIn.setBlockState(sourceRegionOrigin.add(3, 1, 2), Blocks.OBSIDIAN.getDefaultState());

    worldIn.setBlockState(sourceRegionOrigin.add(6, 1, 2), Blocks.TORCH.getDefaultState());

    worldIn.setBlockState(sourceRegionOrigin.add(10, 0, 2), Blocks.NETHERRACK.getDefaultState());
    worldIn.setBlockState(sourceRegionOrigin.add(10, 1, 2), Blocks.FIRE.getDefaultState());

    BlockPos testRegionOriginA = new BlockPos(0, 204, 8);
//    BlockPos testRegionOriginB = new BlockPos(0, 204, 0);
//    BlockPos testRegionOriginC = new BlockPos(0, 204, 0);

    TestRunner.teleportPlayerToTestRegion(playerIn, testRegionOriginA.south(10));  // teleport the player nearby so you can watch

    // copy the test blocks to the destination region
    TestRunner.copyTestRegion(playerIn, sourceRegionOrigin, testRegionOriginA,
                              SOURCE_REGION_SIZE_X, SOURCE_REGION_SIZE_Y, SOURCE_REGION_SIZE_Z);
    //todo reinstate for testing

//    DragonBreed forestDragonBreed = DragonBreedRegistry.getInstance().getBreedByName("forest");
//
//    EntityTameableDragon dragon = new EntityTameableDragon(worldIn);
//    BreathWeapon breathWeapon = forestDragonBreed.getBreathWeapon(dragon);  // just a dummy dragon
//
//    BreathAffectedBlock bab = new BreathAffectedBlock();
//    bab.addHitDensity(EnumFacing.DOWN, 1);
//    breathWeapon.affectBlock(worldIn, testRegionOriginA.add(2, 1, 2), bab);
//
//    breathWeapon.affectBlock(worldIn, testRegionOriginA.add(6, 1, 2), bab);
//
//    breathWeapon.affectBlock(worldIn, testRegionOriginA.add(10, 1, 2), bab);

    boolean success = true;
//    copyTestRegion(playerIn, sourceRegionOrigin, testRegionOriginB,
//                   SOURCE_REGION_SIZE_X, SOURCE_REGION_SIZE_Y, SOURCE_REGION_SIZE_Z);
//    copyTestRegion(playerIn, sourceRegionOrigin, testRegionOriginC,
//                   SOURCE_REGION_SIZE_X, SOURCE_REGION_SIZE_Y, SOURCE_REGION_SIZE_Z);

//    boolean success = true;
//    // testA: replace stone with wood; ladder should remain
//    worldIn.setBlockState(testRegionOriginA.add(1, 0, 1), Blocks.log.getDefaultState());
//    success &= worldIn.getBlockState(testRegionOriginA.add(2, 0, 1)).getBlock() == Blocks.ladder;
//
//    // testB: replace stone with glass; ladder should be destroyed
//    worldIn.setBlockState(testRegionOriginB.add(1, 0, 1), Blocks.glass.getDefaultState());
//    success &= worldIn.getBlockState(testRegionOriginB.add(2, 0, 1)).getBlock() == Blocks.air;
//
//    // testC: replace stone with diamond block; ladder should remain
//    worldIn.setBlockState(testRegionOriginC.add(1, 0, 1), Blocks.diamond_block.getDefaultState());
//    success &= worldIn.getBlockState(testRegionOriginC.add(2, 0, 1)).getBlock() == Blocks.ladder;

    return success;
  }

}
