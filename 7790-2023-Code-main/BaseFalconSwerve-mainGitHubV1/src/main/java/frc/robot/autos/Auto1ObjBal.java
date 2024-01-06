// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.autos;

import frc.robot.commands.AutoBalance;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.TargetClass;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Extender;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Auto1ObjBal extends SequentialCommandGroup
{
    public Auto1ObjBal(final Arm s_Arm, final Extender s_Extender, final Swerve s_Swerve, final Vision s_Vision, final TargetClass target) {
        super(new Command[0]);
        final TargetClass firstTarget = new TargetClass(2);
        final TargetClass secondTarget = new TargetClass(3);
        final float driveoutDist = 8.5f;
        final float driveBackDist = 6.0f;
        this.addCommands(new InstantCommand(() -> s_Arm.setAngleTarget(firstTarget), new Subsystem[0]), new WaitCommand(0.4), new InstantCommand(() -> s_Extender.extendToTarget(firstTarget), new Subsystem[0]), new WaitCommand(0.8), new InstantCommand(() -> s_Extender.openClaw(), new Subsystem[0]), new WaitCommand(0.4), new UnExtend(s_Arm, s_Extender), new WaitCommand(0.4), new InstantCommand(() -> s_Arm.grabbingObjects(), new Subsystem[0]), new DriveDistance(s_Swerve, -driveoutDist, 0.0f), new AutoBalance(s_Swerve), new InstantCommand(() -> s_Vision.setPipeline(1), new Subsystem[0]));
    }
}
