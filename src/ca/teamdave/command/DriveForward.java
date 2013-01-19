/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.command;

import ca.teamdave.RobotInterface;

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

    public void runCommandStep(RobotInterface robot) {
        steps_remaining--;
        robot.setDrive(0.0, 1.0);
    }
    
}
