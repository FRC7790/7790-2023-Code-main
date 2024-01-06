// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.TargetClass;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Targeting extends CommandBase
{
    private Swerve s_Swerve;
    private Vision s_Vision;
    private TargetClass target;
    private boolean finished;
    PIDController pidX;
    PIDController pidZ;
    PIDController pidRot;
    
    public Targeting(final Swerve s_Swerve, final Vision s_Vision, final TargetClass target) {
        this.finished = false;
        this.pidX = new PIDController(4.0, 0.0, 0.2);
        this.pidZ = new PIDController(6.0, 0.0, 0.2);
        this.pidRot = new PIDController(6.0, 0.0, 0.2);
        this.s_Swerve = s_Swerve;
        this.s_Vision = s_Vision;
        this.target = target;
        this.addRequirements(s_Swerve);
        this.addRequirements(s_Vision);
    }
    
    @Override
    public void initialize() {
        this.finished = false;
    }
    
    @Override
    public void execute() {
        this.s_Vision.setPipeline(0);
        if (this.s_Vision.getTruePipeline() == 0) {
            if (this.s_Vision.getTargetId() > 8 || this.s_Vision.getTargetId() < 1) {
                this.finished = false;
                this.s_Swerve.drive(new Translation2d(0.0, 0.0), 0.0, false, true);
                return;
            }
            final Pose2d pose = this.target.getTargetCoord();
            float targetX = (float)pose.getX();
            float targetZ = (float)pose.getY();
            SmartDashboard.putNumber("target x", targetX);
            SmartDashboard.putNumber("target z", targetZ);
            final Pose3d targetPose = this.s_Vision.getTargetPose();
            if (this.s_Vision.getTargetId() == 4 || this.s_Vision.getTargetId() == 5) {
                SmartDashboard.putNumber("meow", this.target.getCurrentTarget());
                if (this.target.getCurrentTarget() == 7) {
                    targetX = 0.8f;
                    targetZ = 1.3f;
                }
                else {
                    targetX = -0.8f;
                    targetZ = 1.3f;
                }
            }
            System.out.println("targeting: " + targetPose.getZ() + ", " + targetZ);
            float movex = -(float)this.pidX.calculate(targetPose.getX(), targetX);
            float movez = -(float)this.pidZ.calculate(targetPose.getZ(), -targetZ);
            float rot = -(float)this.pidRot.calculate(targetPose.getRotation().getY(), 0.0);
            if (rot > 180.0f) {
                rot -= 360.0f;
            }
            if (Math.abs(movex) < 0.2 && Math.abs(movez) < 0.2 && Math.abs(rot) < 0.05) {
                this.finished = true;
                movex = 0.0f;
                movez = 0.0f;
                rot = 0.0f;
                this.s_Swerve.drive(new Translation2d(movez, -movex), rot, false, true);
            }
            else {
                this.finished = false;
                SmartDashboard.putNumber("move x", movex);
                SmartDashboard.putNumber("move z", movez);
                SmartDashboard.putNumber("move rot", rot);
                final float speedMax = 1.0f;
                final float rotMax = 1.0f;
                movez = (float)MathUtil.clamp(movez, -speedMax, speedMax);
                movex = (float)MathUtil.clamp(movex, -speedMax, speedMax);
                rot = (float)MathUtil.clamp(rot * 1.0f, -rotMax, rotMax);
                this.s_Swerve.drive(new Translation2d(movez, -movex), -rot, false, true);
            }
        }
    }
    
    @Override
    public boolean isFinished() {
        return this.finished;
    }
}
