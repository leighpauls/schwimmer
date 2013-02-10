/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;

import edu.wpi.first.wpilibj.Encoder;
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
    
    public Shooter() {
        mFrontMotor = new Victor(5);
        mBackMotor = new Victor(6);
        
        mFrontShooterEncoder = new Encoder(5, 6);
        mBackShooterEncoder = new Encoder(7, 8);
        
        reinit();
    }
    
    public void reinit() {
        mFrontShooterEncoder.reset();
        mFrontShooterEncoder.start();
        mBackShooterEncoder.reset();
        mBackShooterEncoder.start();
    }
    
    public void setPower(double front, double back) {
        mFrontMotor.set(front);
        mBackMotor.set(back);
    }
    
    public double getFrontSpeed() {
        return mFrontShooterEncoder.getRate();
    }
    public double getBackSpeed() {
        return mBackShooterEncoder.getRate();
    }
}
