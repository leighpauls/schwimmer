/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.shooter.dummy;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class DummyShootDisk implements Command{
    
    private int mTicksLeft;
    private boolean mFirstCycle;
    
    public DummyShootDisk() {
        mTicksLeft = Const.getInstance().getInt("dummy_shoot_ticks", 400/20);
        mFirstCycle = true;
    }
    
    public void runCommandStep(Robot robot) {
        if (mFirstCycle) {
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
