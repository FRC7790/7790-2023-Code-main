// 
// Decompiled by Procyon v0.5.36
// 

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase
{
    private long m_tid;
    private double m_x;
    private double m_y;
    private double m_z;
    private double m_cubeX;
    private double m_cubeY;
    private double m_coneX;
    private double m_coneY;
    private String m_objType;
    private double m_pitch;
    private double m_yaw;
    private double m_roll;
    private int currentPipeline;
    private int m_truePipe;
    
    public Vision() {
        this.currentPipeline = 0;
        this.m_truePipe = 0;
        this.setPipeline(1);
    }
    
    @Override
    public void periodic() {
        final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        this.m_truePipe = (int)table.getEntry("getpipe").getInteger(0L);
        if (this.m_truePipe == 0) {
            final double[] value = table.getEntry("botpose_targetspace").getDoubleArray(new double[6]);
            this.m_tid = table.getEntry("tid").getInteger(0L);
            this.m_x = value[0];
            this.m_y = value[1];
            this.m_z = value[2];
            this.m_roll = value[3] * 3.141592653589793 / 180.0;
            this.m_pitch = value[4] * 3.141592653589793 / 180.0;
            this.m_yaw = value[5] * 3.141592653589793 / 180.0;
            SmartDashboard.putNumber("X Value", value[0]);
            SmartDashboard.putNumber("Y Value", value[1]);
            SmartDashboard.putNumber("Z Value", value[2]);
            SmartDashboard.putNumber("Roll Value", value[3]);
            SmartDashboard.putNumber("pitch Value", value[4]);
            SmartDashboard.putNumber("Yaw Value", value[5]);
            SmartDashboard.putNumber("target", (double)this.m_tid);
        }
        else if (this.m_truePipe == 1 || this.m_truePipe == 2) {
            this.m_cubeX = table.getEntry("tx").getDouble(0.0);
            this.m_cubeY = table.getEntry("ty").getDouble(0.0);
            this.m_cubeX = table.getEntry("tx").getDouble(0.0);
            this.m_cubeY = table.getEntry("ty").getDouble(0.0);
            this.m_objType = table.getEntry("tclass").getString("");
        }
    }
    
    public Pose3d getTargetPose() {
        final Pose3d targetPose = new Pose3d(this.m_x, this.m_y, this.m_z, new Rotation3d(this.m_roll, this.m_pitch, this.m_yaw));
        return targetPose;
    }
    
    public Pose2d getCubePose() {
        final Pose2d objPose = new Pose2d(this.m_cubeX, this.m_cubeY, new Rotation2d());
        return objPose;
    }
    
    public Pose2d getConePose() {
        final Pose2d objPose = new Pose2d(this.m_coneX, this.m_coneY, new Rotation2d());
        return objPose;
    }
    
    public String getObjType() {
        return this.m_objType;
    }
    
    public int getTargetId() {
        return (int)this.m_tid;
    }
    
    public void setPipeline(final int pipeType) {
        if (this.currentPipeline != pipeType) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeType);
            this.currentPipeline = pipeType;
            this.m_objType = "";
            this.m_tid = 0L;
            System.out.print("pipe line =" + this.currentPipeline);
        }
    }
    
    public int getTruePipeline() {
        return this.m_truePipe;
    }
    
    @Override
    public void simulationPeriodic() {
    }
}
