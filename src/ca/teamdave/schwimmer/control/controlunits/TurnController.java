/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.control.HighStaticPWD;

/**
 *
 * @author leighpauls
 */
public class TurnController {
    private final HighStaticPWD mController;
    private final double mForwardPower;
    
    
    public TurnController(double forwardPower) {
        mController = new HighStaticPWD(0.01, 0, 0, 2.0);
        mForwardPower = forwardPower;
    }
    
    public void setDestAngle(double newAngle) {
        mController.setSetPoint(newAngle, true);
    }
    
    /**
     * 
     * @param robot
     * @return true iff the control loop is done
     */
    public boolean doCycle(RobotInterface robot) {
        robot.setDrive(mController.computeCycle(robot.getHeading()), mForwardPower);
        return mController.isDone();
    }

}
