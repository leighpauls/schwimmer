/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.competition;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.hopper.HopperHeight;
import ca.teamdave.schwimmer.command.meta.Delay;
import ca.teamdave.schwimmer.command.meta.Latch;
import ca.teamdave.schwimmer.command.meta.Series;
import ca.teamdave.schwimmer.command.shooter.ShootDisks;
import ca.teamdave.schwimmer.command.shooter.ShooterHeight;
import ca.teamdave.schwimmer.command.shooter.ShooterStop;

/**
 *
 * @author leigh-pauls
 */
public class Front2Disk extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        return new Series(new Command[] {
           new Latch(new Command[] {
               new HopperHeight(true),
               new ShooterHeight(true)
           }),
           new ShootDisks(2),
           new Delay(0.5),
           new ShooterStop()
        });
    }

    public String getVisibleName() {
        return "Front 2 Disk";
    }
    
}
