// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.TargetClass;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Extender;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PlaceObjectFull extends SequentialCommandGroup
{
    public PlaceObjectFull(final Arm s_Arm, final Extender s_Extender, final Vision s_Vision, final Swerve s_Swerve, final TargetClass target) {
        super(new Command[0]);
        this.addRequirements(s_Arm, s_Swerve, s_Vision, s_Extender);
        this.addCommands(new PlaceObject(s_Arm, s_Extender, s_Vision, s_Swerve, target), new WaitCommand(0.4), new InstantCommand(() -> s_Arm.setHighScoreScore(), new Subsystem[0]), new WaitCommand(0.4), new InstantCommand(() -> s_Extender.openClaw(), new Subsystem[0]), new WaitCommand(0.4), new InstantCommand(() -> s_Extender.retractAll(), new Subsystem[0]));
    }
}
