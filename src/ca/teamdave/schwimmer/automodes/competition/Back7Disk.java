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
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class Back7Disk extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        Const c = Const.getInstance();
        double driveAngle = c.getDouble(
                "7_disk_drive_angle", -25);
        double driveDistance = c.getDouble(
                "7_disk_drive_dist", 1.5);
        double drivePower = c.getDouble(
                "7_disk_drive_power", 0.5);

        // TODO: I shouldn't be double-looking up a variable by string here
        DaveVector startPosition = DaveVector.fromFieldRadial(
                c.getDouble("5_disk_drive_dist", 1.5),
                c.getDouble("5_disk_drive_angle", 25.0));
        
        return new Series(new Command[] {
            Back5Disk.getAsCommand(),
            
            // turn to disks and lower hopper
            new Latch(new Command[] {
                new TurnToHeading(driveAngle),
                new HopperHeight(false)
            }),
            
            // drive forward and pick up
            new Latch(new Command[] {
                new PickupIntake(),
                new FollowLine(
                startPosition, 
                DaveVector.fromFieldRadial(1, driveAngle), 
                driveDistance, 
                drivePower)
            }),
                
            // spit and drive back
            new Latch(new Command[] {
                new PickupSpit(),
                new TurnToHeading(0)
            }),
            // TODO: drive back to pyramid
            // raise the hopper
            new Latch(new Command[] {
                // TODO: spin up shooter wheels
                new HopperHeight(true),
                new PickupStop()
            }),
            // fire
            new ShootDisks(2)
        });
    }

    public String getVisibleName() {
        return "Back 7 Disk";
    }
    
}
