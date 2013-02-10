/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;


import ca.teamdave.schwimmer.util.DaveUtil;
import ca.teamdave.schwimmer.util.DaveVector;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author leigh-pauls
 */
public class Robot {

    private final Operators mOperators;
    private final Shooter mShooter;
    private final Drive mDrive;
    
   
    public Robot() {
        
        mDrive = new Drive();
        mShooter = new Shooter();
        mOperators = new Operators();
        
        reinit();
    }

    public Shooter getShooter() {
        return mShooter;
    }
    
    public Operators getOperators() {
        return mOperators;
    }
    
    public Drive getDrive() {
        return mDrive;
    }
    
    final public void reinit() {
        reinit(DaveVector.fromXY(0, 0), 0.0);
    }
    
    final public void reinit(DaveVector newPosition, double newHeading) {
        mDrive.reinit();
        mShooter.reinit();
    }
    
    public void periodicUpdate(String autoName) {
        mDrive.periodicUpdate(autoName);
        
        DaveVector pos = mDrive.getPosition();
        
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser1, 
                1, 
                "X: " + DaveUtil.toAccuracy(pos.getX(), 3)
                + " Y: " + DaveUtil.toAccuracy(pos.getY(), 3));
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser2,
                1,
                "L: " + DaveUtil.toAccuracy(mDrive.getEncoderLeft(), 3) + "       ");
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser3,
                1, 
                "R: " + DaveUtil.toAccuracy(mDrive.getEncoderRight(), 3) + "    ");
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser4,
                1, 
                "Gyro: " + DaveUtil.toAccuracy(mDrive.getHeading(), 3) + "      ");
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser5, 
                1, 
                "Auto: " + autoName + "               ");
        
        DriverStationLCD.getInstance().updateLCD();
        
    }
    
}
