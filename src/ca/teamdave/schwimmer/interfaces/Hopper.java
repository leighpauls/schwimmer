/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author leighpauls
 */
public class Hopper {
    private final Solenoid mRaiserUp;
    private final Solenoid mRaiserDown;
    private final Solenoid mPunchExtend;
    private final Solenoid mPunchRetract;
    private boolean mIsRaiserUp;
    
    public Hopper() {
        mRaiserUp = new Solenoid(1, 5);
        mRaiserDown = new Solenoid(1, 6);
        
        mPunchExtend = new Solenoid(2, 1);
        mPunchRetract = new Solenoid(2, 2);
        
        setRaiser(true);
        setPunch(false);
    }
    
    final public void setRaiser(boolean up) {
        mRaiserUp.set(up);
        mRaiserDown.set(!up);
        
        // TODO: add a timer delay on this state
        mIsRaiserUp = up;
    }
    
    final public void setPunch(boolean extended) {
        if (!mIsRaiserUp && extended) {
            System.out.println("Tried to extend punch while hopper down");
            extended = false;
        }
        mPunchExtend.set(extended);
        mPunchRetract.set(!extended);
    }
    
    public boolean isSafeToIntake() {
        return !mIsRaiserUp;
    }
}
