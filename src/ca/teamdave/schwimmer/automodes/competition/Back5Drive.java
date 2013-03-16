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
import ca.teamdave.schwimmer.command.shooter.ShooterHeight;
import ca.teamdave.schwimmer.command.shooter.ShooterStop;
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class Back5Drive extends AutoModeDescriptor {
    private final boolean mIsLeft;

    public Back5Drive(boolean isLeft) {
        mIsLeft = isLeft;
    }
    
    public Command getTopLevelCommand() {
        Const c = Const.getInstance();
        
        DaveVector safePosition = c.vectorFromConst("5_disk_drive_safe", -0.3, -1);

        double power = c.getDouble("5_disk_drive_drive_power", 0.4);
        double endAngle = c.getDouble("5_disk_drive_end_angle", 45);

        DaveVector endPosition;
        
        if (mIsLeft) {
            endPosition = c.vectorFromConst("5_disk_dive_left", -3.0, -3.0);
            endAngle *= -1;
        } else {
            endPosition = c.vectorFromConst("5_disk_dive_right", 1.0, -3.0);
        }
        
        return new Series(new Command[] {
            Back5Disk.getAsCommand(),
            
            // lower the hopper+shooter
            new HopperHeight(false),
            new ShooterHeight(false),
            
            // Drive to a safe position
            new DriveToPositionReverse(safePosition, power),
            // Drive to the end position
            new DriveToPositionReverse(endPosition, power),
            new TurnToHeading(endAngle),
            new DriveStop()
        });
    }

    public String getVisibleName() {
        return "5 Drive " + (mIsLeft ? "Left" : "Right");
    }
    
}
