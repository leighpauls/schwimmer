/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer;

import ca.teamdave.schwimmer.control.controlunits.BaseLock;

/**
 *
 * @author leighpauls
 */
public class TeleopController {
    private final BaseLock mBaseLock;
    private boolean mBaseLockEngaged;
    
    
    public TeleopController() {
        mBaseLock = new BaseLock();
        mBaseLockEngaged = false;
    }
    
    public void initTeleopMode() {
        mBaseLockEngaged = false;
    }
    
    public void runTeleopCycle(RobotInterface robot) {
        if (robot.isBaseLockButtonDown()) {
            if (!mBaseLockEngaged) {
                mBaseLock.engageLock(robot);
                mBaseLockEngaged = true;
            }
            mBaseLock.doCycle(robot);
        } else {
            mBaseLock.releaseLock();
            mBaseLockEngaged = false;
            robot.setDrive(robot.getDriverX(), robot.getDriverY());            
        }
    }
}
