/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.meta;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.command.Command;

/**
 * Executes children commands in parallel, returns complete once all children 
 * have completed. Continues calling all commands until all have finished.
 * NoOp.tailWrapper may come in use when using sensitive commands with this 
 * class.
 * @author leighpauls
 */
public class Latch implements Command {
    private final Command[] mChildren;
    private boolean mIsDone;

    public Latch(Command[] children) {
        mChildren = children;
        mIsDone = false;
    }
    
    public void runCommandStep(RobotInterface robot) {
        mIsDone = true;
        for (int i = 0; i < mChildren.length; ++i) {
            mChildren[i].runCommandStep(robot);
            if (!mChildren[i].isDone()) {
                mIsDone = false;
            }
        }
    }

    public boolean isDone() {
        return mIsDone;
    }
    
}
