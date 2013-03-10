/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.competition;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.shooter.ShootDisks;

/**
 *
 * @author leighpauls
 */
public class Back3Disk extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        return new ShootDisks(3);
    }

    public String getVisibleName() {
        return "Back 3 Disks";
    }
    
}
