package frc.robot;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.lib.util.SwerveModuleConstants;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import frc.lib.util.COTSFalconSwerveConstants;

public final class Constants
{
    public static final double stickDeadband = 0.06;
    
    public static final class Swerve
    {
        public static final boolean invertGyro = true;
        public static final COTSFalconSwerveConstants chosenModule;
        public static final double trackWidth;
        public static final double wheelBase;
        public static final double wheelCircumference;
        public static final double wheelDiameter;
        public static final SwerveDriveKinematics swerveKinematics;
        public static final double driveGearRatio;
        public static final double angleGearRatio;
        public static final boolean angleMotorInvert;
        public static final boolean driveMotorInvert;
        public static final boolean canCoderInvert;
        public static final double voltageComp = 12.0;
        public static final int angleContinuousCurrentLimit = 25;
        public static final int anglePeakCurrentLimit = 40;
        public static final double anglePeakCurrentDuration = 0.1;
        public static final boolean angleEnableCurrentLimit = true;
        public static final int driveContinuousCurrentLimit = 35;
        public static final int drivePeakCurrentLimit = 60;
        public static final double drivePeakCurrentDuration = 0.1;
        public static final boolean driveEnableCurrentLimit = true;
        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;
        public static final double angleKP;
        public static final double angleKI;
        public static final double angleKD;
        public static final double angleKF;
        public static final double angleKFF = 0.0;
        public static final double driveKP = 0.05;
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.0;
        public static final double driveKF = 0.0;
        public static final double driveConversionPositionFactor;
        public static final double driveConversionVelocityFactor;
        public static final double angleConversionFactor;
        public static final double driveKS = 0.02666666666666667;
        public static final double driveKV = 0.12583333333333332;
        public static final double driveKA = 0.022500000000000003;
        public static final double maxSpeed = 4.5;
        public static final double maxSpeedAuto = 12.0;
        public static final double maxAngularVelocity = 3.0;
        public static final CANSparkMax.IdleMode angleNeutralMode;
        public static final CANSparkMax.IdleMode driveNeutralMode;
        public static boolean angleInvert;
        
        static {
            chosenModule = COTSFalconSwerveConstants.SDSMK4i(6.75);
            trackWidth = Units.inchesToMeters(24.625);
            wheelBase = Units.inchesToMeters(24.625);
            wheelCircumference = Swerve.chosenModule.wheelCircumference;
            wheelDiameter = Units.inchesToMeters(Swerve.wheelCircumference / 3.141592653589793);
            swerveKinematics = new SwerveDriveKinematics(new Translation2d[] { new Translation2d(Swerve.wheelBase / 2.0, Swerve.trackWidth / 2.0), new Translation2d(Swerve.wheelBase / 2.0, -Swerve.trackWidth / 2.0), new Translation2d(-Swerve.wheelBase / 2.0, Swerve.trackWidth / 2.0), new Translation2d(-Swerve.wheelBase / 2.0, -Swerve.trackWidth / 2.0) });
            driveGearRatio = Swerve.chosenModule.driveGearRatio;
            angleGearRatio = Swerve.chosenModule.angleGearRatio;
            angleMotorInvert = Swerve.chosenModule.angleMotorInvert;
            driveMotorInvert = Swerve.chosenModule.driveMotorInvert;
            canCoderInvert = Swerve.chosenModule.canCoderInvert;
            angleKP = Swerve.chosenModule.angleKP;
            angleKI = Swerve.chosenModule.angleKI;
            angleKD = Swerve.chosenModule.angleKD;
            angleKF = Swerve.chosenModule.angleKF;
            driveConversionPositionFactor = Swerve.wheelDiameter * 3.141592653589793 / Swerve.driveGearRatio;
            driveConversionVelocityFactor = Swerve.driveConversionPositionFactor / 60.0;
            angleConversionFactor = 360.0 / Swerve.angleGearRatio;
            angleNeutralMode = CANSparkMax.IdleMode.kCoast;
            driveNeutralMode = CANSparkMax.IdleMode.kBrake;
            Swerve.angleInvert = true;
        }
        
        public static final class Mod0
        {
            public static final int driveMotorID = 3;
            public static final int angleMotorID = 4;
            public static final int canCoderID = 9;
            public static final Rotation2d angleOffset;
            public static final SwerveModuleConstants constants;
            
            static {
                angleOffset = Rotation2d.fromDegrees(155.6);
                constants = new SwerveModuleConstants(3, 4, 9, Mod0.angleOffset);
            }
        }
        
        public static final class Mod1
        {
            public static final int driveMotorID = 1;
            public static final int angleMotorID = 2;
            public static final int canCoderID = 10;
            public static final Rotation2d angleOffset;
            public static final SwerveModuleConstants constants;
            
            static {
                angleOffset = Rotation2d.fromDegrees(165.1);
                constants = new SwerveModuleConstants(1, 2, 10, Mod1.angleOffset);
            }
        }
        
        public static final class Mod2
        {
            public static final int driveMotorID = 5;
            public static final int angleMotorID = 6;
            public static final int canCoderID = 12;
            public static final Rotation2d angleOffset;
            public static final SwerveModuleConstants constants;
            
            static {
                angleOffset = Rotation2d.fromDegrees(176.84);
                constants = new SwerveModuleConstants(5, 6, 12, Mod2.angleOffset);
            }
        }
        
        public static final class Mod3
        {
            public static final int driveMotorID = 7;
            public static final int angleMotorID = 8;
            public static final int canCoderID = 11;
            public static final Rotation2d angleOffset;
            public static final SwerveModuleConstants constants;
            
            static {
                angleOffset = Rotation2d.fromDegrees(209.3);
                constants = new SwerveModuleConstants(7, 8, 11, Mod3.angleOffset);
            }
        }
    }
    
    public static final class AutoConstants
    {
        public static final double kMaxSpeedMetersPerSecond = 12.0;
        public static final double kMaxAccelerationMetersPerSecondSquared = 12.0;
        public static final double kMaxAngularSpeedRadiansPerSecond = 3.141592653589793;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = 3.141592653589793;
        public static final double kPXController = 1.0;
        public static final double kPYController = 1.0;
        public static final double kPThetaController = 1.0;
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints;
        
        static {
            kThetaControllerConstraints = new TrapezoidProfile.Constraints(3.141592653589793, 3.141592653589793);
        }
    }
}
