/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.meta;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.interfaces.Robot;

/**
 *
 * @author leighpauls
 */
public class Delay implements Command {
    private int mCycles;

    public Delay(double seconds) {
        mCycles = (int)(seconds * 50);
    }
    
    public void runCommandStep(Robot robot) {
        if (mCycles > 0) {
            mCycles -= 1;
        }
    }

    public boolean isDone() {
        return mCycles <= 0;
    }
    
}
