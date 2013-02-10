/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.meta;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.command.Command;

/**
 *
 * @author leighpauls
 */
public class Endless implements Command {

    public void runCommandStep(Robot robot) { }

    public boolean isDone() {
        return false;
    }
    
}
