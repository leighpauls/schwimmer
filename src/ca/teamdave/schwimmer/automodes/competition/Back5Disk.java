/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.competition;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.FollowLine;
import ca.teamdave.schwimmer.command.drive.TurnToHeading;
import ca.teamdave.schwimmer.command.hopper.HopperHeight;
import ca.teamdave.schwimmer.command.meta.Latch;
import ca.teamdave.schwimmer.command.meta.Series;
import ca.teamdave.schwimmer.command.pickup.PickupIntake;
import ca.teamdave.schwimmer.command.pickup.PickupSpit;
import ca.teamdave.schwimmer.command.pickup.PickupStop;
import ca.teamdave.schwimmer.command.shooter.ShootDisks;
import ca.teamdave.schwimmer.command.shooter.ShooterHeight;
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class Back5Disk extends AutoModeDescriptor {

    public static Command getAsCommand() {
        double driveAngle = Const.getInstance().getDouble(
                "5_disk_drive_angle", 25.0);
        double driveDistance = Const.getInstance().getDouble(
                "5_disk_drive_dist", 1.5);
        double drivePower = Const.getInstance().getDouble(
                "5_disk_drive_power", 0.5);
        
        return new Series(new Command[] {
            Back3Disk.getAsCommand(),
    
            // turn to disks and lower hopper
            new Latch(new Command[] {
                new TurnToHeading(driveAngle),
                new HopperHeight(false),
            }),
            
            // drive forward and pick up
            new Latch(new Command[] {
                new PickupIntake(),
                new FollowLine(
                DaveVector.fromXY(0, 0),
                DaveVector.fromFieldRadial(1, driveAngle),
                driveDistance,
                drivePower)
            }),
            
            // raise the shooter, aim, and spit any caught disks
            new Latch(new Command[] {
                new PickupSpit(),                
                new TurnToHeading(0),
                new ShooterHeight(true)
            }),
            
            // raise the hopper
            new Latch(new Command[] {
                // TODO: spin up the shooter wheels
                new HopperHeight(true),
                new PickupStop()
            }),
            
            // fire
            new ShootDisks(2)
        });
    }

    public Command getTopLevelCommand() {
        return getAsCommand();
    }

    public String getVisibleName() {
        return "Back 5 Disk";
    }
    
}
