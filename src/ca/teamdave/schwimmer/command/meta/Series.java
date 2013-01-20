/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.meta;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.command.Command;

/**
 * Executes children commands in series, returns complete once the last command
 * is complete
 * @author leighpauls
 */
public class Series implements Command {
    private final Command[] mChildren;
    private int mCurCommand;

    public Series(Command[] children) {
        mChildren = children;
        mCurCommand = 0;
    }
    
    public void runCommandStep(RobotInterface robot) {
        if (mCurCommand >= mChildren.length) {
            return;
        }
        
        mChildren[mCurCommand].runCommandStep(robot);
        if (mChildren[mCurCommand].isDone()) {
            mCurCommand++;
        }
    }

    public boolean isDone() {
        return mCurCommand >= mChildren.length;
    }
}