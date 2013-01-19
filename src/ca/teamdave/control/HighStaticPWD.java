/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.control;

/**
 *
 * @author leighpauls
 */
public class HighStaticPWD {
    private double p;
    private double w;
    private double d;
    private double acceptableError;
    private double setPoint;
    private boolean firstCycle;
    private double lastPosition;
    private int errorCount;
    
    public HighStaticPWD(double p, double w, double d,
            double acceptableError) {
        setControlConstants(p, w, d, acceptableError);
        
        setPoint = 0.0;
        lastPosition = 0.0;
    }
    
    public void setSetPoint(double newSetPoint) {
        setPoint = newSetPoint;
        errorCount = 0;
        firstCycle = true;
    }
    
    public final void setControlConstants(double p, double w, double d, double acceptableErrror) {
        this.p = Math.abs(p);
        this.w = Math.abs(w);
        this.d = Math.abs(d);
        this.acceptableError = Math.abs(acceptableErrror);
        errorCount = 0;
        firstCycle = true;
    }
    
    public double computeCycle(double curPosition) {
        double output = 0.0;

        // Proportional contribuion
        double error = curPosition - setPoint;
        output -= p * error;
        
        // Wait contribution
        if (Math.abs(error) < acceptableError || (error * errorCount < 0)) {
            errorCount = 0;
        } else {
            errorCount += (error > 0) ? 1 : -1;
        }
        
        output -= w * errorCount;
        
        // Derivitive contribution
        double change = firstCycle ? 0.0 : (curPosition - lastPosition);
        lastPosition = curPosition;
        firstCycle = false;
        
        output -= d * change;

        return output;
    }
}
