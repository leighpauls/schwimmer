/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer;


import ca.teamdave.schwimmer.util.DaveUtil;
import ca.teamdave.schwimmer.util.DaveVector;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author leigh-pauls
 */
public class RobotInterface {
    // Robot actuators
    private Victor mDriveLeft;
    private Victor mDriveRight;
    
    // Robot sensors
    private Encoder mLeftEncoder;
    private Encoder mRightEncoder;
    private Gyro mGyro;
    
    // TODO: find this const
    static private final double K_METERS_PER_ENCODER_TICK = 1.0 / 791.5;
    
    // human input
    private Joystick mDriver;
    private double mLastEncoderAverage;
    private DaveVector mPos;
    private double mHeading;
    private double mGyroOffset;

   
    public RobotInterface() {
        mDriveLeft = new Victor(1);
        mDriveRight = new Victor(3);
        
        mLeftEncoder = new Encoder(1, 2);
        mRightEncoder = new Encoder(3, 4);
        
        mGyro = new Gyro(1);
        mGyro.setSensitivity(0.0125 * 200.0 / 360.0);
        
        mDriver = new Joystick(1);
        
        reinit();
    }

    
    final public void reinit() {
        reinit(DaveVector.fromXY(0, 0), 0.0);
    }
    
    final public void reinit(DaveVector newPosition, double newHeading) {
        mPos = newPosition;
        mLastEncoderAverage = 0.0;
        mLeftEncoder.reset();
        mLeftEncoder.start();
        mRightEncoder.reset();
        mRightEncoder.start();
        
        mGyro.reset();
        mGyroOffset = newHeading;
        mHeading = newHeading;
    }
    
    public void periodicUpdate(String autoName) {
        double curEncoderAverage = (getEncoderLeft() + getEncoderRight()) / 2.0;
        double distTraveled = curEncoderAverage - mLastEncoderAverage;
        mLastEncoderAverage = curEncoderAverage;
        
        mHeading = mGyroOffset - mGyro.getAngle();
        
        mPos.moveFieldRadial(distTraveled, mHeading);
        
        
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser1, 
                1, 
                "X: " + DaveUtil.toAccuracy(mPos.getX(), 3)
                + " Y: " + DaveUtil.toAccuracy(mPos.getY(), 3));
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser2,
                1,
                "L: " + DaveUtil.toAccuracy(getEncoderLeft(), 3) + "       ");
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser3,
                1, 
                "R: " + DaveUtil.toAccuracy(getEncoderRight(), 3) + "    ");
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser4,
                1, 
                "Gyro: " + DaveUtil.toAccuracy(mHeading, 3) + "      ");
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser5, 
                1, 
                "Auto: " + autoName + "               ");
        
        DriverStationLCD.getInstance().updateLCD();
    }
    
    public DaveVector getPosition() {
        return mPos;
    }
    
    public void setDrive(double x, double y) {
        double left = y + x;
        double right = -y + x;
        
        mDriveLeft.set(left);
        mDriveRight.set(right);
    }  
    
    public double getDriverX() {
        return mDriver.getX(GenericHID.Hand.kLeft);
    }

    public double getDriverY() {
        return -mDriver.getY(GenericHID.Hand.kLeft);
    }
    
    public double getEncoderLeft() {
        return -mLeftEncoder.get() * K_METERS_PER_ENCODER_TICK;
    }
    
    public double getEncoderRight() {
        return mRightEncoder.get() * K_METERS_PER_ENCODER_TICK;
    }

    public double getHeading() {
        return mHeading;
    }
    
    public boolean isAutonSelectButton() {
        return mDriver.getRawButton(1);
    }
}
