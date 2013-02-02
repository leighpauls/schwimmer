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
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author leigh-pauls
 */
public class RobotInterface {
    // Robot actuators
    private final Jaguar mDriveLeft;
    private final Jaguar mDriveRight;

    private final Jaguar mFrontShooter;
    private final Jaguar mBackShooter;
    
    
    // Robot sensors
    private final Encoder mLeftEncoder;
    private final Encoder mRightEncoder;
    private Gyro mGyro;
    
    private final Encoder mFrontShooterEncoder;
    private final Encoder mBackShooterEncoder;
    
    // TODO: find this const
    static private final double K_METERS_PER_ENCODER_TICK = 1.0 / 791.5;
    
    // robot state
    private double mLastEncoderAverage;
    private DaveVector mPos;
    private double mHeading;
    private double mGyroOffset;
    private final DriverInterface mDriverInterface;
    
   
    public RobotInterface() {
        // FIXME: if these scrambled values end up in master, Leigh's fucked up
        // Don't let this comment get merged in!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        mDriveLeft = new Jaguar(4);
        mDriveRight = new Jaguar(5);
        
        mLeftEncoder = new Encoder(5, 6);
        mRightEncoder = new Encoder(7, 8);

        // TODO: replace with real controllers on the real robot
        mFrontShooter = new Jaguar(1);
        mBackShooter = new Jaguar(3);
        mFrontShooterEncoder = new Encoder(1, 2);
        mBackShooterEncoder = new Encoder(3, 4);
        
        mGyro = new Gyro(1);
        mGyro.setSensitivity(0.0125 * 200.0 / 360.0);
        
        mDriverInterface = new DriverInterface();
        
        reinit();
    }

    public DriverInterface getDriver() {
        return mDriverInterface;
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
        
        mFrontShooterEncoder.reset();
        mFrontShooterEncoder.start();
        mBackShooterEncoder.reset();
        mBackShooterEncoder.start();
        
        mGyro.reset();
        mGyroOffset = newHeading;
        mHeading = newHeading;
    }
    
    public void periodicUpdate(String autoName) {
        double curEncoderAverage = getEncoderAverage();
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
    
    public void setShooterPower(double front, double back) {
        mFrontShooter.set(front);
        mBackShooter.set(back);
        System.out.println(front + "," + getFrontShooterSpeed());
    }

    public double getFrontShooterSpeed() {
        double res = -mFrontShooterEncoder.getRate(); // ticks per second
        // System.out.println("Front speed: " + res);
        return res;
                
    }
    
    public double getBackShooterSpeed() {
        return -mBackShooterEncoder.getRate();
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
