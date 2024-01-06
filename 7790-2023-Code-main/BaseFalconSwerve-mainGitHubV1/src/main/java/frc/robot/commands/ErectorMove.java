// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.DoubleSupplier;
import frc.robot.subsystems.Erector;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ErectorMove extends CommandBase
{
    private Erector s_Erector;
    private DoubleSupplier joyAngle;
    
    public ErectorMove(final Erector s_Erector, final DoubleSupplier joyAngle) {
        this.s_Erector = s_Erector;
        this.joyAngle = joyAngle;
        this.addRequirements(s_Erector);
    }
    
    @Override
    public void execute() {
        final float erectorAngleVal = (float)MathUtil.applyDeadband(this.joyAngle.getAsDouble(), 0.06);
        this.s_Erector.moveByJoystickAmount(erectorAngleVal);
    }
}
