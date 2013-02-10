/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.test;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.TurnToHeading;
import ca.teamdave.schwimmer.command.meta.Endless;
import ca.teamdave.schwimmer.command.meta.Latch;

/**
 *
 * @author leighpauls
 */
public class TestTurn extends AutoModeDescriptor {

    
    public String getVisibleName() {
        return "Test Turn";
    }

    public Command getTopLevelCommand() {
        return new Latch(new Command[] {
                new TurnToHeading(90),
                new Endless()
        });
    }
    
}
