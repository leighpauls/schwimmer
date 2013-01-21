/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.teamdave.schwimmer;


import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.automodes.AutoModeSelector;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Schwimmer extends IterativeRobot {
    RobotInterface mRobot;
    AutoController mAuto;
    
    AutoModeDescriptor mSelectedAuto;
    private AutoModeSelector mAutoSelector;
    private long mLastTime = 0;
    
   
    public void robotInit() {
        mRobot = new RobotInterface();
        mAuto = new AutoController();
        mAutoSelector = new AutoModeSelector();
        mSelectedAuto = mAutoSelector.getDefault();
        System.out.println("Using default auto: " 
                + mSelectedAuto.getVisibleName());
    }

    public void autonomousInit() {
        mRobot.reinit(
                mSelectedAuto.getInitialPosition(),
                mSelectedAuto.getInitialHeading());
        mAuto.initAutoMode(mSelectedAuto);
    }
    public void autonomousPeriodic() {
        mRobot.periodicUpdate(mSelectedAuto.getVisibleName());
        mAuto.runAutoStep(mRobot);
    }


    public void teleopPeriodic() {
        mRobot.periodicUpdate(mSelectedAuto.getVisibleName());

        // TODO: make a real teleop class
        mRobot.setDrive(mRobot.getDriverX(), mRobot.getDriverY());
    }

    
    public void testPeriodic() {
        mRobot.periodicUpdate(mSelectedAuto.getVisibleName());
        
    }
    
    
    
            
    public void disabledPeriodic() {
        mRobot.periodicUpdate(mSelectedAuto.getVisibleName());
        
        if (mRobot.isAutonSelectButton()) {
            mSelectedAuto = mAutoSelector.selectFromAnalogRange(
                    mRobot.getDriverY());
        }
    }
}
