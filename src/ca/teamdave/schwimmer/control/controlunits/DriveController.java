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
public class DriveController {
    private final TurnController mTurnControl;
    private final HighStaticPWD mForwardControl;
    
    public DriveController() {
        mTurnControl = new TurnController();
        mForwardControl = new HighStaticPWD(4.0, 0.01, 20.0, 0.005);
    }
    
    public void setControlDestination(double encoderAverage, double heading) {
        mForwardControl.setSetPoint(encoderAverage);
        mTurnControl.setDestAngle(heading);
    }
    
    public boolean doCycle(RobotInterface robot) {
        double forwardPower = mForwardControl.computeCycle(robot.getEncoderAverage());
        mTurnControl.setForwardPower(forwardPower);
        
        boolean turnDone = mTurnControl.doCycle(robot);
        
        return mForwardControl.isDone() && turnDone;
    }
}
