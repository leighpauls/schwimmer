/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.competition;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.meta.Delay;
import ca.teamdave.schwimmer.command.meta.Series;
import ca.teamdave.schwimmer.command.shooter.ShootDisks;
import ca.teamdave.schwimmer.command.shooter.ShooterHeight;
import ca.teamdave.schwimmer.command.shooter.ShooterStop;


/**
 *
 * @author leighpauls
 */
public class Back3Disk extends AutoModeDescriptor {

    public static Command getAsCommand() {
        return new Series(new Command[] {
            new ShooterHeight(false),
            new ShootDisks(3),
            new Delay(0.3),
            new ShooterStop()
        });
    }
    
    public Command getTopLevelCommand() {
        return getAsCommand();
    }

    public String getVisibleName() {
        return "Back 3 Stop";
    }
    
}
