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
    RobotInterface robot;
    AutoController auto;
    
   
    // Move this stuff to RobotControl

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        robot = new RobotInterface();
        robot.reinit();
        auto = new AutoController();
        
    }

    public void autonomousInit() {
        auto.initAutoMode();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        auto.runAutoStep(robot);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        robot.setDrive(robot.getDriverX(), robot.getDriverY());
        
        // System.out.println("x: " + x + ", y: " + y);
        
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
            
    
}
