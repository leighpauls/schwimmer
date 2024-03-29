/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control;

/**
 *
 * @author leighpauls
 */
public class HighStaticPWD {
    private double mP;
    private double mW;
    private double mD;
    private double mAcceptableError;
    private double mSetPoint;
    private boolean mFirstCycle;
    private double mLastError;
    private int mErrorCount;
    private boolean mDone;
    
    public HighStaticPWD(double p, double w, double d,
            double acceptableError) {
        setControlConstants(p, w, d, acceptableError);

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
            mErrorCount = 0;
            mFirstCycle = true;
        }
    }
    
    public final void setControlConstants(double p, double w, double d, double acceptableErrror) {
        mP = Math.abs(p);
        mW = Math.abs(w);
        mD = Math.abs(d);
        mAcceptableError = Math.abs(acceptableErrror);
        mErrorCount = 0;
        mFirstCycle = true;
    }
    
    public double computeCycle(double curPosition) {
        double output = 0.0;

        // Proportional contribuion
        double error = curPosition - mSetPoint;
        output -= mP * error;
        
        // Wait contribution
        if (Math.abs(error) < mAcceptableError || (error * mErrorCount < 0)) {
            mErrorCount = 0;
        } else {
            mErrorCount += (error > 0) ? 1 : -1;
        }
        
        output -= mW * mErrorCount;
        
        // Derivitive contribution
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
