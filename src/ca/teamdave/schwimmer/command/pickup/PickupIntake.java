/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.pickup;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.interfaces.Feeder;
import ca.teamdave.schwimmer.interfaces.Robot;

/**
 *
 * @author leighpauls
 */
public class PickupIntake implements Command {

    private boolean mDone = false;
    
    public void runCommandStep(Robot robot) {
        robot.getFeeder().setFeederState(Feeder.STATE_IN);
        mDone = true;
    }

    public boolean isDone() {
        return mDone;
    }
    
}
