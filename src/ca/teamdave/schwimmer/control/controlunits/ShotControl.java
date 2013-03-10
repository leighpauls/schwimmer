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

    public ShotControl() {
        mFrontControl = Const.getInstance().pidFFFromConst(
                "shooter_front",
                0.00005, 0.000000001, 0.0, 500, 0.000035);
        mBackControl = Const.getInstance().pidFFFromConst(
                "shooter_back",
                0.0007, 0.000000001, 0.0, 500, 0.0001);

        mEngaged = false;
        
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
        mBackControl.setSetPoint(0);
        mEngaged = false;
    }
    
    public boolean isEngaged() {
        return mEngaged;
    }
    
    public void doCycle(Shooter shot) {
        if (mEngaged) {
            shot.setPower(
                    mFrontControl.computeCycle(shot.getFrontSpeed()),
                    mBackControl.computeCycle(shot.getBackSpeed()),
                    true);
        } else {
            shot.setPower(0.0, 0.0, false);
        }
    }
    
    public boolean isAtSpeed() {
        if (!mEngaged) {
            return false;
        }
        return mFrontControl.isDone() && mBackControl.isDone();
    }
    
}
