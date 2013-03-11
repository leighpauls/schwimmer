/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.shooter;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.interfaces.Robot;

/**
 *
 * @author leighpauls
 */
public class ShooterHeight implements Command {
    private final boolean mUp;
    private boolean mDone;
    

    public ShooterHeight(boolean up) {
        mUp = up;
        mDone = false;
    }
    
    public void runCommandStep(Robot robot) {
        if (!mDone) {
            robot.getShooter().setRaiser(mUp);
        }
        mDone = true;
    }

    public boolean isDone() {
        return mDone;
    }
   
    
}
