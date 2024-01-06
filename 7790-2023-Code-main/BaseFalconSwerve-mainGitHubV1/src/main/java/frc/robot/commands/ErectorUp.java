// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Erector;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ErectorUp extends CommandBase
{
    Erector m_erector;
    
    public ErectorUp(final Erector erector) {
        this.m_erector = erector;
        this.addRequirements(erector);
    }
    
    @Override
    public void execute() {
        this.m_erector.erectorUp();
    }
}
