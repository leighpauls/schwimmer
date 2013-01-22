/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.drive;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.control.controlunits.TurnController;

/**
 *
 * @author leighpauls
 */
public class TurnToHeading implements Command {
    private final TurnController mControl;
    private boolean mIsDone;

    public TurnToHeading(double destHeading) {
        mControl = new TurnController(destHeading);
        mIsDone = false;
    }
    
    public void runCommandStep(RobotInterface robot) {
        mIsDone = mControl.doCycle(robot);
    }

    public boolean isDone() {
        return mIsDone;
    }
    
    
}
