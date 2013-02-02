/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.control.LinearPID;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class Shooter {
    private final LinearPID mFrontControl;
    private final LinearPID mBackControl;
    private boolean mEngaged;


    public Shooter() {
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
    
    public boolean doCycle(RobotInterface robot) {
        if (mEngaged) {
            robot.setShooterPower(
                    mFrontControl.computeCycle(robot.getFrontShooterSpeed()),
                    mBackControl.computeCycle(robot.getBackShooterSpeed()));
            return mFrontControl.isDone() && mBackControl.isDone();
        } else {
            robot.setShooterPower(0.0, 0.0);
            return false;
        }
                
    }
    
}
