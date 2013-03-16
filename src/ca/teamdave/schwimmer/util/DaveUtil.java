/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.util;

import com.sun.squawk.util.MathUtils;

/**
 *
 * @author leighpauls
 */
public class DaveUtil {
    public static double sign(double x) {
        return (x > 0) ? 1.0 : -1.0;
    }
    
    public static String toAccuracy(double val, int decimals) {
        double power = MathUtils.pow(10, decimals);
        return Double.toString(MathUtils.round(val * power) / power);
    }
    
    public static double limit(double val, double max) {
        max = Math.abs(max);
        return Math.max(-max, Math.min(max, val));
    }
}
