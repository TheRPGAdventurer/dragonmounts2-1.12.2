/*
 ** 2012 March 19
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.client.model.dragon.anim;

import com.TheRPGAdventurer.ROTD.util.math.Interpolation;
import com.TheRPGAdventurer.ROTD.util.math.MathX;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Very simple fixed size circular buffer implementation for animation purposes.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class CircularBuffer {

    private float buffer[];
    private int index = 0;

    public CircularBuffer(int size) {
        buffer = new float[size];
    }
    
    public void fill(float value) {
        Arrays.fill(buffer, value);
    }
    
    public void update(float value) {
        // move forward
        index++;
        
        // restart pointer at the end to form a virtual ring
        index %= buffer.length;
        
        buffer[index] = value;
    }
    
    public float get(float x, int offset) {
        int i = index - offset;
        int len = buffer.length - 1;
        return Interpolation.linear(buffer[i - 1 & len], buffer[i & len], x);
    }
    
    public float get(float x, int offset1, int offset2) {
        return get(x, offset2) - get(x, offset1);
    }
    
    /**
     * Retrieves a value from the buffer at a given age. Interpolates between the two nearest values
     * @param x interpolation value [0 to 1].  0 = newer value --> 1 = older value
     * @param offset the number of places to look back in time.  0 = most recent value.  must be 0 to buffersize-2 inclusive
     * @return the interpolated historical values
     */
    public double getInterpolatedValue(float x, int offset) {
        checkArgument(x >= 0.0F);
        checkArgument(x <= 1.0F);
        checkArgument(offset >= 0);
        checkArgument(offset <= buffer.length - 2);
        int newerIndex = MathX.modulus(index - offset, buffer.length);
        int olderIndex = MathX.modulus(index - offset - 1, buffer.length);
//        return MathX.lerp(buffer[i - 1 & len], buffer[i & len], x);  bug fix... & only works if buffer is a power of 2
        return MathX.lerp(buffer[newerIndex], buffer[olderIndex], x);
    }

    /**
     * Calculates the difference between two values from the buffer, [offset2 minus offset1]
     * @param x interpolation value [0 to 1].  0 = newer value --> 1 = older value
     * @param offset1 the number of places to look back in time.  0 = most recent value.  must be 0 to buffersize-2 inclusive
     * @param offset2 as offset1.
     * @return the difference between the two nominated historical values
     */
    public double getChangeInValue(float x, int offset1, int offset2) {
        return getInterpolatedValue(x, offset2) - getInterpolatedValue(x, offset1);
    }
}
