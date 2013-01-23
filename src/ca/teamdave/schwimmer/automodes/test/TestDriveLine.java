/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.test;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.FollowLine;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class TestDriveLine extends AutoModeDescriptor{
    public String getVisibleName() {
        return "Test Drive Line";
    }

    
    public Command getTopLevelCommand() {
        return new FollowLine(
                DaveVector.fromXY(0.5, 0),
                DaveVector.fromXY(0, 1),
                10.0,
                1.0);
    }
    
}
