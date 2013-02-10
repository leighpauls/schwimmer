/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command;

import ca.teamdave.schwimmer.interfaces.Robot;

/**
 * Stops everything
 * @author leighpauls
 */
public class Stop implements Command {

    public void runCommandStep(Robot robot) {
        robot.getDrive().setDrive(0.0, 0.0);
    }

    public boolean isDone() {
        return false;
    }
    
}
