/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.competition;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.DriveStop;
import ca.teamdave.schwimmer.command.drive.DriveToPosition;
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
import ca.teamdave.schwimmer.command.shooter.ShooterStop;
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class Back5Disk extends AutoModeDescriptor {

    public static Command getAsCommand() {
        Const c = Const.getInstance();
        DaveVector drive_pos_1 = c.vectorFromConst("5_disk_1", -0.3, 0.7);
        DaveVector drive_pos_2 = c.vectorFromConst("5_disk_2", -0.7, 1.0);
        DaveVector drive_pos_3 = c.vectorFromConst("5_disk_3", -0.9, 2.0);
        
        double power = c.getDouble("5_disk_drive_power", 0.3);
        double closeShotAngle = c.getDouble("5_disk_close_angle", -5.0);
        
        return new Series(new Command[] {
            Back3Disk.getAsCommand(),
    
            // lower hopper
            new HopperHeight(false),
            new PickupIntake(),
            // drive forward and pick up

            new DriveToPosition(drive_pos_1, power),
            new DriveToPosition(drive_pos_2, power),
            new DriveToPosition(drive_pos_3, power),
            
            new DriveStop(),
            // finish picking up
            new Delay(0.5),
            
            // raise the shooter, aim, and spit any caught disks
            new Latch(new Command[] {
                new PickupSpit(),        
                new TurnToHeading(closeShotAngle),
                new ShooterHeight(true)
            }),
            
            // Make sure there isn't any stateful drive power 
            new DriveStop(),
            
            // raise the hopper
            new Latch(new Command[] {
                // TODO: spin up the shooter wheels
                new HopperHeight(true),
                new PickupStop()
            }),
            
            // fire
            new ShootDisks(2),
            new Delay(0.5),
            new ShooterStop()
            
        });
    }

    public Command getTopLevelCommand() {
        return getAsCommand();
    }

    public String getVisibleName() {
        return "Back 5 Stop";
    }
    
}
