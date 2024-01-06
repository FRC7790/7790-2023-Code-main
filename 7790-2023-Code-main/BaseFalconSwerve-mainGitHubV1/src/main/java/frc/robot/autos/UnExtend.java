// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Extender;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class UnExtend extends SequentialCommandGroup
{
    public UnExtend(final Arm s_Arm, final Extender s_Extender) {
        super(new Command[0]);
        this.addCommands(new InstantCommand(() -> s_Extender.retractAll(), new Subsystem[0]));
    }
}