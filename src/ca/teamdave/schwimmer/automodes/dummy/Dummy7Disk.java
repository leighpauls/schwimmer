/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.dummy;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.FollowLine;
import ca.teamdave.schwimmer.command.drive.TurnToHeading;
import ca.teamdave.schwimmer.command.meta.Latch;
import ca.teamdave.schwimmer.command.meta.NoOp;
import ca.teamdave.schwimmer.command.meta.Series;
import ca.teamdave.schwimmer.command.shooter.DummyShootDisk;
import ca.teamdave.schwimmer.command.shooter.DummyWaitForFlyWheel;
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class Dummy7Disk extends AutoModeDescriptor {

    public String getVisibleName() {
        return "Dummy 7 Disk";
    }

    public Command getTopLevelCommand() {
        double farDiskDist = Const.getInstance().getDouble(
                "dummy_7_disk_far_meters", 3.5);
        double closeDiskDist = Const.getInstance().getDouble(
                "dummy_7_disk_close_meters", 1.2);
        double sidePathDist = Const.getInstance().getDouble(
                "dummy_7_disk_side_meters", 1.5);
        double straightPower = Const.getInstance().getDouble(
                "dummy_7_disk_power", 0.5);
        double finalAngle = Const.getInstance().getDouble(
                "dummy_7_disk_final_angle", 390);

        // TODO: Put the flywheel on and keep it on

        // Drive forward and fire 3 disks
        Command firstApproach = new FollowLine(
                DaveVector.fromXY(0, 0),
                DaveVector.fromXY(0, 1),
                farDiskDist,
                straightPower);
        Command driveHold = new TurnToHeading(0.0);

        Command driveSeries = new Series(new Command[]{
                    firstApproach,
                    driveHold
                });

        Command shotSeries = new Series(new Command[]{
                    new DummyWaitForFlyWheel(),
                    new DummyShootDisk(),
                    new DummyShootDisk(),
                    new DummyShootDisk()
                });

        Command firstSegment = new Latch(new Command[]{
            shotSeries,
            driveSeries
        });

        // Turn left
        Command firstLeft = new TurnToHeading(90.0);

        // Dive through the first 2 disks
        // TODO: pickup
        Command secondSegment = new FollowLine(
                DaveVector.fromXY(0, farDiskDist),
                DaveVector.fromXY(-1.0, 0.0),
                sidePathDist, straightPower);
        // TODO: stop pickup

        // Turn Left
        Command secondLeft = new TurnToHeading(180.0);

        // Drive to the second row of disks
        Command thirdSegment = new FollowLine(
                DaveVector.fromXY(-sidePathDist, farDiskDist),
                DaveVector.fromXY(0, -1.0),
                farDiskDist - closeDiskDist,
                straightPower);

        Command thirdLeft = new TurnToHeading(270);

        // TODO: pickip
        Command fourthSegment = new FollowLine(
                DaveVector.fromXY(-sidePathDist, closeDiskDist),
                DaveVector.fromXY(1.0, 0.0),
                sidePathDist, straightPower);

        Command finalCorner = new TurnToHeading(finalAngle);

        Command finalShots = new Series(new Command[]{
                    new DummyShootDisk(),
                    new DummyShootDisk(),
                    new DummyShootDisk(),
                    new DummyShootDisk()
                });
        Command holdAim = new TurnToHeading(360);

        return new Series(new Command[]{
                    firstSegment,
                    firstLeft,
                    secondSegment,
                    secondLeft,
                    thirdSegment,
                    thirdLeft,
                    fourthSegment,
                    finalCorner,
                    new Latch(new Command[] {
                        finalShots,
                        holdAim
                    })
                });

    }
}
