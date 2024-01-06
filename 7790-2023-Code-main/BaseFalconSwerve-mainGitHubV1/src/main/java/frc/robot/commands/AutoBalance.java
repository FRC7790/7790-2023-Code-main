// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalance extends CommandBase
{
    private Swerve s_Swerve;
    boolean isSlowDown;
    public int mode;
    public int tipCount;
    boolean isTipped;
    
    public AutoBalance(final Swerve s_Swerve) {
        this.isSlowDown = false;
        this.mode = 0;
        this.tipCount = 0;
        this.isTipped = false;
        this.s_Swerve = s_Swerve;
        this.addRequirements(s_Swerve);
    }
    
    @Override
    public void execute() {
        final double roll = this.s_Swerve.getPitch();
        SmartDashboard.putNumber("Roll", roll);
        float moveX = 0.0f;
        float speed = 2.0f;
        float multiplier = 0.06f;
        if (roll > 10.0) {
            this.isTipped = false;
        }
        if (roll < -10.0) {
            this.isSlowDown = true;
            if (!this.isTipped) {
                this.isTipped = true;
                ++this.tipCount;
                System.out.println("tips " + this.tipCount);
            }
        }
        if (this.isSlowDown) {
            multiplier = 0.037f;
            speed = 0.51f - (float)(this.tipCount * 0.18);
        }
        moveX = (float)(roll * multiplier);
        moveX = (float)MathUtil.clamp(moveX, -speed, speed);
        this.s_Swerve.drive(new Translation2d(-moveX, 0.0), 0.0, false, true);
    }
}
