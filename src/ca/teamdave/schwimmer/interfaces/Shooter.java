/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.interfaces;

import ca.teamdave.schwimmer.util.DaveUtil;
import edu.wpi.first.wpilibj.Compressor;
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
    private double mFrontLowPass;
    private double mBackLowPass;
    private final Compressor mCompressor;
    
    public Shooter(Compressor compressor) {
        System.out.println("making Shooter");
        mFrontMotor = new Victor(1, 6);
        mBackMotor = new Victor(1, 7);
        
        mFrontShooterEncoder = new Encoder(1, 5, 1, 6, true, Encoder.EncodingType.k1X);
        mBackShooterEncoder = new Encoder(1, 7, 1, 8, true, Encoder.EncodingType.k1X);
        
        mRaiserUp = new Solenoid(1, 1);
        mRaiserDown = new Solenoid(1, 2);
        
        mCompressor = compressor;
        
        reinit();
        
    }
    
    final public void reinit() {
        mFrontShooterEncoder.reset();
        mFrontShooterEncoder.start();
        mBackShooterEncoder.reset();
        mBackShooterEncoder.start();
        
        mFrontLowPass = 0.0;
        mBackLowPass = 0.0;
        
        mCompressor.start();
    }
    
    final static double kLowPassContribution = 0.1;
    
    public void periodicUpdate() {
        mFrontLowPass = mFrontLowPass * (1-kLowPassContribution)
                + mFrontShooterEncoder.getRate() * kLowPassContribution;
        mBackLowPass = mBackLowPass * (1-kLowPassContribution)
                + mBackShooterEncoder.getRate() * kLowPassContribution;
    }
    
    public void setPower(double front, double back, boolean engaged) {
        mFrontMotor.set(DaveUtil.limit(-front, 1.0));
        mBackMotor.set(DaveUtil.limit(-back, 1.0));
        
        if (engaged) {
            mCompressor.stop();
        } else {
            mCompressor.start();
        }
    }
    
    public double getFrontSpeed() {
        return mFrontLowPass;
    }
    public double getBackSpeed() {
        return mBackLowPass;
    }
    
    public void setRaiser(boolean up) {
        mRaiserUp.set(up);
        mRaiserDown.set(!up);
    }
}
