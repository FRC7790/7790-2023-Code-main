// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.TargetClass;
import edu.wpi.first.math.MathUtil;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.controller.PIDController;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase
{
    private CANSparkMax armMotor1;
    private CANSparkMax armMotor3;
    private CANSparkMax armMotor4;
    private CANCoder armEncoder;
    private float desiredAngle;
    private boolean chunkyMonkey;
    private float angleOffset;
    private float angleMax;
    private float angleMin;
    private float lowScore;
    private float middleScore;
    private float highScore;
    private float lowScoreCube;
    private float middleScoreCube;
    private float highScoreCube;
    private float highScoreScore;
    private float humanPickupAngle;
    private float objectAngle;
    private float pickupFromAbove;
    PIDController pid;
    
    public Arm() {
        this.desiredAngle = 0.0f;
        this.chunkyMonkey = false;
        this.angleOffset = 255.0f;
        this.angleMax = 35.0f;
        this.angleMin = -52.0f;
        this.lowScore = -30.0f;
        this.middleScore = 22.0f;
        this.highScore = 30.0f;
        this.lowScoreCube = -30.0f;
        this.middleScoreCube = 17.0f;
        this.highScoreCube = 25.0f;
        this.highScoreScore = 24.0f;
        this.humanPickupAngle = 20.0f;
        this.objectAngle = -24.5f;
        this.pickupFromAbove = -10.0f;
        this.pid = new PIDController(0.03, 0.0, 0.0);
        this.armMotor1 = new CANSparkMax(21, CANSparkMaxLowLevel.MotorType.kBrushed);
        this.armMotor3 = new CANSparkMax(13, CANSparkMaxLowLevel.MotorType.kBrushed);
        this.armMotor4 = new CANSparkMax(14, CANSparkMaxLowLevel.MotorType.kBrushed);
        this.armMotor3.follow(this.armMotor1, true);
        this.armMotor4.follow(this.armMotor1, true);
        this.armEncoder = new CANCoder(15);
    }
    
    public void setDesiredAngle(final float desiredAngle) {
        this.desiredAngle = (float)MathUtil.clamp(desiredAngle, this.angleMin, this.angleMax);
    }
    
    public void moveAmount(final float amount) {
        this.setDesiredAngle(this.desiredAngle + amount);
    }
    
    public void setHighScore() {
        this.setDesiredAngle(this.highScore);
    }
    
    public void setHumanPickup() {
        this.setDesiredAngle(this.humanPickupAngle);
    }
    
    public void setHighScoreScore() {
        this.setDesiredAngle(this.highScoreScore);
    }
    
    public void setMiddleScore() {
        this.setDesiredAngle(this.middleScore);
    }
    
    public void setLowScore() {
        this.setDesiredAngle(this.lowScore);
    }
    
    public void grabbingObjects() {
        this.setDesiredAngle(this.objectAngle);
    }
    
    public void setHighScoreCube() {
        this.setDesiredAngle(this.highScoreCube);
    }
    
    public void setMiddleScoreCube() {
        this.setDesiredAngle(this.middleScoreCube);
    }
    
    public void setLowScoreCube() {
        this.setDesiredAngle(this.lowScoreCube);
    }
    
    public void setPickupFromAbove() {
        this.setDesiredAngle(this.pickupFromAbove);
    }
    
    public void setAngleTarget(final TargetClass target) {
        if (target.getCurrentTarget() == 1 || target.getCurrentTarget() == 3) {
            this.setHighScore();
        }
        if (target.getCurrentTarget() == 2) {
            this.setHighScoreCube();
        }
        if (target.getCurrentTarget() == 4 || target.getCurrentTarget() == 6) {
            this.setMiddleScore();
        }
        if (target.getCurrentTarget() == 5) {
            this.setMiddleScoreCube();
        }
        if (target.getCurrentTarget() == 7 || target.getCurrentTarget() == 9) {
            this.setLowScore();
        }
        if (target.getCurrentTarget() == 8) {
            this.setLowScoreCube();
        }
    }
    
    @Override
    public void periodic() {
        if (!this.chunkyMonkey) {
            this.desiredAngle = (float)(this.armEncoder.getAbsolutePosition() - this.angleOffset);
            this.chunkyMonkey = true;
        }
        final float maxoutput = 0.5f;
        final double output = MathUtil.clamp(this.pid.calculate(this.armEncoder.getAbsolutePosition() - this.angleOffset, this.desiredAngle), -maxoutput, maxoutput);
        this.armMotor1.set(-output);
        SmartDashboard.putNumber("Arm Angle", this.armEncoder.getAbsolutePosition() - this.angleOffset);
        SmartDashboard.putNumber("Desired Angle", this.desiredAngle);
        SmartDashboard.putNumber("output", output);
    }
}
