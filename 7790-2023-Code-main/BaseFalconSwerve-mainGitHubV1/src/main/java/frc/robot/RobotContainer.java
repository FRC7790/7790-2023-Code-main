

package frc.robot;

import frc.robot.autos.PickupHuman;
import frc.robot.autos.PlaceObject;
import frc.robot.autos.PickUpObjectFast;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ErectorMove;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.TeleopSwerve;
import frc.robot.autos.AutoObject2;
import frc.robot.autos.AutoObject;
import frc.robot.autos.Auto1ObjBal;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Erector;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Extender;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

public class RobotContainer
{
    private final Joystick driver;
    private final Joystick buttonBox;
    private final Joystick gunner;
    private final int translationAxis;
    private final int strafeAxis;
    private final int rotationAxis;
    private final int armAxisUp;
    private final int armAxisDown;
    private final int armAxisJoy = 1;
    private final JoystickButton zeroGyro;
    private final JoystickButton robotCentric;
    private final JoystickButton targeting;
    private final JoystickButton humanPickup;
    private final JoystickButton cubePickup;
    private final JoystickButton extendStage1;
    private final JoystickButton unextendStage1;
    private final JoystickButton extendStage2;
    private final JoystickButton unextendStage2;
    private final JoystickButton extendStageAll;
    private final JoystickButton unextendAll;
    private final JoystickButton clawOpen;
    private final JoystickButton clawClose;
    private final JoystickButton erectorDown;
    private final JoystickButton erectorUp;
    private final JoystickButton target1;
    private final JoystickButton target2;
    private final JoystickButton target3;
    private final JoystickButton target4;
    private final JoystickButton target5;
    private final JoystickButton target6;
    private final JoystickButton target7;
    private final JoystickButton target8;
    private final JoystickButton target9;
    public final Swerve s_Swerve;
    public final Vision s_Vision;
    public final Extender s_Extender;
    public final Arm s_Arm;
    public TargetClass currentTarget;
    public final Erector s_Erector;
    private final Command m_Auto1ObjBal;
    private final Command m_AutoObject;
    private final Command m_AutoObject2;
    SendableChooser<Command> m_chooser;
    
    public RobotContainer() {
        this.driver = new Joystick(0);
        this.buttonBox = new Joystick(1);
        this.gunner = new Joystick(2);
        this.translationAxis = XboxController.Axis.kLeftY.value;
        this.strafeAxis = XboxController.Axis.kLeftX.value;
        this.rotationAxis = XboxController.Axis.kRightX.value;
        this.armAxisUp = XboxController.Axis.kRightTrigger.value;
        this.armAxisDown = XboxController.Axis.kLeftTrigger.value;
        this.zeroGyro = new JoystickButton(this.driver, XboxController.Button.kY.value);
        this.robotCentric = new JoystickButton(this.driver, XboxController.Button.kLeftBumper.value);
        this.targeting = new JoystickButton(this.driver, XboxController.Button.kRightBumper.value);
        this.humanPickup = new JoystickButton(this.driver, XboxController.Button.kX.value);
        this.cubePickup = new JoystickButton(this.driver, XboxController.Button.kA.value);
        this.extendStage1 = new JoystickButton(this.gunner, 8);
        this.unextendStage1 = new JoystickButton(this.gunner, 7);
        this.extendStage2 = new JoystickButton(this.gunner, 10);
        this.unextendStage2 = new JoystickButton(this.gunner, 9);
        this.extendStageAll = new JoystickButton(this.gunner, 5);
        this.unextendAll = new JoystickButton(this.gunner, 3);
        this.clawOpen = new JoystickButton(this.gunner, 12);
        this.clawClose = new JoystickButton(this.gunner, 11);
        this.erectorDown = new JoystickButton(this.gunner, 2);
        this.erectorUp = new JoystickButton(this.gunner, 1);
        this.target1 = new JoystickButton(this.buttonBox, 1);
        this.target2 = new JoystickButton(this.buttonBox, 2);
        this.target3 = new JoystickButton(this.buttonBox, 3);
        this.target4 = new JoystickButton(this.buttonBox, 4);
        this.target5 = new JoystickButton(this.buttonBox, 5);
        this.target6 = new JoystickButton(this.buttonBox, 6);
        this.target7 = new JoystickButton(this.buttonBox, 7);
        this.target8 = new JoystickButton(this.buttonBox, 8);
        this.target9 = new JoystickButton(this.buttonBox, 9);
        this.s_Swerve = new Swerve();
        this.s_Vision = new Vision();
        this.s_Extender = new Extender();
        this.s_Arm = new Arm();
        this.currentTarget = new TargetClass(1);
        this.s_Erector = new Erector();
        this.m_Auto1ObjBal = new Auto1ObjBal(this.s_Arm, this.s_Extender, this.s_Swerve, this.s_Vision, this.currentTarget);
        this.m_AutoObject = new AutoObject(this.s_Arm, this.s_Extender, this.s_Swerve, this.s_Vision, this.currentTarget);
        this.m_AutoObject2 = new AutoObject2(this.s_Arm, this.s_Extender, this.s_Swerve, this.s_Vision, this.currentTarget);
        this.m_chooser = new SendableChooser<Command>();
        this.s_Swerve.setDefaultCommand(new TeleopSwerve(this.s_Swerve, () -> this.driver.getRawAxis(this.translationAxis), () -> this.driver.getRawAxis(this.strafeAxis), () -> this.driver.getRawAxis(this.rotationAxis), () -> this.robotCentric.getAsBoolean()));
        this.s_Arm.setDefaultCommand(new ArmCommand(this.s_Arm, () -> this.driver.getRawAxis(this.armAxisUp), () -> this.driver.getRawAxis(this.armAxisDown), () -> this.gunner.getRawAxis(1)));
        this.s_Erector.setDefaultCommand(new ErectorMove(this.s_Erector, () -> this.gunner.getRawAxis(2)));
        this.configureButtonBindings();
        this.m_chooser.addOption("2 objects", this.m_AutoObject);
        this.m_chooser.addOption("object then balance", this.m_AutoObject);
        SmartDashboard.putData(this.m_chooser);
    }
    
