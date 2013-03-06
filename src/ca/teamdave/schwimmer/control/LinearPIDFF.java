/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control;

/**
 *
 * @author leighpauls
 */
public class LinearPIDFF {
    private final LinearPID mPID;
    
    private double mFF;
    private double mSetPoint;

    public LinearPIDFF(double p, double i, double d, double acceptableError, double ff) {
        mPID = new LinearPID(p, i, d, acceptableError);
        mFF = ff;
        mSetPoint = 0.0;
    }
    
    public void setSetPoint(double newSetPoint) {
        mPID.setSetPoint(newSetPoint);
        mSetPoint = newSetPoint;
    }
    
    public void setControlConstants(double p, double i, double d, double error, double ff) {
        mPID.setControlConstants(p, i, d, error);
        mFF = ff;
    }
    
    public double computeCycle(double curPosition) {
        double output = mPID.computeCycle(curPosition);
        output += mFF * mSetPoint;
        return output;
    }
    
    public boolean isDone() {
        return mPID.isDone();
    }
    
    public double getCurError() {
        return mPID.getCurError();
    }
}
