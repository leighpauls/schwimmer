/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer;

import ca.teamdave.schwimmer.interfaces.Robot;
import ca.teamdave.schwimmer.control.controlunits.BaseLock;
import ca.teamdave.schwimmer.control.controlunits.ShotControl;
import ca.teamdave.schwimmer.interfaces.Feeder;
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
        if (op.isShooterButtonDown()) {
            if (!mShooter.isEngaged()){
                mShooter.engage();
            }
        } else {
            if (mShooter.isEngaged()){
                mShooter.disengage();
            }
        }
        Hopper hop = robot.getHopper();
        mShooter.doCycle(shot, robot.getHopper(), op.isPunchButtonDown());
        
        // Shooter raiser
        if (op.isShooterHighButtonDown()) {
            shot.setRaiser(true);
        } else if (op.isShooterLowButtonDown()) {
            shot.setRaiser(false);
        }

        // hopper raiser
        if (op.isHopperHighButtonDown()) {
            hop.setRaiser(true);
        } else if (op.isHopperLowButtonDown()) {
            hop.setRaiser(false);
        }
        // hopper punch
        // hop.setPunch(op.isPunchButtonDown());
        
        // Feeder
        if (op.isIntakeButtonDown()) {
            double adjustment = op.getIntakeAdjustment();
            robot.getFeeder().setFeederState(Feeder.STATE_IN, adjustment);
            // System.out.println("Adjust: " + adjustment);
        } else if (op.isReverseIntakeButtonDown()) {
            robot.getFeeder().setFeederState(Feeder.STATE_SPIT);
        } else {
            robot.getFeeder().setFeederState(Feeder.STATE_UP);
        }
        
        // hanging
        if (op.isHangerDownButton()) {
            robot.getHanger().set(false);
        } else if (op.isHangerUpButton()) {
            robot.getHanger().set(true);
        }
        
        // shifting
        if (op.isShiftUp()) {
            robot.getDrive().setShifter(true);
        } else if (op.isShiftDown()) {
            robot.getDrive().setShifter(false);
        }
    }
}
