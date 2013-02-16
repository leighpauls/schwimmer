/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.control.controlunits.BaseLock;
import ca.teamdave.schwimmer.control.controlunits.ShotControl;
import ca.teamdave.schwimmer.interfaces.Hopper;
import ca.teamdave.schwimmer.interfaces.Operators;
import ca.teamdave.schwimmer.interfaces.Shooter;
import ca.teamdave.schwimmer.util.Const;

/**
 *
 * @author leighpauls
 */
public class TeleopController {
    private final BaseLock mBaseLock;
    private boolean mBaseLockEngaged;
    private final ShotControl mShooter;
    
    
    public TeleopController() {
        mBaseLock = new BaseLock();
        mShooter = new ShotControl();
        mBaseLockEngaged = false;
    }
    
    public void initTeleopMode() {
        mBaseLockEngaged = false;
    }
    
    public void runTeleopCycle(Robot robot) {
        Operators op = robot.getOperators();
        
        if (op.isBaseLockButtonDown()) {
            if (!mBaseLockEngaged) {
                mBaseLock.engageLock(robot);
                mBaseLockEngaged = true;
            }
            mBaseLock.doCycle(robot);
        } else {
            mBaseLock.releaseLock();
            mBaseLockEngaged = false;
            robot.getDrive().setDrive(
                    op.getDriverX(),
                    op.getDriverY());            
        }
        
        Shooter shot = robot.getShooter();
        // Shooter Speed
        if (op.isShooterButtonDown()) {
            shot.setPower(
                    Const.getInstance().getDouble("shooter_front_ff", 1.0),
                    Const.getInstance().getDouble("shooter_back_ff", 1.0));
        } else {
            shot.setPower(0, 0);
        }
        // Shooter raiser
        if (op.isShooterHighButtonDown()) {
            shot.setRaiser(true);
        } else if (op.isShooterLowButtonDown()) {
            shot.setRaiser(false);
        }
        

        Hopper hop = robot.getHopper();
        // hopper raiser
        if (op.isHopperHighButtonDown()) {
            hop.setRaiser(true);
        } else if (op.isHopperLowButtonDown()) {
            hop.setRaiser(false);
        }
        // hopper punch
        hop.setPunch(op.isPunchButtonDown());
        
        // Feeder
        robot.getFeeder().activateFeeder(op.isIntakeButtonDown());
    }
}
