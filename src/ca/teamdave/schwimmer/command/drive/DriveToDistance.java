/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.drive;

import ca.teamdave.schwimmer.util.DaveUtil;
import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.control.HighStaticPWD;

/**
 *
 * @author leighpauls
 */
public class DriveToDistance implements Command {
    private final HighStaticPWD control;
    private final double maxPower;

    public DriveToDistance(double dist) {
        this(dist, 1.0);
    }
    public DriveToDistance(double dist, double maxPower) {
        control = new HighStaticPWD(0.01, 0, 0, 0.05);
        control.setSetPoint(dist);
        this.maxPower = Math.abs(maxPower);
    }
    
    public void runCommandStep(RobotInterface robot) {
        double position = (robot.getEncoderLeft() + robot.getEncoderRight()) / 2.0;
        double output = control.computeCycle(position);
        System.out.println(position);
        
        if (Math.abs(output) > maxPower) {
            output = maxPower * DaveUtil.sign(output);
        }
        
        robot.setDrive(0, output);
    }

    public boolean isDone() {
        return control.isDone();
    }
    
}
