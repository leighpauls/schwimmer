/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes;

import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 * Creates an auto mode and holds any associated meta-data
 * @author leighpauls
 */
public abstract class AutoModeDescriptor {
    public double getInitialHeading() {
        return 0.0;
    }
    
    public DaveVector getInitialPosition() {
        return DaveVector.fromXY(0, 0);
    }
    
    public abstract Command getTopLevelCommand();
    public abstract String getVisibleName();
}
