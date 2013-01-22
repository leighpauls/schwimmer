/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.drive;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.control.controlunits.LineFollower;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 * Simply try to follow the defined line until the specified distance
 * @author leighpauls
 */
public class FollowLine implements Command {
    private final LineFollower mController;
    private boolean mIsDone;

    public FollowLine(
            DaveVector origin,
            DaveVector direction,
            double dist,
            double ForwardPower) {
        mController = new LineFollower(origin, direction, dist, ForwardPower);
        mIsDone = false;
    }
    
    public void runCommandStep(RobotInterface robot) {
        mIsDone = mController.doCycle(robot);
    }

    public boolean isDone() {
        return mIsDone;
    }
    
}
