/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.test;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.DriveToPosition;
import ca.teamdave.schwimmer.command.drive.DriveToPositionReverse;
import ca.teamdave.schwimmer.command.drive.TurnToHeading;
import ca.teamdave.schwimmer.command.meta.Series;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class TestDriveToPos extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        return new Series(new Command[] {
            new TurnToHeading(-30),
            new DriveToPosition(DaveVector.fromXY(1, 2), 0.5),
            new DriveToPositionReverse(DaveVector.fromXY(1, 0), 0.5)
        });
    }

    public String getVisibleName() {
        return "Test Drive Position";
    }
    
}
