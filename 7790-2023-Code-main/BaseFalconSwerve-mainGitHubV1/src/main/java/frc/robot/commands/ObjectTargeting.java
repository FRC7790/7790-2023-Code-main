// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ObjectTargeting extends CommandBase
{
    private Swerve s_Swerve;
    private Vision s_Vision;
    private boolean finished;
    PIDController pidObjX;
    PIDController pidObjY;
    
    public ObjectTargeting(final Swerve s_Swerve, final Vision s_Vision) {
        this.finished = false;
        this.pidObjX = new PIDController(0.07, 0.0, 0.0);
        this.pidObjY = new PIDController(0.03, 0.0, 0.0);
        this.s_Swerve = s_Swerve;
        this.s_Vision = s_Vision;
        this.addRequirements(s_Swerve);
        this.addRequirements(s_Vision);
    }
    
    @Override
    public void initialize() {
        this.finished = false;
    }
    
    @Override
    public void execute() {
        this.s_Vision.setPipeline(1);
        if (this.s_Vision.getTruePipeline() == 1) {
            String objType = this.s_Vision.getObjType();
            final float offsetX = 0.0f;
            float offsetY = 0.0f;
            if (objType.length() > 3) {
                objType = objType.substring(0, 4);
            }
            if (!objType.equalsIgnoreCase("Cube")) {
                if (!objType.equalsIgnoreCase("cone")) {
                    this.finished = false;
                    return;
                }
                offsetY = 0.5f;
            }
            final Pose2d pose = this.s_Vision.getCubePose();
            float objX = -(float)this.pidObjX.calculate(pose.getX(), offsetX);
            float objY = -(float)this.pidObjY.calculate(pose.getY(), offsetY);
            SmartDashboard.putNumber("Object offset", offsetY);
            SmartDashboard.putNumber("Object X Value", objX);
            SmartDashboard.putNumber("Object Y Value", objY);
            if (Math.abs(objX) < 0.09 && Math.abs(objY) < 0.02) {
                this.finished = true;
                objX = 0.0f;
                objY = 0.0f;
            }
            else {
                this.finished = false;
            }
            final float speedMax = 0.2f;
            final float maxRot = 0.75f;
            objY = (float)MathUtil.clamp(objY, -speedMax, speedMax);
            objX = (float)MathUtil.clamp(objX, -maxRot, maxRot);
            this.s_Swerve.drive(new Translation2d(-objY, 0.0).times(4.5), objX, false, true);
        }
        else {
            this.finished = false;
        }
    }
    
    @Override
    public boolean isFinished() {
        return this.finished;
    }
}
