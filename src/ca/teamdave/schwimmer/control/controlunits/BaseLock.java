/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.RobotInterface;

/**
 *
 * @author leighpauls
 */
public class BaseLock {

    private boolean mEngaged;
    private final DriveController mDriveController;
    private double mBaseHeading;
    private double mBaseEncoder;
    
    public BaseLock() {
        mEngaged = false;
        mDriveController = new DriveController();
    }
    
    private double getHeadingTrim(RobotInterface robot) {
        return -robot.getDriverX() * 45;
    }
    
    public void engageLock(RobotInterface robot) {
        mEngaged = true;
        mBaseHeading = robot.getHeading();
        mBaseEncoder = robot.getEncoderAverage();
    }
    
    public void releaseLock() {
        mEngaged = false;
    }
    
    public void doCycle(RobotInterface robot) {
        if (!mEngaged) {
            System.out.println("Running while not engaged");
            return;
        }
        mDriveController.setControlDestination(mBaseEncoder, mBaseHeading + getHeadingTrim(robot));
        mDriveController.doCycle(robot);
    }
}
