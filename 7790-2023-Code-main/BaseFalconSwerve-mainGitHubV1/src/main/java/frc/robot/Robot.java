// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot
{
    public static CTREConfigs ctreConfigs;
    private Command m_autonomousCommand;
    private RobotContainer m_robotContainer;
    
    @Override
    public void robotInit() {
        Robot.ctreConfigs = new CTREConfigs();
        this.m_robotContainer = new RobotContainer();
    }
    
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }
    
    @Override
    public void disabledInit() {
    }
    
    @Override
    public void disabledPeriodic() {
    }
    
    @Override
    public void autonomousInit() {
        this.m_autonomousCommand = this.m_robotContainer.getAutonomousCommand();
        if (this.m_autonomousCommand != null) {
            this.m_autonomousCommand.schedule();
        }
    }
    
    @Override
    public void autonomousPeriodic() {
    }
    
    @Override
    public void teleopInit() {
        if (this.m_autonomousCommand != null) {
            this.m_autonomousCommand.cancel();
        }
    }
    
    @Override
    public void teleopPeriodic() {
    }
    
    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }
    
    @Override
    public void testPeriodic() {
    }
}
