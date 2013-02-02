/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control;

/**
 *
 * @author leighpauls
 */
public class LinearPID {
    private double mP;
    private double mI;
    private double mD;
    private double mAcceptableError;
    private boolean mDone;
    private double mSetPoint;
    private double mLastError;
    private double mIntegration;
    private boolean mFirstCycle;
    
    public LinearPID(double p, double i, double d, double acceptableError) {
        setControlConstants(p, i, d, acceptableError);
        
        mDone = false;
        mSetPoint = 0.0;
        mLastError = 0.0;
    }
    
    public void setSetPoint(double newSetPoint) {
        setSetPoint(newSetPoint, false);
    }
    
    public void setSetPoint(double newSetPoint, boolean softReset) {
        mSetPoint = newSetPoint;
        if (!softReset) {
            mIntegration = 0.0;
            mFirstCycle = true;
        }
    }
    
    public void setControlConstants(double p, double i, double d, double acceptableError) {
        mP = Math.abs(p);
        mI = Math.abs(i);
        mD = Math.abs(d);
        mAcceptableError = Math.abs(acceptableError);
        mIntegration = 0.0;
        mFirstCycle = true;
    }

    public double computeCycle(double curPosition) {
        double output = 0.0;
        
        double error = curPosition - mSetPoint;
        output -= mP * error;
        
        // integral
        mIntegration += error;
        output -= mI * mIntegration;
        
        double change = mFirstCycle ? 0.0 : (error - mLastError);
        mLastError = error;
        mFirstCycle = false;
        
        output -= mD * change;
        
        mDone = Math.abs(error) < mAcceptableError;
        
        return output;
    }
    
    public boolean isDone() {
        return mDone;
    }
    
}
