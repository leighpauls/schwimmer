/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author leigh-pauls
 */
public class RobotInterface {
    private Victor drive_left;
    private Victor drive_right;
    private Joystick driver;
   
    public RobotInterface() {
        
        drive_left = new Victor(1);
        drive_right = new Victor(3);
        driver = new Joystick(1);
    }
    
    public void setDrive(double x, double y) {
        double left = y + x;
        double right = -y + x;
        
        drive_left.set(left);
        drive_right.set(right);
    }  
    
    public double getDriverX() {
        return driver.getX(GenericHID.Hand.kLeft);
    }

    public double getDriverY() {
        return -driver.getY(GenericHID.Hand.kLeft);
    }
    
}
