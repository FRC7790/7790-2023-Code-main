// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateCommand extends CommandBase
{
    private Swerve s_Swerve;
    private float desiredRotation;
    boolean isFinished;
    boolean isInit;
    double offset;
    PIDController pid;
    
    public RotateCommand(final Swerve s_Swerve, final float desiredRotation) {
        this.isFinished = false;
        this.isInit = false;
        this.offset = 0.0;
        this.pid = new PIDController(0.1, 0.0, 0.0);
        this.s_Swerve = s_Swerve;
        this.desiredRotation = (float)this.NormalizeAngle(desiredRotation);
        this.addRequirements(s_Swerve);
    }
    
    @Override
    public void execute() {
        double yaw = this.s_Swerve.getYaw().getDegrees();
        if (!this.isInit) {
            this.offset = yaw;
            this.isInit = true;
        }
        yaw = this.NormalizeAngle(yaw - this.offset);
        final float maxoutput = 4.0f;
        final double output = MathUtil.clamp(this.pid.calculate(yaw, this.desiredRotation), -maxoutput, maxoutput);
        SmartDashboard.putNumber("yaw of auto", yaw);
        SmartDashboard.putNumber("desired rotation", this.desiredRotation);
        final float error = (float)yaw - this.desiredRotation;
        if (Math.abs(error) < 0.5) {
            this.isFinished = true;
            this.s_Swerve.drive(new Translation2d(0.0, 0.0), 0.0, true, true);
        }
        else {
            this.s_Swerve.drive(new Translation2d(0.0, 0.0), -output, true, true);
        }
    }
    
    private double NormalizeAngle(double yaw) {
        while (yaw > 180.0) {
            yaw -= 360.0;
        }
        while (yaw < -180.0) {
            yaw += 360.0;
        }
        return yaw;
    }
    
    @Override
    public boolean isFinished() {
        return this.isFinished;
    }
}
