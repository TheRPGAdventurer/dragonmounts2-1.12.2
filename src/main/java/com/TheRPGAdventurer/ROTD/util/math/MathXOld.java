///*
// ** 2012 Januar 8
// **
// ** The author disclaims copyright to this source code.  In place of
// ** a legal notice, here is a blessing:
// **    May you do good and not evil.
// **    May you find forgiveness for yourself and forgive others.
// **    May you share freely, never taking more than you give.
// */
//package com.TheRPGAdventurer.ROTD.util.math;
//
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Vec3d;
//
//import java.util.Random;
//
///**
// * Math helper class.
// *
// * @author Nico Bergemann <barracuda415 at yahoo.de>
// */
//public class MathX {
//
//    public static final double PI_D = Math.PI;
//    public static final float PI_F = (float) Math.PI;
//    public static boolean useLUT = true;
//
//    /**
//     * You no take constructor!
//     */
//    private MathX() {
//    }
//
//    // float sine function, may use LUT
//    public static float sin(float a) {
//        return (float) Math.sin(a);
//    }
//
//    // float cosine function, may use LUT
//    public static float cos(float a) {
//        return (float) Math.cos(a);
//    }
//
//    // float tangent function
//    public static float tan(float a) {
//        return (float) Math.tan(a);
//    }
//
//    // float atan2 function
//    public static float atan2(float y, float x) {
//        return (float) Math.atan2(y, x);
//    }
//
//    // float degrees to radians conversion
//    public static float toRadians(float angdeg) {
//        return (float) Math.toRadians(angdeg);
//    }
//
//    // float radians to degrees conversion
//    public static float toDegrees(float angrad) {
//        return (float) Math.toDegrees(angrad);
//    }
//
//    // normalizes a float degrees angle to between +180 and -180
//    public static float normDeg(float a) {
//        a %= 360;
//        if (a >= 180) {
//            a -= 360;
//        }
//        if (a < -180) {
//            a += 360;
//        }
//        return a;
//    }
//
//    // normalizes a double degrees angle to between +180 and -180
//    public static double normDeg(double a) {
//        a %= 360;
//        if (a >= 180) {
//            a -= 360;
//        }
//        if (a < -180) {
//            a += 360;
//        }
//        return a;
//    }
//
//    // normalizes a float radians angle to between +π and -π
//    public static float normRad(float a) {
//        a %= PI_F * 2;
//        if (a >= PI_F) {
//            a -= PI_F * 2;
//        }
//        if (a < -PI_F) {
//            a += PI_F * 2;
//        }
//        return a;
//    }
//
//    // normalizes a double radians angle to between +π and -π
//    public static double normRad(double a) {
//        a %= PI_D * 2;
//        if (a >= PI_D) {
//            a -= PI_D * 2;
//        }
//        if (a < -PI_D) {
//            a += PI_D * 2;
//        }
//        return a;
//    }
//
//    /** return a random value from a truncated gaussian distribution with
//     *    mean and standard deviation = threeSigma/3
//     *  distribution is truncated to +/- threeSigma.
//     * @param mean the mean of the distribution
//     * @param threeSigma three times the standard deviation of the distribution
//     * @return
//     */
//    public static double getTruncatedGaussian(Random rand, double mean, double threeSigma)
//    {
//      double rawValue = rand.nextGaussian();
//      rawValue = MathHelper.clamp(rawValue, -3.0, +3.0);
//      return mean + rawValue * threeSigma / 3.0;
//    }
//
//    // float square root
//    public static float sqrtf(float f) {
//        return (float) Math.sqrt(f);
//    }
//
//    // numeric float clamp
//    public static float clamp(float value, float min, float max) {
//        return (value < min ? min : (value > max ? max : value));
//    }
//
//    // numeric double clamp
//    public static double clamp(double value, double min, double max) {
//        return (value < min ? min : (value > max ? max : value));
//    }
//
//    // numeric integer clamp
//    public static int clamp(int value, int min, int max) {
//        return (value < min ? min : (value > max ? max : value));
//    }
//
//    // numeric integer clamp
//    public static int clamps(int value, int min, int max) {
//        return (value < min ? min : (value >= max ? max : value));
//    }
//
//    public static float updateRotation(float r1, float r2, float step) {
//        return r1 + clamp(normDeg(r2 - r1), -step, step);
//    }
//
//    // float linear interpolation
//    public static float lerp(float a, float b, float x) {
//        return a * (1 - x) + b * x;
//    }
//
//    // double linear interpolation
//    public static double lerp(double a, double b, double x) {
//        return a * (1 - x) + b * x;
//    }
//
//    // smoothed float linear interpolation, similar to terp() but faster
//    public static float slerp(float a, float b, float x) {
//        if (x <= 0) {
//            return a;
//        }
//        if (x >= 1) {
//            return b;
//        }
//
////        return lerp(a, b, x * x * (3 - 2 * x)); im stupid ik
//        return lerp(a, b, x * x * x);
//    }
//
// // float trigonometric interpolation
//    public static float terp(float a, float b, float x) {
//        if (x <= 0) {
//            return a;
//        }
//        if (x >= 1) {
//            return b;
//        }
//
//        float mu2 = (1 - cos(x * PI_F)) / 2.0f;
//        return a * (1 - mu2) + b * mu2;
//    }
//
//    // double trigonometric interpolation
//    public static double terp(double a, double b, double x) {
//        if (x <= 0) {
//            return a;
//        }
//        if (x >= 1) {
//            return b;
//        }
//
//        double mu2 = (1 - Math.cos(x * PI_D)) / 2.0;
//        return a * (1 - mu2) + b * mu2;
//    }
//
//    /** clamp the target angle to within a given range of the centre angle
//     * @param targetAngle the desired angle (degrees)
//     * @param centreAngle the centre angle to clamp to (degrees)
//     * @param maximumDifference the maximum allowable difference between the target and the centre (degrees)
//     * @return the target angle, clamped to within +/-maximuDifference of the centreAngle.
//     */
//    public static float constrainAngle(float targetAngle, float centreAngle, float maximumDifference) {
//        return centreAngle + clamp(normDeg(targetAngle - centreAngle), -maximumDifference, maximumDifference);
//    }
//
//    public static Vec3d multiply(Vec3d source, double multiplier) {
//      return new Vec3d(source.x * multiplier, source.y * multiplier, source.z * multiplier);
//    }
//
//    public final static double MINIMUM_SIGNIFICANT_DIFFERENCE = 1e-3;
//
//    public static boolean isApproximatelyEqual(double x1, double x2) {
//
//      return Math.abs(x1 - x2) <= MINIMUM_SIGNIFICANT_DIFFERENCE;
//    }
//
//  public static boolean isSignificantlyDifferent(double x1, double x2) {
//    return Math.abs(x1 - x2) > MINIMUM_SIGNIFICANT_DIFFERENCE;
//  }
//
//    /** return the modulus (always positive)
//     * @param numerator
//     * @param divisor
//     * @return calculates the numerator modulus by divisor, always positive
//     */
//  public static int modulus(int numerator, int divisor) {
//      return (numerator % divisor + divisor) % divisor;
//  }
//
//  /**
//   * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
//   */
//  public static float wrapAngleTo180(float p_76142_0_)
//  {
//      p_76142_0_ %= 360.0F;
//
//      if (p_76142_0_ >= 180.0F)
//      {
//          p_76142_0_ -= 360.0F;
//      }
//
//      if (p_76142_0_ < -180.0F)
//      {
//          p_76142_0_ += 360.0F;
//      }
//
//      return p_76142_0_;
//  }
//
//  /**
//   * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
//   */
//  public static double wrapAngleTo180(double p_76138_0_)
//  {
//      p_76138_0_ %= 360.0D;
//
//      if (p_76138_0_ >= 180.0D)
//      {
//          p_76138_0_ -= 360.0D;
//      }
//
//      if (p_76138_0_ < -180.0D)
//      {
//          p_76138_0_ += 360.0D;
//      }
//
//      return p_76138_0_;
//  }
//
//  public static float invSqrt(float x) {
//	    float xhalf = 0.5f * x;
//	    int i = Float.floatToIntBits(x);
//	    i = 0x5f3759df - (i >> 1);
//	    x = Float.intBitsToFloat(i);
//	    x *= (1.5f - xhalf * x * x);
//	    return x;
//  }
//
//}
