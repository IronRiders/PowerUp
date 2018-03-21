package frc.team4180;

import java.util.Stack;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4180.LambdaJoystick.ThrottlePosition;

public class Autonomous {

    private Stack<BooleanSupplier> actions; // true to halt, false to continue
    private final Robot robot;
    private double yaw; // Degrees
    private final double speed = 0.3; // Motor power


    //THIS WILL NOT WORK YOU MUST CONVERT THE TYPES!
    Autonomous(Robot robot, boolean isRightSide, boolean switchRight){
        int turnAngle = isRightSide ? 90 : -90;
        this.robot = robot;
        robot.cubePusher.reset();
        this.yaw = robot.gyro.getYaw();
        this.actions = new Stack<>();
        if(isRightSide==switchRight){
            addAction(()->depositBlock());
            addAction(()->drive(6.5));
            addAction(()->startDrive());
        }
    }

    public void run() {
        try {
            final boolean stop = actions.peek().getAsBoolean();
            if(stop) {
                actions.pop();
            }
        }catch (Exception e){

        }
    }

    public void addAction(final BooleanSupplier action) {
        actions.push(action);
    }

    public boolean drive(final double distance) {
        SmartDashboard.putString("DB/String 5", "drive "+ distance);
        if(robot.driveTrain.getDistance() < distance) {
            robot.driveTrain.updateSpeed(new ThrottlePosition(0, -speed));
            return false;
        }
        robot.driveTrain.updateSpeed(new ThrottlePosition(0, 0));
        return true;
    }

    public boolean turn(double degrees) {
        if(Math.abs(robot.gyro.getYaw() - yaw - degrees) < 7) {
            robot.driveTrain.updateSpeed(new ThrottlePosition(speed, 0));
            return false;
        }
        return true;
    }

    public boolean depositBlock() {
        robot.cubePusher.extend();
        return true;
    }

    public boolean startDrive() {
        SmartDashboard.putString("DB/String 6", "start drive");
        robot.driveTrain.reset();
        return true;
    }

    public boolean startTurn() {
        yaw = robot.gyro.getYaw();
        return true;
    }

}
