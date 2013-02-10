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
    private final Joystick mDriver;
    
    public Operators() {
        mDriver = new Joystick(1);
    }
    
    private double filterJoystick(double raw) {
        return raw * raw * DaveUtil.sign(raw);
    }

    
    public double getDriverX() {
        return filterJoystick(mDriver.getX(GenericHID.Hand.kLeft));
    }

    public double getDriverY() {
        return filterJoystick(-mDriver.getY(GenericHID.Hand.kLeft));
    }

    public boolean isAutonSelectButtonDown() {
        return mDriver.getRawButton(1);
    }

    public boolean isBaseLockButtonDown() {
        return mDriver.getRawButton(2);
    }
    
    public boolean isShooterButtonDown() {
        return mDriver.getRawButton(8);
    }

    // drop/run intake
    public boolean isIntakeButtonDown() {
        return mDriver.getRawButton(3);
    }

    // hopper up/down
    public boolean isHopperLowButtonDown() {
        return mDriver.getRawButton(7);
    }
    public boolean isHopperHighButtonDown() {
        return mDriver.getRawButton(5);
    }
    
    // punch in/out
    public boolean isPunchButtonDown() {
        return mDriver.getRawButton(6);
    }
            
    
    // shooter up/down
    public boolean isShooterLowButtonDown() {
        return mDriver.getRawButton(1);
    }
    public boolean isShooterHighButtonDown() {
        return mDriver.getRawButton(4);
    }
}
