package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;

public class TargetClass
{
    private int currentTarget;
    private double topRow;
    private double middleRow;
    private double bottomRow;
    private double leftColumn;
    private double middleColumn;
    private double rightColumn;
    
    public TargetClass(final int num) {
        this.currentTarget = 2;
        this.topRow = 1.1;
        this.middleRow = 1.1;
        this.bottomRow = 1.1;
        this.leftColumn = -0.55;
        this.middleColumn = 0.0;
        this.rightColumn = 0.66;
        this.setCurrentTarget(num);
    }
    
    public void setCurrentTarget(final int num) {
        this.currentTarget = num;
    }
    
    public int getCurrentTarget() {
        return this.currentTarget;
    }
    
    public Pose2d getTargetCoord() {
        double x = 0.0;
        double y = 0.0;
        switch (this.currentTarget) {
            case 1: {
                x = this.leftColumn;
                y = this.topRow;
                SmartDashboard.putNumber("Current Target", this.currentTarget);
                break;
            }
            case 2: {
                x = this.middleColumn;
                y = this.topRow;
                SmartDashboard.putNumber("Current Target", this.currentTarget);
                break;
            }
            case 3: {
                x = this.rightColumn;
                y = this.topRow;
                SmartDashboard.putNumber("Current Target", this.currentTarget);
                break;
            }
            case 4: {
                x = this.leftColumn;
                y = this.middleRow;
                SmartDashboard.putNumber("Current Target", this.currentTarget);
                break;
            }
            case 5: {
                x = this.middleColumn;
                y = this.middleRow;
                SmartDashboard.putNumber("Current Target", this.currentTarget);
                break;
            }
            case 6: {
                x = this.rightColumn;
                y = this.middleRow;
                SmartDashboard.putNumber("Current Target", this.currentTarget);
                break;
            }
            case 7: {
                x = this.leftColumn;
                y = this.bottomRow;
                SmartDashboard.putNumber("Current Target", this.currentTarget);
                break;
            }
            case 8: {
                x = this.middleColumn;
                y = this.bottomRow;
                SmartDashboard.putNumber("Current Target", this.currentTarget);
                break;
            }
            case 9: {
                x = this.rightColumn;
                y = this.bottomRow;
                SmartDashboard.putNumber("Current Target", this.currentTarget);
                break;
            }
        }
        final Pose2d pose = new Pose2d(x, y, new Rotation2d());
        return pose;
    }
}
