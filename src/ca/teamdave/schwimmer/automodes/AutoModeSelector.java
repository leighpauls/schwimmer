/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes;

import ca.teamdave.schwimmer.automodes.dummy.Dummy7Disk;
import ca.teamdave.schwimmer.automodes.test.TestDriveLine;
import ca.teamdave.schwimmer.automodes.test.TestTurn;
import ca.teamdave.schwimmer.command.Command;

/**
 *
 * @author leighpauls
 */
public class AutoModeSelector {
    private final AutoModeDescriptor[] mModeList;

    public AutoModeSelector() {
        mModeList = new AutoModeDescriptor[] {
            new Dummy7Disk(),
            new TestDriveLine(),
            new TestTurn()
        };
    }
    
    public AutoModeDescriptor getDefault() {
        return mModeList[0];
    }
    
    public AutoModeDescriptor selectFromAnalogRange(double analogInput) {
        int idx = (int)Math.floor(Math.abs(
                (analogInput / 2.0 + 0.5) * mModeList.length));
        if (idx >= mModeList.length) {
            idx = mModeList.length - 1;
        }
        
        return mModeList[idx];
    }
}
