/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author leighpauls
 */
public class Feeder {
    public static final String STATE_UP = "UP";
    public static final String STATE_IN = "IN";
    public static final String STATE_SPIT = "SPIT";
    
    private final Victor mIntakeMotor;
    private final Solenoid mRaiserUp;
    private final Solenoid mRaiserDown;
    private final Hopper mHopper;
    
    public Feeder(Hopper hopper) {
        System.out.println("making Feeder");
        mIntakeMotor = new Victor(1, 5);
        mRaiserUp = new Solenoid(1, 7);
        mRaiserDown = new Solenoid(1, 8);
        mHopper = hopper;
        
        setFeederState(STATE_UP);
    }
    
    final public void setFeederState(String state) {
        setFeederState(state, 0.0);
    }
    
    final public void setFeederState(String state, double powerAdjustment) {
        if (state == STATE_IN && (!mHopper.isSafeToIntake())) {
            System.out.println("Tried to intake with hopper up");
            state = STATE_UP;
        }
        if (state == STATE_IN) {
            // mIntakeMotor.set(-0.63 - powerAdjustment);
            mIntakeMotor.set(-1.0 - powerAdjustment);
            mRaiserDown.set(true);
            mRaiserUp.set(false);
        } else if (state == STATE_SPIT) {
            mIntakeMotor.set(1.0);
            mRaiserDown.set(true);
            mRaiserUp.set(false);
        } else {
            if (state != STATE_UP) {
                System.out.println("Invalid feeder state: " + state);                
            }
            mIntakeMotor.set(0.0);
            mRaiserDown.set(false);
            mRaiserUp.set(true);
        }
    }
    
}
