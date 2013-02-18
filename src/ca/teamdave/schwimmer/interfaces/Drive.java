/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;

import ca.teamdave.schwimmer.util.DaveVector;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author leighpauls
 */
public class Drive {
    static private final double K_METERS_PER_ENCODER_TICK = 1.0 / 2696.0;
    
    // Robot actuators
    private final Victor mLeftFrontMotor;
    private final Victor mLeftBackMotor;
    
    private final Victor mRightFrontMotor;
    private final Victor mRightBackMotor;
    
    // Robot sensors
    private final Encoder mLeftEncoder;
    private final Encoder mRightEncoder;
    private Gyro mGyro;
    
    private double mGyroOffset;
    private double mHeading;
    private DaveVector mPos;
    private double mLastEncoderAverage;
    private final Solenoid mShifterHigh;
    private final Solenoid mShifterLow;

    public Drive() {
        System.out.println("making Drive");
        mLeftFrontMotor = new Victor(1, 1);
        mLeftBackMotor = new Victor(1, 2);
        
        mRightFrontMotor = new Victor(1, 3);
        mRightBackMotor = new Victor(1, 4);
        
        mShifterHigh = new Solenoid(2, 3);
        mShifterLow = new Solenoid(2, 4);
        
        mLeftEncoder = new Encoder(1, 1, 1, 2);
        mRightEncoder = new Encoder(1, 3, 1, 4);
        mGyro = new Gyro(1);
        mGyro.setSensitivity(0.0125 * 200.0 / 360.0);
        
        mGyroOffset = 0.0;
        mHeading = 0.0;
        mPos = DaveVector.fromXY(0, 0);
        mLastEncoderAverage = 0.0;
    }
    
    public void reinit() {
        reinit(DaveVector.fromXY(0, 0), 0);
    }
    
    public void reinit(DaveVector newPosition, double newHeading) {
        mLeftEncoder.reset();
        mRightEncoder.reset();
        
        mLeftEncoder.start();
        mRightEncoder.start();
        
        mGyro.reset();
        
        mGyroOffset = newHeading;
        mHeading = newHeading;
        mPos = newPosition;
        mLastEncoderAverage = 0.0;
    }
    
    public void periodicUpdate() {
        double curEncoderAverage = getEncoderAverage();
        double distTraveled = curEncoderAverage - mLastEncoderAverage;
        mLastEncoderAverage = curEncoderAverage;
        
        mHeading = mGyroOffset - mGyro.getAngle();
        
        mPos.moveFieldRadial(distTraveled, mHeading);
    }

    public void setShifter(boolean high) {
        mShifterHigh.set(high);
        mShifterLow.set(!high);
    }
    
    public DaveVector getPosition() {
        return mPos;
    }
    
    public void setDrive(double x, double y) {
        double left = -y - x;
        double right = y - x;
        mLeftFrontMotor.set(left);
        mLeftBackMotor.set(left);
        mRightFrontMotor.set(right);
        mRightBackMotor.set(right);
    }
    
    public double getEncoderAverage() {
        return (getEncoderLeft() + getEncoderRight()) / 2.0;
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
