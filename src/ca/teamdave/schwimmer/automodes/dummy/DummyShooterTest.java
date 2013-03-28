/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.dummy;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.automodes.competition.Back3Disk;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.meta.Delay;
import ca.teamdave.schwimmer.command.meta.Series;
import ca.teamdave.schwimmer.command.shooter.ShootDisks;
import ca.teamdave.schwimmer.command.shooter.ShooterStop;

/**
 *
 * @author leigh-pauls
 */
public class DummyShooterTest extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        return new Series(new Command[] {
            Back3Disk.getAsCommand(),
            new Delay(2.0),
            new ShootDisks(2),
            new Delay(2.0),
            new ShooterStop()
        });
    }

    public String getVisibleName() {
        return "Dummy shooter";
    }
    
}
