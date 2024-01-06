// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.ObjectTargeting;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Extender;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PickUpObjectFast extends SequentialCommandGroup
{
    public PickUpObjectFast(final Arm s_Arm, final Extender s_Extender, final Vision s_Vision, final Swerve s_Swerve) {
        super(new Command[0]);
        this.addRequirements(s_Arm, s_Swerve, s_Vision, s_Extender);
        this.addCommands(new ObjectTargeting(s_Swerve, s_Vision));
        this.addCommands(new InstantCommand(() -> s_Arm.grabbingObjects(), new Subsystem[0]), new InstantCommand(() -> s_Extender.openClaw(), new Subsystem[0]), new InstantCommand(() -> s_Extender.retract1(), new Subsystem[0]), new InstantCommand(() -> s_Extender.extend2(), new Subsystem[0]), new WaitCommand(0.5), new InstantCommand(() -> s_Extender.closeClaw(), new Subsystem[0]), new WaitCommand(0.2), new InstantCommand(() -> s_Extender.retractAll(), new Subsystem[0]));
    }
}
