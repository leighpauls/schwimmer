/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.test;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.FollowArc;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class TestArc extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        return new FollowArc(
                DaveVector.fromXY(0, 0),
                DaveVector.fromXY(0, 1),
                1.0, 3, 0.5);
    }

    public String getVisibleName() {
        return "test arc";
    }
    
}
