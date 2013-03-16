/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.drive;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.control.HighStaticPWD;
import ca.teamdave.schwimmer.control.controlunits.TurnController;
import ca.teamdave.schwimmer.interfaces.Drive;
import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveUtil;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class DriveToPosition implements Command {
    private final DaveVector mDest;
    private final TurnController mTurnControl;
    private final HighStaticPWD mForwardControl;
    private final double mMaxPower;
    private boolean mDone;

    public DriveToPosition(DaveVector position, double maxPower) {
        mDest = position;
        mTurnControl = new TurnController();
        mForwardControl = Const.getInstance().pwdFromConst(
                "position_drive", 0.7, 0.0, 0.0, 0.1);
        mMaxPower = maxPower;
        mDone = false;
    }
    
    public void runCommandStep(Robot robot) {
        Drive drive = robot.getDrive();
        DaveVector curPos = drive.getPosition();
        double curHeading = drive.getHeading();
        DaveVector meToDest = curPos.vectorTo(mDest);
        
        // robot will go backwards if that's a faster path!!!
        double forwardPowerMultiplier = Math.cos(
                Math.toRadians(meToDest.getFieldAngle() - curHeading));
        double forwardPower = forwardPowerMultiplier 
                * DaveUtil.limit(mForwardControl.computeCycle(-meToDest.getMagnitude()), mMaxPower);
        
        mTurnControl.setDestAngle(meToDest.getFieldAngle());
        mTurnControl.setForwardPower(forwardPower);
        mTurnControl.doCycle(robot);
        mDone = mForwardControl.isDone();
    }

    public boolean isDone() {
        return mDone;
    }
    
}
