/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.drive;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.interfaces.Robot;

/**
 *
 * @author leighpauls
 */
public class DriveStop implements Command {

    private boolean mDone = false;
    
    public void runCommandStep(Robot robot) {
        robot.getDrive().setDrive(0, 0);
        mDone = true;
    }

    public boolean isDone() {
        return mDone;
    }
    
}
