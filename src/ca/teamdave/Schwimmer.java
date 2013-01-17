/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.teamdave;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Schwimmer extends IterativeRobot {
    private Victor drive_left;
    private Victor drive_right;
    private Joystick driver;
    
    // Move this stuff to RobotControl

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        System.out.println("hello world!");
        drive_left = new Victor(1);
        drive_right = new Victor(3);
        driver = new Joystick(1);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        double x = driver.getX(GenericHID.Hand.kLeft);
        double y = -driver.getY(GenericHID.Hand.kRight);

        System.out.println("x: " + x + ", y: " + y);
        
        double left = y + x;
        double right = -y + x;
        
        drive_left.set(left);
        drive_right.set(right);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
