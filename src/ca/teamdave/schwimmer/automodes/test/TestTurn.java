/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.test;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.TurnToHeading;

/**
 *
 * @author leighpauls
 */
public class TestTurn extends AutoModeDescriptor {

    
    public String getVisibleName() {
        return "Test Turn";
    }

    public Command getTopLevelCommand() {
        return new TurnToHeading(90);
    }
    
}
