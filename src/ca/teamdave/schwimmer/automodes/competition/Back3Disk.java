/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.competition;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.DriveStop;
import ca.teamdave.schwimmer.command.hopper.HopperHeight;
import ca.teamdave.schwimmer.command.meta.Delay;
import ca.teamdave.schwimmer.command.meta.Latch;
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
            new DriveStop(),
            new Latch(new Command[] {
                new ShooterHeight(false),
                new HopperHeight(true)
            }),
            new ShootDisks(3),
            new Delay(1.0),
            new ShooterStop()
        });
    }
    
    public Command getTopLevelCommand() {
        return getAsCommand();
    }

    public String getVisibleName() {
        return "3 Stop";
    }
    
}
