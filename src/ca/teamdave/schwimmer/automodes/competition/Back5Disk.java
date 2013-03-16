/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.competition;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.DriveStop;
import ca.teamdave.schwimmer.command.drive.DriveToPosition;
import ca.teamdave.schwimmer.command.drive.FollowLine;
import ca.teamdave.schwimmer.command.drive.TurnToHeading;
import ca.teamdave.schwimmer.command.hopper.HopperHeight;
import ca.teamdave.schwimmer.command.meta.Delay;
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
        double turnAngle = Const.getInstance().getDouble(
                "5_disk_turn_angle", 40);
        double driveAngle = Const.getInstance().getDouble(
                "5_disk_drive_angle", 30.0);
        double driveDistance = Const.getInstance().getDouble(
                "5_disk_drive_dist", 1.5);
        double drivePower = Const.getInstance().getDouble(
                "5_disk_drive_power", 0.5);
        
        double closeShotAngle = Const.getInstance().getDouble(
                "5_disk_close_angle", -10);
        
        return new Series(new Command[] {
            Back3Disk.getAsCommand(),
    
            // turn to disks and lower hopper
            new Latch(new Command[] {
                new TurnToHeading(turnAngle),
                new HopperHeight(false),
            }),
            
            new TurnToHeading(driveAngle),
            
            // drive forward and pick up
            new Latch(new Command[] {
                new PickupIntake(),
                // TODO: replace this with DriveToPosition
                new DriveToPosition(
                DaveVector.fromFieldRadial(driveDistance, driveAngle),
                drivePower
                )
            }),
            
            new DriveStop(),
            new Delay(1.0),
            
            // raise the shooter, aim, and spit any caught disks
            new Latch(new Command[] {
                new PickupSpit(),        
                new TurnToHeading(closeShotAngle),
                new ShooterHeight(true)
            }),
            
            new DriveStop(),
            
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
