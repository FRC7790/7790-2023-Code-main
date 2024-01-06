// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot;

import com.ctre.phoenix.sensors.SensorTimeBase;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public final class CTREConfigs
{
    public TalonFXConfiguration swerveAngleFXConfig;
    public TalonFXConfiguration swerveDriveFXConfig;
    public CANCoderConfiguration swerveCanCoderConfig;
    
    public CTREConfigs() {
        this.swerveAngleFXConfig = new TalonFXConfiguration();
        this.swerveDriveFXConfig = new TalonFXConfiguration();
        this.swerveCanCoderConfig = new CANCoderConfiguration();
        final SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(true, 25.0, 40.0, 0.1);
        this.swerveAngleFXConfig.slot0.kP = Constants.Swerve.angleKP;
        this.swerveAngleFXConfig.slot0.kI = Constants.Swerve.angleKI;
        this.swerveAngleFXConfig.slot0.kD = Constants.Swerve.angleKD;
        this.swerveAngleFXConfig.slot0.kF = Constants.Swerve.angleKF;
        this.swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;
        final SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(true, 35.0, 60.0, 0.1);
        this.swerveDriveFXConfig.slot0.kP = 0.05;
        this.swerveDriveFXConfig.slot0.kI = 0.0;
        this.swerveDriveFXConfig.slot0.kD = 0.0;
        this.swerveDriveFXConfig.slot0.kF = 0.0;
        this.swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        this.swerveDriveFXConfig.openloopRamp = 0.25;
        this.swerveDriveFXConfig.closedloopRamp = 0.0;
        this.swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        this.swerveCanCoderConfig.sensorDirection = Constants.Swerve.canCoderInvert;
        this.swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        this.swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
    }
}
