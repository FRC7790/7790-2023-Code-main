// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Extender;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ExtendArmHigh extends SequentialCommandGroup
{
    public ExtendArmHigh(final Arm s_Arm, final Extender s_Extender) {
        super(new Command[0]);
        this.addCommands(new InstantCommand(() -> s_Arm.setHighScore(), new Subsystem[0]), new WaitCommand(1.0), new InstantCommand(() -> s_Extender.extendAll(), new Subsystem[0]), new WaitCommand(2.0), new InstantCommand(() -> s_Extender.openClaw(), new Subsystem[0]));
    }
}
