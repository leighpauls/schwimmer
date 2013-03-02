/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;

import ca.teamdave.schwimmer.util.DaveUtil;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author leighpauls
 */
public class Operators {
    private final XboxGamePad mDriver;
    private final Joystick mOperator;
    
    public Operators() {
        System.out.println("making operators");
        mDriver = new XboxGamePad(1);
        mOperator = new Joystick(2);
    }
    
    private double filterJoystick(double raw) {
        return raw * raw * DaveUtil.sign(raw);
    }

    
    public double getDriverX() {
        return mDriver.getXLeft();
    }

    public double getDriverY() {
        return -mDriver.getYLeft();
    }

    public boolean isAutonSelectButtonDown() {
        return mDriver.getAButton();
    }

    public boolean isBaseLockButtonDown() {
        return mDriver.getBButton();
    }
    
    // drop/run intake
    public boolean isIntakeButtonDown() {
        return mDriver.getRightBumper();
    }
    public boolean isReverseIntakeButtonDown() {
        return mDriver.getLeftBumper();
    }

    public boolean isHangerUpButton() {
        return mDriver.getYButton();
    }
    public boolean isHangerDownButton() {
        return mDriver.getXButton();
    }
    
    public boolean isShooterButtonDown() {
        return mOperator.getRawButton(8);
    }
    
    // hopper up/down
    public boolean isHopperLowButtonDown() {
        return mOperator.getRawButton(7);
    }
    public boolean isHopperHighButtonDown() {
        return mOperator.getRawButton(5);
    }
    
    // punch in/out
    public boolean isPunchButtonDown() {
        return mOperator.getRawButton(6);
    }
            
    
    // shooter up/down
    public boolean isShooterLowButtonDown() {
        return mOperator.getRawButton(1);
    }
    public boolean isShooterHighButtonDown() {
        return mOperator.getRawButton(4);
    }
    
    public boolean isShiftUp() {
        return mDriver.getTriggerDifference() > 0.5;
    }
    public boolean isShiftDown() {
        return mDriver.getTriggerDifference() < -0.5;
    }
}
