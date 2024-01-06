// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.SwerveModule;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase
{
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    private final AHRS gyro;
    
    public Swerve() {
        this.gyro = new AHRS(SPI.Port.kMXP);
        this.zeroGyroscope();
        this.mSwerveMods = new SwerveModule[] { new SwerveModule(0, Constants.Swerve.Mod0.constants), new SwerveModule(1, Constants.Swerve.Mod1.constants), new SwerveModule(2, Constants.Swerve.Mod2.constants), new SwerveModule(3, Constants.Swerve.Mod3.constants) };
        Timer.delay(1.0);
        this.resetModulesToAbsolute();
        this.swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, this.getYaw(), this.getModulePositions());
    }
    
    public void drive(final Translation2d translation, final double rotation, final boolean fieldRelative, final boolean isOpenLoop) {
        final SwerveModuleState[] swerveModuleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotation, this.getYaw()) : new ChassisSpeeds(translation.getX(), translation.getY(), rotation));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, 4.5);
        for (final SwerveModule mod : this.mSwerveMods) {
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }
    
    public void setModuleStates(final SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, 12.0);
        for (final SwerveModule mod : this.mSwerveMods) {
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }
    
    public Pose2d getPose() {
        return this.swerveOdometry.getPoseMeters();
    }
    
    public void resetOdometry(final Pose2d pose) {
        this.swerveOdometry.resetPosition(this.getYaw(), this.getModulePositions(), pose);
    }
    
    public SwerveModuleState[] getModuleStates() {
        final SwerveModuleState[] states = new SwerveModuleState[4];
        for (final SwerveModule mod : this.mSwerveMods) {
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }
    
    public SwerveModulePosition[] getModulePositions() {
        final SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for (final SwerveModule mod : this.mSwerveMods) {
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }
    
    public Rotation2d getYaw() {
        final Rotation2d yaw = Rotation2d.fromDegrees(360.0f - this.gyro.getYaw());
        return Rotation2d.fromDegrees(yaw.getDegrees());
    }
    
    public void resetModulesToAbsolute() {
        for (final SwerveModule mod : this.mSwerveMods) {
            mod.resetToAbsolute();
        }
    }
    
    public void zeroGyroscope() {
        this.gyro.zeroYaw();
    }
    
    public double getRoll() {
        final double roll = this.gyro.getRoll();
        return roll;
    }
    
    public double getPitch() {
        final double pitch = this.gyro.getPitch();
        return pitch;
    }
    
    public Rotation2d getGyroscopeRotation() {
        if (this.gyro.isMagnetometerCalibrated()) {
            return Rotation2d.fromDegrees(this.gyro.getFusedHeading());
        }
        return Rotation2d.fromDegrees(360.0 - this.gyro.getYaw());
    }
    
    @Override
    public void periodic() {
        this.swerveOdometry.update(this.getYaw(), this.getModulePositions());
        final Translation2d translation = this.swerveOdometry.getPoseMeters().getTranslation();
        for (final SwerveModule mod : this.mSwerveMods) {
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);
        }
        SmartDashboard.putNumber(" Translation X", translation.getX());
        SmartDashboard.putNumber(" Translation Y", translation.getY());
    }
}
