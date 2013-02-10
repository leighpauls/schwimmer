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
    private final Victor mIntakeMotor;
    private final Solenoid mRaiserUp;
    private final Solenoid mRaiserDown;
    private final Hopper mHopper;
    
    public Feeder(Hopper hopper) {
        mIntakeMotor = new Victor(7);
        mRaiserUp = new Solenoid(1, 7);
        mRaiserDown = new Solenoid(1, 8);
        mHopper = hopper;
        
        activateFeeder(false);
    }
    
    final public void activateFeeder(boolean on) {
        if (on && !mHopper.isSafeToIntake()) {
            System.out.println("Tried to run intake with hopper up");
            on = false;
        }
        
        mIntakeMotor.set(on ? 1.0 : 0.0);
        mRaiserDown.set(on);
        mRaiserUp.set(!on);
    }
}
