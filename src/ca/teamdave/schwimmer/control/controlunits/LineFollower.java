/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.control.controlunits;

import ca.teamdave.schwimmer.RobotInterface;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 * Controller which follows the line specified in the constructor until crossing 
 * the line perpendicular to the reference line at the specified distance
 * @author leighpauls
 */
public class LineFollower {
    private final DaveVector mLineOrigin;
    private final DaveVector mLineDirection;
    private final double mTravelDisance;
    private final TurnController mTurnController;
    
    public LineFollower(
            final DaveVector lineOrigin,
            final DaveVector lineDirection,
            double travelDistance,
            double forwardPower) {
        mLineOrigin = lineOrigin;
        mLineDirection = lineDirection;
        mTravelDisance = travelDistance;
        
        // just point down the line for the sake of contruction??
        mTurnController = new TurnController(forwardPower);
    }
    
    /**
     * Call this periodically when in use
     * @param robot
     * @return true iff I've passed the finishing line
     */
    public boolean doCycle(RobotInterface robot) {
        DaveVector curPos = robot.getPosition();
        mTurnController.setDestAngle(getDesiredAngle(curPos));
        mTurnController.doCycle(robot);
        
        // check if I've crossed the line
        return hasTraveledDistance(curPos);
    }
    
    private double getDesiredAngle(DaveVector curPosition) {
        // find the distance from here to the line
        double distToLine = curPosition.distanceToLine(
                mLineOrigin, mLineDirection);
        double turnHardness = distToLine * 45;
        turnHardness = Math.min(90.0, Math.max(-90, turnHardness));
        return mLineDirection.getFieldAngle() - turnHardness;
    }

    private boolean hasTraveledDistance(DaveVector curPosition) {
        // check if I've crossed the line
        DaveVector startLine = DaveVector.fromFieldRadial(
                1.0, mLineDirection.getFieldAngle() + 90.0);
        if (curPosition.distanceToLine(mLineOrigin, startLine) 
                >= mTravelDisance) {
            return true;
        }
        return false;

    }
}
