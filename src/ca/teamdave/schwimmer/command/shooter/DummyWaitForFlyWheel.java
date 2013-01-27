/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.shooter;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class DummyWaitForFlyWheel implements Command {

    private int mTicksLeft;
    private boolean mFirstCycle;
    
    public DummyWaitForFlyWheel() {
        mTicksLeft = Const.getInstance().getInt(
                "dummy_flywheel_ticks", 2000 / 20);
        mFirstCycle = true;
    }
    
    public void runCommandStep(RobotInterface robot) {
        if (mFirstCycle) {
            System.out.println("Spnning up fly wheel");
            mFirstCycle = false;
        }
        if (mTicksLeft > 0) {
            mTicksLeft--;
        }
    }

    public boolean isDone() {
        return mTicksLeft <= 0;
    }
    
}
