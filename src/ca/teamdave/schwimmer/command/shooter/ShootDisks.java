/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.shooter;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.control.controlunits.PunchControl;
import ca.teamdave.schwimmer.control.controlunits.ShotControl;
import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.interfaces.Shooter;

/**
 *
 * @author leighpauls
 */
public class ShootDisks implements Command {
    private final ShotControl mShooterControl;
    private final PunchControl mPunchControl;
    private final int mNumDisks;
    
    private boolean mFistCycle;
    private boolean mDone;
        
    public ShootDisks(int numDisks) {
        mShooterControl = new ShotControl();
        mPunchControl = new PunchControl(mShooterControl);
        mNumDisks = numDisks;
        
        mFistCycle = true;
        mDone = false;
    }

    public void runCommandStep(Robot robot) {
        if (mFistCycle) {
            mShooterControl.engage();
            mPunchControl.setStateAutoCount(mNumDisks);
            
            mFistCycle = false;
        }
        
        mShooterControl.doCycle(robot.getShooter());
        boolean punchFinished = mPunchControl.doCycle(robot.getHopper());
        if ((!mDone) && punchFinished) {
            mDone = true;
            mShooterControl.disengage();
        }
    }

    public boolean isDone() {
        return mDone;
    }
}
