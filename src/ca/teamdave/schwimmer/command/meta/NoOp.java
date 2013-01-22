/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.meta;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.command.Command;

/**
 * Does nothing but complete it's self. Useful to use in series with completion-
 * sensitive commands within a Latch command.
 * @author leighpauls
 */
public class NoOp implements Command {

    public void runCommandStep(RobotInterface robot) { }

    public boolean isDone() {
        return true;
    }
    
    /**
     * Be VERY careful that the command you're tailing doesn't have significant
     * end-state issues (aka, anything to do with driving state)
     * @param cmd
     * @return A Series Command with cmd and a NoOp
     */
    public static Command tailWrapper(Command cmd) {
        return new Series(new Command[] {
            cmd,
            new NoOp()
        });
    }
}
