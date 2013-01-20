/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.teamdave.schwimmer;


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
    
   
    public void robotInit() {
        robot = new RobotInterface();
        auto = new AutoController();
    }

    public void autonomousInit() {
        robot.reinit();
        auto.initAutoMode();
    }
    public void autonomousPeriodic() {
        robot.periodicUpdate();
        auto.runAutoStep(robot);
    }


    public void teleopPeriodic() {
        robot.periodicUpdate();
        robot.setDrive(robot.getDriverX(), robot.getDriverY());
    }

    
    public void testPeriodic() {
        robot.periodicUpdate();
        
    }
    
            
    public void disabledPeriodic() {
        robot.periodicUpdate();
    }
}
