/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.schwimmer.automodes.competition;

import ca.teamdave.schwimmer.automodes.AutoModeDescriptor;
import ca.teamdave.schwimmer.command.Command;
import ca.teamdave.schwimmer.command.drive.DriveStop;
import ca.teamdave.schwimmer.command.drive.DriveToPositionReverse;
import ca.teamdave.schwimmer.command.meta.Series;
import ca.teamdave.schwimmer.util.Const;
import ca.teamdave.schwimmer.util.DaveVector;

/**
 *
 * @author leighpauls
 */
public class Back3Drive extends AutoModeDescriptor {

    public Command getTopLevelCommand() {
        Const c = Const.getInstance();
        DaveVector endPosition = c.vectorFromConst("3_disk_back_end", 0, -3);
        double power = c.getDouble(("3_disk_back_drive_power"), 0.3);
        
        return new Series(new Command[] {
            Back3Disk.getAsCommand(),
            
            new DriveToPositionReverse(endPosition, power),
            new DriveStop()
        });
    }

    public String getVisibleName() {
        return "3 Drive";
    }
    
}
