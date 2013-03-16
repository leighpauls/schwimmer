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
public class DaveVector {
    private double mX;
    private double mY;

    public static DaveVector fromXY(double x, double y) {
        DaveVector res = new DaveVector();
        res.setXY(x, y);
        return res;
    }
    
    public static DaveVector fromFieldRadial(double dist, double headingDegrees) {
        DaveVector res = new DaveVector();
        res.setFieldRadial(dist, headingDegrees);
        return res;
    }
    
    private DaveVector() {}
    
    public double getX() {
        return mX;
    }
    public double getY() {
        return mY;
    }
    
    public void setXY(double x, double y) {
        mX = x;
        mY = y;
    }
    
    public void moveXY(double x, double y) {
        mX += x;
        mY += y;
    }
    
    /**
     * Set the vector using Field Radial co-ordinates (0 degrees is the +Y axis
     * and 90 degrees is the -X axis)
     * @param dist distance from origin
     * @param headingDegrees counter-clockwise degrees away from the +Y axis
     */
    public void setFieldRadial(double dist, double headingDegrees) {
        mX = dist * (-Math.sin(headingDegrees * Math.PI / 180.0));
        mY = dist * (Math.cos(headingDegrees * Math.PI / 180.0));
    }
    
    public void moveFieldRadial(double dist, double headingDegrees) {
        mX += dist * (-Math.sin(headingDegrees * Math.PI / 180.0));
        mY += dist * (Math.cos(headingDegrees * Math.PI / 180.0));
    }
    public double getFieldAngle() {
        return MathUtils.atan2(-mX, mY) * 180.0 / Math.PI;
    }
    public double getMagnitude() {
        return Math.sqrt(mX*mX + mY*mY);
    }
    
    public double directionalDistanceToLine(DaveVector origin, DaveVector direction) {
        DaveVector hypotenuse = DaveVector.fromXY(
                this.mX - origin.mX,
                this.mY - origin.mY);
        double angle = hypotenuse.getFieldAngle() - direction.getFieldAngle();
        return hypotenuse.getMagnitude() * Math.sin(angle * Math.PI / 180.0);
    }
    
    public DaveVector add(DaveVector other) {
        return DaveVector.fromXY(mX + other.mX, mY + other.mY);
    }

    public DaveVector multiply(double factor) {
        return DaveVector.fromXY(mX * factor, mY * factor);
    }
    
    public DaveVector vectorTo(DaveVector to) {
        return DaveVector.fromXY(to.mX - mX, to.mY - mY);
    }
    
    public String toString() {
        return "Vector X: " + mX + ", Y: " + mY;
    }

    public void println(String label) {
        System.out.println(label + toString());
    }
}
