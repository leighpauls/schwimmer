/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes;

import ca.teamdave.schwimmer.automodes.competition.Back3Disk;
import ca.teamdave.schwimmer.automodes.competition.Back3Drive;
import ca.teamdave.schwimmer.automodes.competition.Back5Disk;
import ca.teamdave.schwimmer.automodes.competition.Back5Drive;
import ca.teamdave.schwimmer.automodes.competition.Back5Pickup;
import ca.teamdave.schwimmer.automodes.competition.Front2Disk;
import ca.teamdave.schwimmer.automodes.dummy.DummyShooterTest;
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
            new DummyShooterTest(),
            new Front2Disk(),
            new Back3Disk(),
            new Back3Drive(),
            new Back5Disk(),
            new Back5Pickup(),
            new Back5Drive(true),
            new Back5Drive(false),
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
        return mModeList[3];
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
