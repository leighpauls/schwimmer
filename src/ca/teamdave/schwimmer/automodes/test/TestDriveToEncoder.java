/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.test;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.DriveToEncoder;
import ca.teamdave.schwimmer.command.meta.Endless;
import ca.teamdave.schwimmer.command.meta.Latch;

/**
 *
 * @author leighpauls
 */
public class TestDriveToEncoder extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        return new Latch(new Command[] {
            new DriveToEncoder(1.0),
            new Endless()
        });
    }

    public String getVisibleName() {
        return "Drive to Encoder";
    }
    
}
