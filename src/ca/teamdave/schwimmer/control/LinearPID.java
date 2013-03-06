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
    private double mCurError;
    
    public LinearPID(double p, double i, double d, double acceptableError) {
        setControlConstants(p, i, d, acceptableError);
        
        mDone = false;
        mSetPoint = 0.0;
        mLastError = 0.0;
        mCurError = 0.0;
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
        
        mCurError = curPosition - mSetPoint;
        output -= mP * mCurError;
        
        // integral
        mIntegration += mCurError;
        output -= mI * mIntegration;
        
        double change = mFirstCycle ? 0.0 : (mCurError - mLastError);
        mLastError = mCurError;
        mFirstCycle = false;
        
        output -= mD * change;
        
        mDone = Math.abs(mCurError) < mAcceptableError;
        
        return output;
    }
    
    public boolean isDone() {
        return mDone;
    }
 
    public double getCurError() {
        return mCurError;
    }
}
