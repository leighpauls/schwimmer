/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave;

import ca.teamdave.command.Command;
import ca.teamdave.command.DriveForward;
import ca.teamdave.command.DriveToDistance;
import ca.teamdave.command.Stop;

/**
 *
 * @author leighpauls
 */
public class AutoController {
    
    private Command commands[];
    
    private int curCommand;
    
    public void initAutoMode() {
        // TODO: actually select from a list of command lists
        curCommand = 0;
        commands = new Command[] {
            new DriveToDistance(100),
            new Stop()
        };
    }
    
    public void runAutoStep(RobotInterface robot) {
        commands[curCommand].runCommandStep(robot);
        if (commands[curCommand].isDone()) {
            curCommand++;
        }
    }
}