    private void configureButtonBindings() {
        this.zeroGyro.onTrue(new InstantCommand(() -> this.s_Swerve.zeroGyroscope(), new Subsystem[0]));
        this.cubePickup.whileTrue(new PickUpObjectFast(this.s_Arm, this.s_Extender, this.s_Vision, this.s_Swerve));
        this.targeting.whileTrue(new PlaceObject(this.s_Arm, this.s_Extender, this.s_Vision, this.s_Swerve, this.currentTarget));
        this.humanPickup.whileTrue(new PickupHuman(this.s_Arm, this.s_Extender, this.s_Vision, this.s_Swerve, this.currentTarget));
        this.extendStage1.onTrue(new InstantCommand(() -> this.s_Extender.extend1(), new Subsystem[0]));
        this.unextendStage1.onTrue(new InstantCommand(() -> this.s_Extender.retract1(), new Subsystem[0]));
        this.extendStage2.onTrue(new InstantCommand(() -> this.s_Extender.extend2(), new Subsystem[0]));
        this.unextendStage2.onTrue(new InstantCommand(() -> this.s_Extender.retract2(), new Subsystem[0]));
        this.extendStageAll.onTrue(new InstantCommand(() -> this.s_Extender.extendAll(), new Subsystem[0]));
        this.unextendAll.onTrue(new InstantCommand(() -> this.s_Extender.retractAll(), new Subsystem[0]));
        this.clawOpen.onTrue(new InstantCommand(() -> this.s_Extender.openClaw(), new Subsystem[0]));
        this.clawClose.onTrue(new InstantCommand(() -> this.s_Extender.closeClaw(), new Subsystem[0]));
        this.target1.onTrue(new InstantCommand(() -> this.currentTarget.setCurrentTarget(1), new Subsystem[0]));
        this.target2.onTrue(new InstantCommand(() -> this.currentTarget.setCurrentTarget(2), new Subsystem[0]));
        this.target3.onTrue(new InstantCommand(() -> this.currentTarget.setCurrentTarget(3), new Subsystem[0]));
        this.target4.onTrue(new InstantCommand(() -> this.currentTarget.setCurrentTarget(4), new Subsystem[0]));
        this.target5.onTrue(new InstantCommand(() -> this.currentTarget.setCurrentTarget(5), new Subsystem[0]));
        this.target6.onTrue(new InstantCommand(() -> this.currentTarget.setCurrentTarget(6), new Subsystem[0]));
        this.target7.onTrue(new InstantCommand(() -> this.currentTarget.setCurrentTarget(7), new Subsystem[0]));
        this.target8.onTrue(new InstantCommand(() -> this.currentTarget.setCurrentTarget(8), new Subsystem[0]));
        this.target9.onTrue(new InstantCommand(() -> this.currentTarget.setCurrentTarget(9), new Subsystem[0]));
        this.erectorDown.onTrue(new InstantCommand(() -> this.s_Erector.erectorDown(), new Subsystem[0]));
        this.erectorDown.onFalse(new InstantCommand(() -> this.s_Erector.erectorMiddle(), new Subsystem[0]));
        this.erectorUp.onTrue(new InstantCommand(() -> this.s_Erector.erectorUp(), new Subsystem[0]));
    }
    
    public Command getAutonomousCommand() {
        return this.m_Auto1ObjBal;
    //  return this.m_AutoObject2;
    }
}
