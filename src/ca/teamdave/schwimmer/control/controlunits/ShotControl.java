/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.control.LinearPID;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class ShotControl {
    private final LinearPID mFrontControl;
    private final LinearPID mBackControl;
    private boolean mEngaged;


    public ShotControl() {
        mFrontControl = Const.getInstance().pidFromConst(
                "shooter_front",
                0.001, 0.0, 0.0, 10);
        mBackControl = Const.getInstance().pidFromConst(
                "shooter_back",
                0.001, 0.0, 0.0, 10);

        mEngaged = false;
    }
    
    public void engage() {
        // TODO: turn this into a muzzle velocity, and use 
        // the gear ratios to convert to proper pairs (3:1)
        mFrontControl.setSetPoint(
                Const.getInstance().getDouble(
                "shooter_front_speed", 600));
        mBackControl.setSetPoint(
                Const.getInstance().getDouble(
                "shooter_back_speed", 200));
        mEngaged = true;
    }
    
    public void disengage() {
        mFrontControl.setSetPoint(0);
        mEngaged = false;
    }
    
    public boolean isEngaged() {
        return mEngaged;
    }
    
    public boolean doCycle(Robot robot) {
        if (mEngaged) {
            robot.getShooter().setPower(
                    mFrontControl.computeCycle(robot.getShooter().getFrontSpeed()),
                    mBackControl.computeCycle(robot.getShooter().getBackSpeed()));
            return mFrontControl.isDone() && mBackControl.isDone();
        } else {
            robot.getShooter().setPower(0.0, 0.0);
            return false;
        }
                
    }
    
}
