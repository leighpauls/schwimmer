/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author leighpauls
 */
public class XboxGamePad {
    private final Joystick mPad;
    
    public XboxGamePad(int port) {
        mPad = new Joystick(port);
    }
    
    public double getXLeft() {
        return mPad.getRawAxis(1);
    }
    public double getYLeft() {
        return mPad.getRawAxis(2);
    }
    /**
     * More positive is more left
     * @return 
     */
    public double getTriggerDifference() {
        return mPad.getRawAxis(3);
    }
    
    public double getXRight() {
        return mPad.getRawAxis(4);
    }
    public double getYRight() {
        return mPad.getRawAxis(5);
    }
    
    public boolean getAButton() {
        return mPad.getRawButton(1);
    }
    public boolean getBButton() {
        return mPad.getRawButton(2);
    }
    public boolean getXButton() {
        return mPad.getRawButton(3);
    }
    public boolean getYButton() {
        return mPad.getRawButton(4);
    }
    public boolean getLeftBumper() {
        return mPad.getRawButton(5);
    }
    
    public boolean getRightBumper() {
        return mPad.getRawButton(6);
    }
    
    public boolean getBackButton() {
        return mPad.getRawButton(7);
    }
    public boolean getStartButton() {
        return mPad.getRawButton(8);
    }
    public boolean getLeftStickButton() {
        return mPad.getRawButton(9);
    }
    public boolean getRightStickButton() {
        return mPad.getRawButton(10);
    }
    
}
