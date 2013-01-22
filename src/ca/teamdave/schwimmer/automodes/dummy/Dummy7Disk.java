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
        double farDiskDist = 4.0;
        double closeDiskDist = 2.0;
        
        // TODO: Put the flywheel on and keep it on
        
        // Drive forward and fire 3 disks
        Command firstApproach = new FollowLine(
                DaveVector.fromXY(0, 0),
                DaveVector.fromXY(0, 1),
                farDiskDist,
                0.3);
        Command driveHold = new TurnToHeading(0.0);

        Command driveSeries = new Series(new Command[] {
            firstApproach,
            driveHold
        });

        Command shotSeries = new Series(new Command[] {
           new DummyWaitForFlyWheel(),
           new DummyShootDisk(),
           new DummyShootDisk(),
           new DummyShootDisk(),
           new NoOp()
        });
        
        Command firstSegment = new Latch(new Command[] {
            driveSeries,
            shotSeries
        });
        
        // Turn left
        Command firstLeft = new TurnToHeading(90.0);
        
        // Dive through the first 2 disks
        // TODO: pickup
        Command secondSegment = new FollowLine(
                DaveVector.fromXY(0, farDiskDist),
                DaveVector.fromXY(-1.0, 0.0),
                2.0, 0.3);
        // TODO: stop pickup
        
        // Turn Left
        Command secondLeft = new TurnToHeading(180.0);
        
        // Drive to the second row of disks
        Command thirdSegment = new FollowLine(
                DaveVector.fromXY(-2.0, farDiskDist),
                DaveVector.fromXY(0, -1.0),
                farDiskDist - closeDiskDist,
                0.3);
        
        Command thirdLeft = new TurnToHeading(270);
        
        // TODO: pickip
        Command fourthSegment = new FollowLine(
                DaveVector.fromXY(-2, closeDiskDist),
                DaveVector.fromXY(1.0, 0.0),
                2.0, 0.3);
        
        Command finalCorner = new TurnToHeading(360);
        
        Command finalShots = new Series(new Command[] {
            new DummyShootDisk(),
            new DummyShootDisk(),
            new DummyShootDisk(),
            new DummyShootDisk(),
            new NoOp()
        });
        
        return new Series(new Command[] {
            firstSegment,
            firstLeft,
            secondSegment,
            secondLeft,
            thirdSegment,
            thirdLeft,
            fourthSegment,
            finalCorner,
            finalShots
        });
        
    }
    
}
