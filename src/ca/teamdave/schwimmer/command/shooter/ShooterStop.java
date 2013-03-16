/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.shooter;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.interfaces.Robot;

/**
 *
 * @author leighpauls
 */
public class ShooterStop implements Command {
    
    private boolean mDone = false;
    public void runCommandStep(Robot robot) {
        robot.getShooter().setPower(0.0, 0.0, false);
        mDone = true;
    }

    public boolean isDone() {
        return mDone;
    }
    
}
