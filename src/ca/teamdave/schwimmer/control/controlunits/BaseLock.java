/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.interfaces.Drive;
import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.util.Const;

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
    
    private double getHeadingTrim(Robot robot) {
        double maxTrim = Const.getInstance().getDouble(
                "base_lock_max_trim", 45);
        return -robot.getOperators().getDriverX() * maxTrim;
    }
    
    public void engageLock(Robot robot) {
        mEngaged = true;
        Drive d = robot.getDrive();
        mBaseHeading = d.getHeading();
        mBaseEncoder = d.getEncoderAverage();
    }
    
    public void releaseLock() {
        mEngaged = false;
    }
    
    public void doCycle(Robot robot) {
        if (!mEngaged) {
            System.out.println("Running while not engaged");
            return;
        }
        mDriveController.setControlDestination(mBaseEncoder, mBaseHeading + getHeadingTrim(robot));
        mDriveController.doCycle(robot);
    }
}
