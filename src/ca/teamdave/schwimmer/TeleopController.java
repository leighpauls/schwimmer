/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer;

import ca.teamdave.schwimmer.control.controlunits.BaseLock;
import ca.teamdave.schwimmer.control.controlunits.Shooter;

/**
 *
 * @author leighpauls
 */
public class TeleopController {
    private final BaseLock mBaseLock;
    private boolean mBaseLockEngaged;
    private final Shooter mShooter;
    
    
    public TeleopController() {
        mBaseLock = new BaseLock();
        mShooter = new Shooter();
        mBaseLockEngaged = false;
    }
    
    public void initTeleopMode() {
        mBaseLockEngaged = false;
    }
    
    public void runTeleopCycle(RobotInterface robot) {
        if (robot.getDriver().isBaseLockButtonDown()) {
            if (!mBaseLockEngaged) {
                mBaseLock.engageLock(robot);
                mBaseLockEngaged = true;
            }
            mBaseLock.doCycle(robot);
        } else {
            mBaseLock.releaseLock();
            mBaseLockEngaged = false;
            robot.setDrive(robot.getDriver().getDriverX(), robot.getDriver().getDriverY());            
        }
        
        if (robot.getDriver().isShooterButtonDown()) {
            if (!mShooter.isEngaged()) {
                mShooter.engage();
            }
        } else if (mShooter.isEngaged()) {
            mShooter.disengage();
        }

        if (mShooter.isEngaged()) {
            boolean shooterSpun = mShooter.doCycle(robot);
            System.out.println("Shooter " + (shooterSpun ? "spun" : "spinning up"));
        } else {
            robot.setShooterPower(robot.getDriver().getDriverY(), 0.0);
        }
    }
}
