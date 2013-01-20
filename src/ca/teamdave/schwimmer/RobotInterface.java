/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer;


import ca.teamdave.schwimmer.util.DaveVector;
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
    static private final double K_METERS_PER_ENCODER_TICK = 1.0;
    
    // human input
    private Joystick mDriver;
    private double mLastEncoderAverage;
    private DaveVector mPos;
    private double mHeading;

   
    public RobotInterface() {
        mDriveLeft = new Victor(1);
        mDriveRight = new Victor(3);
        
        mLeftEncoder = new Encoder(1, 2);
        mRightEncoder = new Encoder(3, 4);
        
        mGyro = new Gyro(1);
        mGyro.setSensitivity(0.0125);
        
        mDriver = new Joystick(1);
        
        reinit();
    }

    final public void reinit() {
        mPos = DaveVector.fromXY(0.0, 0.0);
        mLastEncoderAverage = 0.0;
        mLeftEncoder.reset();
        mLeftEncoder.start();
        mRightEncoder.reset();
        mRightEncoder.start();
        
        mGyro.reset();
        mHeading = 0.0;
    }
    
    public void periodicUpdate() {
        double curEncoderAverage = (getEncoderLeft() + getEncoderRight()) / 2.0;
        double distTraveled = curEncoderAverage - mLastEncoderAverage;
        mLastEncoderAverage = curEncoderAverage;
        
        mHeading = mGyro.getAngle();
        
        mPos.moveFieldRadial(distTraveled, mHeading);
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
    
}
