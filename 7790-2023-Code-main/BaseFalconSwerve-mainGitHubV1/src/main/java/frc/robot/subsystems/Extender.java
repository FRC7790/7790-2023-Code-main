// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.subsystems;

import frc.robot.TargetClass;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Extender extends SubsystemBase
{
    DoubleSolenoid stage1;
    DoubleSolenoid stage2;
    Solenoid claw;
    
    public Extender() {
        this.stage1 = new DoubleSolenoid(33, PneumaticsModuleType.CTREPCM, 0, 1);
        this.stage2 = new DoubleSolenoid(33, PneumaticsModuleType.CTREPCM, 2, 3);
        this.claw = new Solenoid(33, PneumaticsModuleType.CTREPCM, 4);
        final Compressor pcmCompressor = new Compressor(33, PneumaticsModuleType.CTREPCM);
        pcmCompressor.enableDigital();
        this.retractAll();
    }
    
    public void retract1() {
        this.stage1.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void retract2() {
        this.stage2.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void retractAll() {
        this.retract1();
        this.retract2();
    }
    
    public void extend1() {
        this.stage1.set(DoubleSolenoid.Value.kForward);
    }
    
    public void extend2() {
        this.stage2.set(DoubleSolenoid.Value.kForward);
    }
    
    public void extendAll() {
        this.extend1();
        this.extend2();
    }
    
    public void extendToTarget(final TargetClass target) {
        if (target.getCurrentTarget() == 1 || target.getCurrentTarget() == 2 || target.getCurrentTarget() == 3) {
            this.extendAll();
        }
        if (target.getCurrentTarget() == 4 || target.getCurrentTarget() == 5 || target.getCurrentTarget() == 6) {
            this.extend2();
        }
    }
    
    public void openClaw() {
        this.claw.set(true);
    }
    
    public void closeClaw() {
        this.claw.set(false);
    }
}
