/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.shooter;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.command.Command;

/**
 *
 * @author leighpauls
 */
public class DummyShootDisk implements Command{
    
    private int mTicksLeft = 25;
    private boolean mFirstCycle = true;
    
    public void runCommandStep(RobotInterface robot) {
        if (mFirstCycle) {
            System.out.println("Fire Disk");
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