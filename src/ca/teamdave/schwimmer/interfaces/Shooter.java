/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author leighpauls
 */
public class Shooter {
    
    private final Victor mFrontMotor;
    private final Victor mBackMotor;
    
    private final Encoder mFrontShooterEncoder;
    private final Encoder mBackShooterEncoder;
    private final Solenoid mRaiserUp;
    private final Solenoid mRaiserDown;
    
    public Shooter() {
        System.out.println("making Shooter");
        mFrontMotor = new Victor(1, 5);
        mBackMotor = new Victor(1, 6);
        
        mFrontShooterEncoder = new Encoder(1, 5, 1, 6);
        mBackShooterEncoder = new Encoder(1, 7, 1, 8);
        
        mRaiserUp = new Solenoid(1, 1);
        mRaiserDown = new Solenoid(1, 2);
        
        reinit();
        
    }
    
    final public void reinit() {
        mFrontShooterEncoder.reset();
        mFrontShooterEncoder.start();
        mBackShooterEncoder.reset();
        mBackShooterEncoder.start();
    }
    
    public void setPower(double front, double back) {
        mFrontMotor.set(-front);
        mBackMotor.set(-back);
    }
    
    public double getFrontSpeed() {
        return mFrontShooterEncoder.getRate();
    }
    public double getBackSpeed() {
        return mBackShooterEncoder.getRate();
    }
    
    public void setRaiser(boolean up) {
        mRaiserUp.set(up);
        mRaiserDown.set(!up);
    }
}
