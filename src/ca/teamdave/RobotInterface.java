/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave;


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
    private Victor driveLeft;
    private Victor driveRight;
    
    // Robot sensors
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private Gyro gyro;
    
    // TODO: find this const
    static private final double METERS_PER_ENCODER_TICK = 1.0;
    
    // human input
    private Joystick driver;
   
    public RobotInterface() {
        driveLeft = new Victor(1);
        driveRight = new Victor(3);
        
        leftEncoder = new Encoder(1, 2);
        rightEncoder = new Encoder(3, 4);
        
        gyro = new Gyro(1);
        gyro.setSensitivity(0.0125);
        
        driver = new Joystick(1);
    }

    public void reinit() {
        leftEncoder.reset();
        leftEncoder.start();
        rightEncoder.reset();
        rightEncoder.start();
                
        
        gyro.reset();
    }
    
    public void setDrive(double x, double y) {
        double left = y + x;
        double right = -y + x;
        
        driveLeft.set(left);
        driveRight.set(right);
    }  
    
    public double getDriverX() {
        return driver.getX(GenericHID.Hand.kLeft);
    }

    public double getDriverY() {
        return -driver.getY(GenericHID.Hand.kLeft);
    }
    
    public double getEncoderLeft() {
        return -leftEncoder.get() * METERS_PER_ENCODER_TICK;
    }
    
    public double getEncoderRight() {
        return rightEncoder.get() * METERS_PER_ENCODER_TICK;
    }
    
}
