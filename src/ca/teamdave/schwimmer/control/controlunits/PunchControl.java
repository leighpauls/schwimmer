/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.interfaces.Hopper;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class PunchControl {
    private final ShotControl mShotControl;
    
    private boolean mAutoPunchEngaged;
    private int mAutoStateDelay;
    private String mAutoState;

    static final String STATE_WAIT_FOR_SPEED = "WAIT_FOR_SPEED";
    static final String STATE_WAIT_TO_WITHDRAW = "WAIT_TO_WITHDRAW";
    static final String STATE_WAIT_TO_PUNCH = "WAIT_TO_PUNCH";
    
    private boolean mManualOut;
    private int mDisksToShoot;
    private boolean mExitOnDiskCount;

    
    public PunchControl(ShotControl shotControl) {
        mShotControl = shotControl;
        
        mAutoPunchEngaged = false;
        mAutoStateDelay = 0;
        mAutoState = STATE_WAIT_FOR_SPEED;
        mManualOut = false;
        mExitOnDiskCount = false;
    }
    
    public void setStateAutoContinuous() {
        setStateAutoCount(-1);
    }
    
    public void setStateAutoCount(int numDisks) {
        mManualOut = false;
        if (numDisks >= 0) {
            mDisksToShoot = numDisks;
            mExitOnDiskCount = true;
        } else {
            mExitOnDiskCount = false;
        }

        if (!mAutoPunchEngaged) {
            mAutoState = STATE_WAIT_FOR_SPEED;
            mAutoPunchEngaged = true;
        }
                
    }
    
    public void setStateManual(boolean punchOut) {
        mManualOut = punchOut;
        mAutoPunchEngaged = false;
    }
    
    public boolean doCycle(Hopper hopper) {
        if (!mAutoPunchEngaged) {
            hopper.setPunch(mManualOut);
            return true;
        }
        
        doPunchStateTransfer(hopper);
        
        if (mExitOnDiskCount && mDisksToShoot <= 0) {
            setStateManual(false);
            return true;
        }
        
        return false;
    }
    
    private void doPunchStateTransfer(Hopper hopper) {        
        if (mAutoState == STATE_WAIT_FOR_SPEED) {
            // Waiting for the shooter to get up to speed
            hopper.setPunch(false);
            
            if (mShotControl.isAtSpeed()) {
                // I'm at speed, start punching
                mAutoState = STATE_WAIT_TO_WITHDRAW;
                mAutoStateDelay = Const.getInstance().getInt(
                        "auto_punch_out_delay", 10);
                System.out.println("Wait to waithdraw");
            }
        }
        
        if (mAutoState == STATE_WAIT_TO_WITHDRAW) {
            // Waiting for the punch to fully extend
            mAutoStateDelay--;
            hopper.setPunch(true);
            
            if (mAutoStateDelay <= 0) {
                // The punch has finished extending, retract it
                mAutoState = STATE_WAIT_TO_PUNCH;
                mAutoStateDelay = Const.getInstance().getInt(
                        "auto_punch_in_delay", 10);
                System.out.println("Wait to punch");
                
                // The disk has been fully shot by now
                mDisksToShoot--;
            }
        } 
        
        if (mAutoState == STATE_WAIT_TO_PUNCH) {
            // Waiting for the punch to fully retract
            mAutoStateDelay--;
            hopper.setPunch(false);
            
            if (mAutoStateDelay <= 0) {
                // The punch has retracted fully
                mAutoState = STATE_WAIT_FOR_SPEED;
                mAutoStateDelay = 0;
                System.out.println("Wait for speed");
            }
        }
    }
}
