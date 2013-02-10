/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author leighpauls
 */
public class Hanger {
    private final Solenoid mUp;
    private final Solenoid mDown;
    
    public Hanger() {
        mUp = new Solenoid(1, 3);
        mDown = new Solenoid(1, 4);
        set(false);
    }
    
    public void set(boolean up) {
        mUp.set(up);
        mDown.set(!up);
    }
}
