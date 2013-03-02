/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;


import ca.teamdave.schwimmer.util.DaveUtil;
import ca.teamdave.schwimmer.util.DaveVector;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author leigh-pauls
 */
public class Robot {

    private final Operators mOperators;
    private final Shooter mShooter;
    private final Drive mDrive;
    private final Hopper mHopper;
    private final Feeder mFeeder;
    private final Hanger mHanger;
    private final Compressor mCompressor;
   
    public Robot() {

        mCompressor = new Compressor(14, 1);
        
        mDrive = new Drive();
        mShooter = new Shooter(mCompressor);
        mOperators = new Operators();
        mHopper = new Hopper();
        mFeeder = new Feeder(mHopper);
        mHanger = new Hanger();
        
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
    
    public Hopper getHopper() {
        return mHopper;
    }
    
    public Feeder getFeeder() {
        return mFeeder;
    }
    
    public Hanger getHanger() {
        return mHanger;
    }
    
    final public void reinit() {
        reinit(DaveVector.fromXY(0, 0), 0.0);
    }
    
    final public void reinit(DaveVector newPosition, double newHeading) {
        mDrive.reinit();
        mShooter.reinit();
        mCompressor.start();
    }
    
    int cyclesToUpdate = 0;
    
    public void periodicUpdate(String autoName) {
        mDrive.periodicUpdate();
        mShooter.periodicUpdate();
        
        if (cyclesToUpdate <= 0) {
            cyclesToUpdate = 10;
        } else {
            cyclesToUpdate--;
            return;
        }
        
        DaveVector pos = mDrive.getPosition();
        
        
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1,
                1,
                "B: " + DaveUtil.toAccuracy(mShooter.getBackSpeed(), 0) + 
                " F: " + DaveUtil.toAccuracy(mShooter.getFrontSpeed(), 0) + "          ");
        
        DriverStationLCD.getInstance().println(
                DriverStationLCD.Line.kUser2, 
                1, 
                "X: " + DaveUtil.toAccuracy(pos.getX(), 3)
                + " Y: " + DaveUtil.toAccuracy(pos.getY(), 3) + "             ");

        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3,
                1,
                "L :" + DaveUtil.toAccuracy(mDrive.getEncoderLeft(), 4) +
                " R: " + DaveUtil.toAccuracy(mDrive.getEncoderRight(), 4) + "             ");
        
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
