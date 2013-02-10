/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.control.controlunits.BaseLock;
import ca.teamdave.schwimmer.control.controlunits.ShotControl;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class TeleopController {
    private final BaseLock mBaseLock;
    private boolean mBaseLockEngaged;
    private final ShotControl mShooter;
    
    
    public TeleopController() {
        mBaseLock = new BaseLock();
        mShooter = new ShotControl();
        mBaseLockEngaged = false;
    }
    
    public void initTeleopMode() {
        mBaseLockEngaged = false;
    }
    
    public void runTeleopCycle(Robot robot) {
        if (robot.getOperators().isBaseLockButtonDown()) {
            if (!mBaseLockEngaged) {
                mBaseLock.engageLock(robot);
                mBaseLockEngaged = true;
            }
            mBaseLock.doCycle(robot);
        } else {
            mBaseLock.releaseLock();
            mBaseLockEngaged = false;
            robot.getDrive().setDrive(robot.getOperators().getDriverX(), robot.getOperators().getDriverY());            
        }
        
        
        if (robot.getOperators().isShooterButtonDown()) {
            robot.getShooter().setPower(
                    Const.getInstance().getDouble("shooter_front_ff", 0.9),
                    Const.getInstance().getDouble("shoorter_back_ff", 0.9));
        } else {
            robot.getShooter().setPower(0, 0);
        }

    }
}
