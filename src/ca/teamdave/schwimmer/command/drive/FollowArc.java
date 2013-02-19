/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.command.drive;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.control.HighStaticPWD;
import ca.teamdave.schwimmer.control.LinearPID;
import ca.teamdave.schwimmer.control.controlunits.TurnController;
import ca.teamdave.schwimmer.interfaces.Drive;
import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class FollowArc implements Command {
    private final DaveVector mTurnCentroid;
    private final double mDestThetaDegrees;
    private final TurnController mTurnController;
    private final double mBendRadius;
    private final LinearPID mDirectionController;
    private final DaveVector mFromOrigin;
    private boolean mDone;
    
    /**
     * 
     * @param origin place to start turning from
     * @param startDirection tangent of the arc at the start of the turn
     * @param bendRadius radius of the turn, positive for right, smaller = harder turn
     * @param arcLength length along the arc to turn
     */
    public FollowArc(
            DaveVector origin,
            DaveVector startDirection,
            double bendRadius,
            double arcLength,
            double forwardPower) {

        mTurnCentroid = origin.add(DaveVector.fromFieldRadial(bendRadius, startDirection.getFieldAngle() + 90));
        mFromOrigin = mTurnCentroid.vectorTo(origin);
        
        mDestThetaDegrees = arcLength / bendRadius * 180 / Math.PI;
        mBendRadius = bendRadius;
        
        mTurnController = new TurnController();
        mTurnController.setDestAngle(startDirection.getFieldAngle());
        mTurnController.setForwardPower(forwardPower);

        mDirectionController = Const.getInstance().pidFromConst(
                "arc_follower_direction", 60.0, 0.0, 400.0, 0.02);
        mDirectionController.setSetPoint(0.0);
        
        double arcHardnessFF = Const.getInstance().getDouble(
                "arc_follower_turn_ff", 0.25 * 1.0 / 1.5);
        mTurnController.setFFTurnPower(arcHardnessFF / bendRadius);
        
        mDone = false;
    }
    
    public void runCommandStep(Robot robot) {
        Drive d = robot.getDrive();
        DaveVector position = d.getPosition();
        
        DaveVector fromCentroid = mTurnCentroid.vectorTo(position);
        
        double distFromArc = mBendRadius - fromCentroid.getMagnitude();
        double idealAngle = fromCentroid.getFieldAngle() + ((mBendRadius > 0) ? 90 : -90);
        double turnHardness = mDirectionController.computeCycle(distFromArc);
        double maxHardness = Const.getInstance().getDouble(
                "arc_follower_max_turn_hardness", 90.0);
        turnHardness = Math.min(maxHardness,
                Math.max(-maxHardness, turnHardness));
        
        
        
        double destAngle = idealAngle + turnHardness;

        System.out.println("dist: " + distFromArc + ", ideal: " + idealAngle + ", hardness" + turnHardness + ", dest: " + destAngle +", cur: " + robot.getDrive().getHeading());
        
        mTurnController.setDestAngle(destAngle);
        mTurnController.doCycle(robot);
        
        double curTheta = Math.abs(fromCentroid.getFieldAngle() - mFromOrigin.getFieldAngle());
        if (curTheta > mDestThetaDegrees) {
            mDone = true;
        } else {
            mDone = false;
        }
    }

    public boolean isDone() {
        return mDone;
    }
}
