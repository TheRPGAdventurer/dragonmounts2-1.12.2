///*
// ** 2012 August 23
// **
// ** The author disclaims copyright to this source code.  In place of
// ** a legal notice, here is a blessing:
// **    May you do good and not evil.
// **    May you find forgiveness for yourself and forgive others.
// **    May you share freely, never taking more than you give.
// */
//package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper;
//
//import com.TheRPGAdventurer.ROTD.util.math.Interpolation;
//import com.TheRPGAdventurer.ROTD.util.math.MathX;
//
///**
// * Enum for dragon life stages. Used as aliases for the age value of dragons.
// *
// * Refactored to DragonLifeStage
// *
// * @author Nico Bergemann <barracuda415 at yahoo.de>
// */
//public enum EnumDragonLifeStage {
//
//    EGG(0.25f),
//    HATCHLING(0.08f),
//    INFANT(0.18f),
//    PREJUVENILE(0.66f),
//    JUVENILE(1.00f),
//    ADULT(2.00f);
//
//    public static final int TICKS_PER_STAGE = 45000;
//    public static final EnumDragonLifeStage[] STAGE = values(); // cached for speed
//
//    public final float scale;
//
//    EnumDragonLifeStage(float scale) {
//        this.scale = scale;
//
//    }
//
//    public static int clampTickCount(int ticksSinceCreation) {
//        return MathX.clamps(ticksSinceCreation, 0, STAGE.length * TICKS_PER_STAGE);
//    }
//
//    public static EnumDragonLifeStage fromTickCount(int ticksSinceCreation) {
//        return STAGE[clampTickCount(ticksSinceCreation) / TICKS_PER_STAGE];
//    }
//
//    public static float progressFromTickCount(int ticksSinceCreation) {
//        EnumDragonLifeStage lifeStage = fromTickCount(ticksSinceCreation);
//        int lifeStageTicks = ticksSinceCreation - lifeStage.startTicks();
//        return lifeStageTicks / (float) TICKS_PER_STAGE;
//    }
//
//    public static float scaleFromTickCount(int ticksSinceCreation) {
//        EnumDragonLifeStage lifeStage = fromTickCount(ticksSinceCreation);
//
//        // constant size for egg and adult stage end growth if adult
//        if (lifeStage == EGG || lifeStage == ADULT) {
//            return lifeStage.scale;
//        }
//
//        // interpolated size between current and next stage
//        return Interpolation.linear(lifeStage.scale, lifeStage.next().scale,
//                progressFromTickCount(ticksSinceCreation));
//    }
//
//
//    public int startTicks() {
//        return ordinal() * TICKS_PER_STAGE;
//    }
//
//    public EnumDragonLifeStage next() {
//        return this == ADULT ? null : STAGE[ordinal() + 1];
//    }
//}

