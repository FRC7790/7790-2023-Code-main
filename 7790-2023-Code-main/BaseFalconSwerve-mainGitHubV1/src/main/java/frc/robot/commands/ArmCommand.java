// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.DoubleSupplier;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmCommand extends CommandBase
{
    private Arm s_Arm;
    private DoubleSupplier armAngleUp;
    private DoubleSupplier armAngleDown;
    private DoubleSupplier armJoy;
    
    public ArmCommand(final Arm s_Arm, final DoubleSupplier armAngleUp, final DoubleSupplier armAngleDown, final DoubleSupplier armJoy) {
        this.s_Arm = s_Arm;
        this.armAngleUp = armAngleUp;
        this.armAngleDown = armAngleDown;
        this.armJoy = armJoy;
        this.addRequirements(s_Arm);
    }
    
    @Override
    public void execute() {
        final double armAngleUpVal = MathUtil.applyDeadband(this.armAngleUp.getAsDouble(), 0.06);
        final double armAngleDownVal = MathUtil.applyDeadband(this.armAngleDown.getAsDouble(), 0.06);
        final double armJoyVal = MathUtil.applyDeadband(this.armJoy.getAsDouble(), 0.06);
        final float up = (float)armAngleUpVal * 2.0f;
        final float down = (float)armAngleDownVal * 2.0f;
        final float joy = (float)armJoyVal;
        float amount = up - down + joy;
        this.s_Arm.moveAmount(amount);
        if (amount < Math.abs(0.1)) {
            amount = 0.0f;
        }
        else {
            amount = up - down + joy;
        }
    }
}
