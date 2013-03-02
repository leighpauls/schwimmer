/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.dummy;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.FollowArc;
import ca.teamdave.schwimmer.command.drive.FollowLine;
import ca.teamdave.schwimmer.command.meta.Endless;
import ca.teamdave.schwimmer.command.meta.Series;
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class ArcMode extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        Const c = Const.getInstance();
        double turnForwardPower = c.getDouble(
                "arc_mode_turn_foward_power", 0.5);
        double straightForwardPower = c.getDouble(
                "arc_mode_straight_forward_power", 0.5);

        FollowArc firstArc = new FollowArc(
                DaveVector.fromXY(0, 0),
                DaveVector.fromFieldRadial(1.0, 0),
                c.getDouble("arc_mode_t1a_radius", 1.2),
                c.getDouble("arc_mode_t1a_length", 0.6),
                turnForwardPower);
        
        FollowArc secondArc = new FollowArc(
                firstArc.getEndPoint(),
                firstArc.getEndDirection(),
                c.getDouble("arc_mode_t1b_radius", 0.7),
                c.getDouble("arc_mode_t1b_length", 0.7),
                turnForwardPower);

        FollowLine fromPrymid = new FollowLine(
                c.vectorFromConst("arc_mode_from_pyr", -0.4, 1.2),
                DaveVector.fromXY(-1, 0),
                c.getDouble("arc_mode_from_pyr_dist", 1.5),
                straightForwardPower);
        
        FollowLine toMid = new FollowLine(
                c.vectorFromConst("arc_mode_to_mid", -2.5, 1.2),
                DaveVector.fromXY(0, 1),
                c.getDouble("arc_mode_to_mid_dist", 2.0),
                straightForwardPower);
        
        FollowLine toMidDisks = new FollowLine(
                c.vectorFromConst("arc_mode_to_disks", -2.5, 4.0),
                DaveVector.fromXY(1, 0),
                c.getDouble("arc_mode_to_disks_dist", 2.5),
                straightForwardPower);
        
        // System.out.println("Point: " + firstArc.getEndPoint().toString() + ", Dir: " + firstArc.getEndDirection().toString());
        
        return new Series(new Command[] {
            firstArc,
            secondArc
        });
    }

    public String getVisibleName() {
        return "Arc Mode";
    }
    
}
