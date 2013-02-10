/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.control.HighStaticPWD;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class TurnController {
    private final HighStaticPWD mController;
    private double mForwardPower;
    private double mDestAngle;
    
    
    public TurnController() {
        mController = Const.getInstance().pwdFromConst(
                "turn_controller", 0.02, 0.01, 0.1, 2.0);
        mDestAngle = 0.0;
        mForwardPower = 0.0;
    }
    
    public void setDestAngle(double newAngle) {
        mDestAngle = newAngle;
    }
    
    public void setForwardPower(double forwardPower) {
        mForwardPower = forwardPower;
    }
    
    /**
     * 
     * @param robot
     * @return true iff the control loop is done
     */
    public boolean doCycle(Robot robot) {
        double curHeading = robot.getDrive().getHeading();
        double destAngle = mDestAngle;
        
        while (Math.abs(destAngle - curHeading) > 180.0) {
            destAngle += 360 * (destAngle > curHeading ? -1 : 1);
        }
        
        mController.setSetPoint(destAngle, true);
        
        robot.getDrive().setDrive(-mController.computeCycle(robot.getDrive().getHeading()), mForwardPower);
        return mController.isDone();
    }

}
