/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.drive;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.command.Command;

/**
 *
 * @author leighpauls
 */
public class DriveForward implements Command {
    private int steps_remaining;

    public DriveForward(int num_steps) {
        steps_remaining = num_steps;
    }
    
    public boolean isDone() {
        return steps_remaining <= 0;
    }

    public void runCommandStep(Robot robot) {
        steps_remaining--;
        robot.getDrive().setDrive(0.0, 1.0);
    }
    
}
