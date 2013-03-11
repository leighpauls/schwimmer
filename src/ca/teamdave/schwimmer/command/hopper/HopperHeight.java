/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.hopper;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class HopperHeight implements Command {
    private int mCyclesRemaining;
    private final boolean mUp;
    public HopperHeight(boolean up) {
        mUp = up;
        mCyclesRemaining = Const.getInstance().getInt("hopper_move_cycles", 25);
    }

    public void runCommandStep(Robot robot) {
        robot.getHopper().setRaiser(mUp);
        if (mCyclesRemaining > 0) {
            mCyclesRemaining--;
        }
    }

    public boolean isDone() {
        return mCyclesRemaining <= 0;
    }
    
}
