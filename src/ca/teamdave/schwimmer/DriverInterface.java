/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer;

import ca.teamdave.schwimmer.util.DaveUtil;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author leighpauls
 */
public class DriverInterface {
    private final Joystick mDriver;
    
    public DriverInterface() {
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
        return mDriver.getRawButton(6);
    }

}
