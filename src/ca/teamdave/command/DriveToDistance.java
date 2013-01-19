/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.command;

import ca.teamdave.RobotInterface;
import ca.teamdave.control.HighStaticPWD;

/**
 *
 * @author leighpauls
 */
public class DriveToDistance implements Command {
    private final HighStaticPWD control;

    public DriveToDistance(double dist) {
        control = new HighStaticPWD(0.01, 0, 0, 0.05);
        control.setSetPoint(dist);
    }
    
    public void runCommandStep(RobotInterface robot) {
        double position = (robot.getEncoderLeft() + robot.getEncoderRight()) / 2.0;
        double output = control.computeCycle(position);
        System.out.println(position);
        robot.setDrive(0, output);
    }

    public boolean isDone() {
        return control.isDone();
    }
    
}
