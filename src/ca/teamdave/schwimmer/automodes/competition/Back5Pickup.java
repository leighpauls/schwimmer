/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.competition;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.DriveStop;
import ca.teamdave.schwimmer.command.drive.DriveToPosition;
import ca.teamdave.schwimmer.command.drive.DriveToPositionReverse;
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
import ca.teamdave.schwimmer.command.shooter.ShooterStop;
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class Back5Pickup extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        Const c = Const.getInstance();
        
        DaveVector pickupPos1 = c.vectorFromConst("7_disk_pick_1", -0.6, 3.6);
        DaveVector pickupPos2 = c.vectorFromConst("7_disk_pick_2", -0.3, 4.0);
        DaveVector shootPos = c.vectorFromConst("7_disk_shoot", -0.4, 2.0);
        double power = c.getDouble("7_disk_drive_power", 0.3);
        
        return new Series(new Command[] {
            Back5Disk.getAsCommand(),
            
            // lower the hopper
            new HopperHeight(false),
            
            // pickup and drive
            new PickupIntake(),
            new DriveToPosition(pickupPos1, power),
            new DriveToPosition(pickupPos2, power),

            // finish picking up
            new DriveStop(),
            new Delay(0.5),
            
            // get ready to shoot
            new Latch(new Command[] {
                new PickupSpit(),
                new DriveToPositionReverse(shootPos, power),
            }),
            
            // aim
            new Latch(new Command[] {
                new TurnToHeading(0.0),
                new HopperHeight(true),
            }),
            
            new DriveStop(),
            new PickupStop(),
        });
    }

    public String getVisibleName() {
        return "Back 5 Pickup";
    }
    
}
