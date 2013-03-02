/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes;

import ca.teamdave.schwimmer.automodes.dummy.Dummy7Disk;
import ca.teamdave.schwimmer.automodes.dummy.ArcMode;
import ca.teamdave.schwimmer.automodes.test.TestArc;
import ca.teamdave.schwimmer.automodes.test.TestDriveLine;
import ca.teamdave.schwimmer.automodes.test.TestDriveToEncoder;
import ca.teamdave.schwimmer.automodes.test.TestTurn;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.meta.Endless;

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
            new TestTurn(),
            new TestDriveToEncoder(),
            new TestArc(),
            new ArcMode(),
            new AutoModeDescriptor() {
                // Nothing command for safety
                public Command getTopLevelCommand() {
                    return new Endless();
                }
                public String getVisibleName() {
                    return "Nothing";
                }
            }
        };
    }
    
    public AutoModeDescriptor getDefault() {
        return mModeList[5];
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
