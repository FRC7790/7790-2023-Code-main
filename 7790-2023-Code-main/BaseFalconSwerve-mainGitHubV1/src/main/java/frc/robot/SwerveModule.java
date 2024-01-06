// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot;

import frc.lib.math.Conversions;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import frc.lib.util.CANSparkMaxUtil;
import frc.lib.util.CANCoderUtil;
import frc.lib.math.OnboardModuleState;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.lib.util.SwerveModuleConstants;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import com.revrobotics.SparkMaxPIDController;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.geometry.Rotation2d;

public class SwerveModule
{
    public int moduleNumber;
    private Rotation2d angleOffset;
    private Rotation2d lastAngle;
    private CANSparkMax angleMotor;
    private CANSparkMax driveMotor;
    private RelativeEncoder driveEncoder;
    private RelativeEncoder integratedAngleEncoder;
    private CANCoder angleEncoder;
    private final SparkMaxPIDController driveController;
    private final SparkMaxPIDController angleController;
    SimpleMotorFeedforward feedforward;
    
    public SwerveModule(final int moduleNumber, final SwerveModuleConstants moduleConstants) {
        this.feedforward = new SimpleMotorFeedforward(0.02666666666666667, 0.12583333333333332, 0.022500000000000003);
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset;
        this.angleEncoder = new CANCoder(moduleConstants.cancoderID);
        this.configAngleEncoder();
        this.angleMotor = new CANSparkMax(moduleConstants.angleMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.integratedAngleEncoder = this.angleMotor.getEncoder();
        this.angleController = this.angleMotor.getPIDController();
        this.configAngleMotor();
        this.driveMotor = new CANSparkMax(moduleConstants.driveMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveEncoder = this.driveMotor.getEncoder();
        this.driveController = this.driveMotor.getPIDController();
        this.configDriveMotor();
        this.lastAngle = this.getState().angle;
    }
    
    public void setDesiredState(SwerveModuleState desiredState, final boolean isOpenLoop) {
        desiredState = OnboardModuleState.optimize(desiredState, this.getState().angle);
        this.setAngle(desiredState);
        this.setSpeed(desiredState, isOpenLoop);
    }
    
    private void setSpeed(final SwerveModuleState desiredState, final boolean isOpenLoop) {
        if (isOpenLoop) {
            final double percentOutput = desiredState.speedMetersPerSecond / 4.5;
            this.driveMotor.set(percentOutput);
        }
        else {
            this.driveController.setReference(desiredState.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity, 0, this.feedforward.calculate(desiredState.speedMetersPerSecond));
        }
    }
    
    private void setAngle(final SwerveModuleState desiredState) {
        final Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= 0.045) ? this.lastAngle : desiredState.angle;
        this.angleController.setReference(angle.getDegrees(), CANSparkMax.ControlType.kPosition);
        this.lastAngle = angle;
    }
    
    private Rotation2d getAngle() {
        return Rotation2d.fromDegrees(this.integratedAngleEncoder.getPosition());
    }
    
    public Rotation2d getCanCoder() {
        return Rotation2d.fromDegrees(this.angleEncoder.getAbsolutePosition());
    }
    
    public void resetToAbsolute() {
        final double absolutePosition = this.getCanCoder().getDegrees() - this.angleOffset.getDegrees();
        this.integratedAngleEncoder.setPosition(absolutePosition);
    }
    
    private void configAngleEncoder() {
        this.angleEncoder.configFactoryDefault();
        CANCoderUtil.setCANCoderBusUsage(this.angleEncoder, CANCoderUtil.CCUsage.kMinimal);
        this.angleEncoder.configAllSettings(Robot.ctreConfigs.swerveCanCoderConfig);
    }
    
    private void configAngleMotor() {
        this.angleMotor.restoreFactoryDefaults();
        CANSparkMaxUtil.setCANSparkMaxBusUsage(this.angleMotor, CANSparkMaxUtil.Usage.kPositionOnly);
        this.angleMotor.setSmartCurrentLimit(25);
        this.angleMotor.setInverted(Constants.Swerve.angleInvert);
        this.angleMotor.setIdleMode(Constants.Swerve.angleNeutralMode);
        this.integratedAngleEncoder.setPositionConversionFactor(Constants.Swerve.angleConversionFactor);
        this.angleController.setP(Constants.Swerve.angleKP);
        this.angleController.setI(Constants.Swerve.angleKI);
        this.angleController.setD(Constants.Swerve.angleKD);
        this.angleController.setFF(0.0);
        this.angleMotor.enableVoltageCompensation(12.0);
        this.angleMotor.burnFlash();
        this.resetToAbsolute();
    }
    
    private void configDriveMotor() {
        this.driveMotor.restoreFactoryDefaults();
        CANSparkMaxUtil.setCANSparkMaxBusUsage(this.driveMotor, CANSparkMaxUtil.Usage.kAll);
        this.driveMotor.setSmartCurrentLimit(35);
        this.driveMotor.setInverted(false);
        this.driveMotor.setIdleMode(Constants.Swerve.driveNeutralMode);
        this.driveEncoder.setVelocityConversionFactor(Constants.Swerve.driveConversionVelocityFactor);
        this.driveEncoder.setPositionConversionFactor(Constants.Swerve.driveConversionPositionFactor);
        this.driveController.setP(Constants.Swerve.angleKP);
        this.driveController.setI(Constants.Swerve.angleKI);
        this.driveController.setD(Constants.Swerve.angleKD);
        this.driveController.setFF(0.0);
        this.driveMotor.enableVoltageCompensation(12.0);
        this.driveMotor.burnFlash();
        this.driveEncoder.setPosition(0.0);
    }
    
    public SwerveModuleState getState() {
        return new SwerveModuleState(this.driveEncoder.getVelocity(), this.getAngle());
    }
    
    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(Conversions.neoToMeters(this.driveMotor.getEncoder().getPosition() * 4096.0, Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio), this.getAngle());
    }
}
