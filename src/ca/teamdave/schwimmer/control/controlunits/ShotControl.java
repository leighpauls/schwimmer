/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.control.LinearPID;
import ca.teamdave.schwimmer.control.LinearPIDFF;
import ca.teamdave.schwimmer.interfaces.Hopper;
import ca.teamdave.schwimmer.interfaces.Shooter;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class ShotControl {
    private final LinearPIDFF mFrontControl;
    private final LinearPIDFF mBackControl;
    private boolean mEngaged;

    private boolean mAutoPunchEngaged;
    private int mAutoStateDelay;
    private String mAutoState;

    static final String STATE_WAIT_FOR_SPEED = "WAIT_FOR_SPEED";
    static final String STATE_WAIT_TO_WITHDRAW = "WAIT_TO_WITHDRAW";
    static final String STATE_WAIT_TO_PUNCH = "WAIT_TO_PUNCH";
    

    public ShotControl() {
        mFrontControl = Const.getInstance().pidFFFromConst(
                "shooter_front",
                0.00005, 0.000000001, 0.0, 10, 0.000035);
        mBackControl = Const.getInstance().pidFFFromConst(
                "shooter_back",
                0.0007, 0.000000001, 0.0, 10, 0.0001);

        mEngaged = false;
        
                
        mAutoPunchEngaged = false;
        mAutoStateDelay = 0;
        mAutoState = STATE_WAIT_FOR_SPEED;

    }
    
    public void engage() {
        // TODO: turn this into a muzzle velocity, and use 
        // the gear ratios to convert to proper pairs (3:1)
        mFrontControl.setSetPoint(
                Const.getInstance().getDouble(
                "shooter_front_speed", 22000));
        mBackControl.setSetPoint(
                Const.getInstance().getDouble(
                "shooter_back_speed", 10000));
        mEngaged = true;
    }
    
    public void disengage() {
        mFrontControl.setSetPoint(0);
        mEngaged = false;
    }
    
    public boolean isEngaged() {
        return mEngaged;
    }
    
    public boolean doCycle(Shooter shot, Hopper hopper, boolean forcePunch) {
        boolean res;
        if (mEngaged) {
            shot.setPower(
                    mFrontControl.computeCycle(shot.getFrontSpeed()),
                    mBackControl.computeCycle(shot.getBackSpeed()),
                    true);

            res = mFrontControl.isDone() && mBackControl.isDone();
        } else {
            shot.setPower(0.0, 0.0, false);
            res =  false;
        }
        
        autoPunch(mEngaged, hopper, forcePunch);
        
        return res;
    }
    
    public boolean isAtSpeed(double acceptedError) {
        if (!mEngaged) {
            return false;
        }
        acceptedError = Math.abs(acceptedError);
        return Math.abs(mFrontControl.getCurError()) < acceptedError 
                && Math.abs(mBackControl.getCurError()) < acceptedError;
    }
    
    
    final public void autoPunch(boolean engaged, Hopper hopper, boolean forcePunch) {
        if (forcePunch) {
            mAutoPunchEngaged = false;
            hopper.setPunch(true);
            return;
        }
        if (!engaged) {
            mAutoPunchEngaged = false;
            hopper.setPunch(false);
            return;
        }
        if (engaged && !mAutoPunchEngaged) {
            mAutoPunchEngaged = true;
            mAutoStateDelay = 0;
            mAutoState = STATE_WAIT_FOR_SPEED;
            System.out.println("Start Autopunch");
        }
        
        if (mAutoState == STATE_WAIT_FOR_SPEED) {
            hopper.setPunch(false);
            if (isAtSpeed(500.0)) {
                mAutoState = STATE_WAIT_TO_WITHDRAW;
                mAutoStateDelay = 10;
                System.out.println("Wait to waithdraw");
            }
        } else if (mAutoState == STATE_WAIT_TO_WITHDRAW) {
            mAutoStateDelay--;
            hopper.setPunch(true);
            if (mAutoStateDelay <= 0) {
                mAutoState = STATE_WAIT_TO_PUNCH;
                mAutoStateDelay = 10;
                System.out.println("Wait to punch");
            }
        } else {
            mAutoStateDelay--;
            hopper.setPunch(false);
            if (mAutoStateDelay <= 0) {
                mAutoState = STATE_WAIT_FOR_SPEED;
                mAutoStateDelay = 0;
                System.out.println("Wait for speed");
            }
        }
    }
    
}
