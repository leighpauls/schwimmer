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
    
    private static int nextId = 0;
    
    private int mTicksLeft = 400 / 20;
    private boolean mFirstCycle = true;
    private int id;
    
    public DummyShootDisk() {
        id = nextId;
        nextId++;
        System.out.println("Made Shot: " + id);
    }
    
    public void runCommandStep(RobotInterface robot) {
        if (mFirstCycle) {
            System.out.println("Fire Disk: " + id);
            System.out.flush();
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
