/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.Stop;

/**
 *
 * @author leighpauls
 */
public class AutoController {
    
    private Stop mSafetyStop = new Stop();
    private Command mTopLevelCommand;
    private boolean mDone;
    
    public void initAutoMode(AutoModeDescriptor descriptor) {
        mTopLevelCommand = descriptor.getTopLevelCommand();
        mDone = false;
    }
    
    public void runAutoStep(RobotInterface robot) {
        // make sure I don't crap out if a list ends
        if (mDone) {
            mSafetyStop.runCommandStep(robot);
            return;
        }
        
        mTopLevelCommand.runCommandStep(robot);
        // totoally stop running on the first "done" signal
        if (mTopLevelCommand.isDone()) {
            mDone = true;
        }

    }
}
