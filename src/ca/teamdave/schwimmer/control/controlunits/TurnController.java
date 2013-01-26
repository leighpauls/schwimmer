/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.control.HighStaticPWD;
import ca.teamdave.schwimmer.util.DaveUtil;

/**
 *
 * @author leighpauls
 */
public class TurnController {
    private final HighStaticPWD mController;
    private final double mForwardPower;
    private double mDestAngle;
    
    
    public TurnController(double forwardPower) {
        mController = new HighStaticPWD(0.02, 0.01, 0.1, 2.0);
        //mController = new HighStaticPWD(0.08, 0.01, 0.5, 2.0);
        mDestAngle = 0.0;
        mForwardPower = forwardPower;
    }
    
    public void setDestAngle(double newAngle) {
        mDestAngle = newAngle;
    }
    
    /**
     * 
     * @param robot
     * @return true iff the control loop is done
     */
    public boolean doCycle(RobotInterface robot) {
        double curHeading = robot.getHeading();
        double destAngle = mDestAngle;
        
        while (Math.abs(destAngle - curHeading) > 180.0) {
            destAngle += 360 * (destAngle > curHeading ? -1 : 1);
        }
        
        mController.setSetPoint(destAngle, true);
        
        robot.setDrive(-mController.computeCycle(robot.getHeading()), mForwardPower);
        return mController.isDone();
    }

}
