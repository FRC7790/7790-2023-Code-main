// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.autos;

import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import java.util.function.Supplier;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.math.controller.PIDController;
import java.util.Objects;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import java.util.List;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveDistanceVeryFast extends SequentialCommandGroup
{
    public DriveDistanceVeryFast(final Swerve s_Swerve, final double distance, final float rotation) {
        super(new Command[0]);
        final TrajectoryConfig config = new TrajectoryConfig(20.0, 20.0).setKinematics(Constants.Swerve.swerveKinematics);
        final double dist = distance;
        final double rot = rotation;
        final Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0.0, 0.0, new Rotation2d(0.0)), List.of(), new Pose2d(-dist, 0.0, new Rotation2d(rot)), config);
        final ProfiledPIDController thetaController = new ProfiledPIDController(1.0, 0.0, 0.0, Constants.AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-3.141592653589793, 3.141592653589793);
        final Trajectory trajectory = exampleTrajectory;
        Objects.requireNonNull(s_Swerve);
        final Supplier<Pose2d> pose = s_Swerve::getPose;
        final SwerveDriveKinematics swerveKinematics = Constants.Swerve.swerveKinematics;
        final PIDController xController = new PIDController(1.0, 0.0, 0.0);
        final PIDController yController = new PIDController(1.0, 0.0, 0.0);
        final ProfiledPIDController thetaController2 = thetaController;
        Objects.requireNonNull(s_Swerve);
        final SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(trajectory, pose, swerveKinematics, xController, yController, thetaController2, s_Swerve::setModuleStates, new Subsystem[] { s_Swerve });
        this.addCommands(new InstantCommand(() -> s_Swerve.resetOdometry(exampleTrajectory.getInitialPose()), new Subsystem[0]), swerveControllerCommand);
    }
}
