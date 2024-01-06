// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.Targeting;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.TargetClass;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Extender;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PlaceObject extends SequentialCommandGroup
{
    public PlaceObject(final Arm s_Arm, final Extender s_Extender, final Vision s_Vision, final Swerve s_Swerve, final TargetClass target) {
        super(new Command[0]);
        this.addRequirements(s_Arm, s_Swerve, s_Vision, s_Extender);
        this.addCommands(new Targeting(s_Swerve, s_Vision, target), new DriveDistanceBackwards(s_Swerve, 1.1, 0.0f), new InstantCommand(() -> s_Arm.setAngleTarget(target), new Subsystem[0]), new WaitCommand(0.5), new InstantCommand(() -> s_Extender.extendToTarget(target), new Subsystem[0]), new WaitCommand(0.5));
    }
}
