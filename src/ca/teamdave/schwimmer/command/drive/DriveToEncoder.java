/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.drive;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.control.controlunits.DriveController;

/**
 *
 * @author leighpauls
 */
public class DriveToEncoder implements Command {
    private final DriveController mDriveController;
    private boolean mIsDone;

    public DriveToEncoder(double encoderPos) {
        mDriveController = new DriveController();
        mDriveController.setControlDestination(encoderPos, 0.0);
        mIsDone = false;
    }
    
    public void runCommandStep(Robot robot) {
        mIsDone = mDriveController.doCycle(robot);
    }

    public boolean isDone() {
        return mIsDone;
    }
    
}
