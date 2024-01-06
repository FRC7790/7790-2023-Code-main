// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopSwerve extends CommandBase
{
    private Swerve s_Swerve;
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;
    
    public TeleopSwerve(final Swerve s_Swerve, final DoubleSupplier translationSup, final DoubleSupplier strafeSup, final DoubleSupplier rotationSup, final BooleanSupplier robotCentricSup) {
        this.s_Swerve = s_Swerve;
        this.addRequirements(s_Swerve);
        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
    }
    
    @Override
    public void execute() {
        double translationVal = MathUtil.applyDeadband(this.translationSup.getAsDouble(), 0.06);
        double strafeVal = MathUtil.applyDeadband(this.strafeSup.getAsDouble(), 0.06);
        final double rotationVal = MathUtil.applyDeadband(this.rotationSup.getAsDouble(), 0.06);
        if (!this.robotCentricSup.getAsBoolean()) {
            translationVal *= -1.0;
            strafeVal *= -1.0;
        }
        this.s_Swerve.drive(new Translation2d(translationVal, strafeVal).times(4.5), rotationVal * 3.0, !this.robotCentricSup.getAsBoolean(), true);
        if (translationVal < Math.abs(0.1)) {
            translationVal = 0.0;
        }
        else {
            translationVal = MathUtil.applyDeadband(this.translationSup.getAsDouble(), 0.06);
        }
    }
}
