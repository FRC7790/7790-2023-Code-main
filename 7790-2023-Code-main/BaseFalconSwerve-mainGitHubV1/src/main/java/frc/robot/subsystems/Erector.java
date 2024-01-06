// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Erector extends SubsystemBase
{
    private WPI_VictorSPX erectorMotor;
    private Encoder erectorEncoder;
    private float desiredAngle;
    private float erectorUpAngle;
    private float erectorMiddleAngle;
    private float erectorDownAngle;
    private boolean isInit;
    PIDController erectorPid;
    DigitalInput homeingSwitch;
    
    public Erector() {
        this.desiredAngle = 0.0f;
        this.erectorUpAngle = 5.0f;
        this.erectorMiddleAngle = 70.0f;
        this.erectorDownAngle = 140.0f;
        this.isInit = false;
        this.erectorPid = new PIDController(0.008, 0.0, 0.0);
        this.homeingSwitch = new DigitalInput(2);
        this.erectorMotor = new WPI_VictorSPX(35);
        (this.erectorEncoder = new Encoder(0, 1, false, CounterBase.EncodingType.k4X)).setDistancePerPulse(0.0054931640625);
    }
    
    public void setDesiredAngle(final float desiredAngle) {
        this.desiredAngle = (float)MathUtil.clamp(desiredAngle, this.erectorUpAngle, this.erectorDownAngle);
    }
    
    public void moveByJoystickAmount(final float f) {
        this.setDesiredAngle(this.desiredAngle + f);
    }
    
    public void erectorEncoderReset() {
        this.erectorEncoder.reset();
    }
    
    public void erectorUp() {
        this.isInit = false;
        this.setDesiredAngle(0.0f);
    }
    
    public void erectorMiddle() {
        this.setDesiredAngle(this.erectorMiddleAngle);
    }
    
    public void erectorDown() {
        this.setDesiredAngle(this.erectorDownAngle);
    }
    
    @Override
    public void periodic() {
        if (!this.isInit) {
            this.erectorMotor.set(-0.10000000149011612);
            if (this.homeingSwitch.get()) {
                this.erectorEncoderReset();
                this.isInit = true;
            }
        }
        else {
            final float maxoutputUp = 0.4f;
            final float maxoutputDown = 0.3f;
            final double error = this.erectorEncoder.getDistance() - this.desiredAngle;
            float maxMotorActual;
            if (error > 0.0) {
                maxMotorActual = maxoutputUp;
            }
            else {
                maxMotorActual = maxoutputDown;
            }
            final double output = MathUtil.clamp(this.erectorPid.calculate(this.erectorEncoder.getDistance(), this.desiredAngle), -maxMotorActual, maxMotorActual);
            this.erectorMotor.set(output);
            SmartDashboard.putNumber("Erectoroutput", output);
        }
        SmartDashboard.putNumber("Erector Angle", this.erectorEncoder.getDistance());
        SmartDashboard.putNumber("Erector Desired Angle", this.desiredAngle);
    }
}
