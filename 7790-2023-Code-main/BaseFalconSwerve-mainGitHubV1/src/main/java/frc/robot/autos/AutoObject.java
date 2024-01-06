// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RotateCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.TargetClass;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Extender;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoObject extends SequentialCommandGroup
{
    public AutoObject(final Arm s_Arm, final Extender s_Extender, final Swerve s_Swerve, final Vision s_Vision, final TargetClass target) {
        super(new Command[0]);
        final TargetClass firstTarget = new TargetClass(2);
        final TargetClass secondTarget = new TargetClass(3);
        final float driveoutDist = 30.0f;
        final float driveBackDist = 17.0f;
        this.addCommands(new InstantCommand(() -> s_Vision.setPipeline(0), new Subsystem[0]), new InstantCommand(() -> s_Vision.setPipeline(1), new Subsystem[0]), new DriveDistanceVeryFast(s_Swerve, -driveoutDist, 0.0f), new InstantCommand(() -> s_Arm.grabbingObjects(), new Subsystem[0]), new RotateCommand(s_Swerve, 175.0f), new WaitCommand(0.5), new PickUpObject(s_Arm, s_Extender, s_Vision, s_Swerve), new WaitCommand(0.5), new RotateCommand(s_Swerve, 175.0f), new DriveDistanceBackwardsFast(s_Swerve, driveBackDist, 0.0f), new PlaceObjectFull(s_Arm, s_Extender, s_Vision, s_Swerve, secondTarget));
    }
}
