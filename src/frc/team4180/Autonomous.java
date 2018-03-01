package frc.team4180;

import java.util.Stack;
import java.util.function.BooleanSupplier;
import frc.team4180.LambdaJoystick.ThrottlePosition;

public class Autonomous {

    private Stack<BooleanSupplier> actions; // true to halt, false to continue
    private final Robot robot;
    private double yaw; // Degrees
    private final double speed = 0.3; // Motor power

    Autonomous(final Robot robot, final BooleanSupplier... actions) {
        this.robot = robot;
        this.yaw = robot.gyro.getYaw();
        this.actions = new Stack<>();
        for(final BooleanSupplier supplier : actions) {
            this.actions.push(supplier);
        }

    }

    Autonomous(AutonomousMode mode, Robot robot, boolean isRightSide){
        int turnAngle = isRightSide ? 90:-90;

        this.robot = robot;
        if (mode.startingPosition.equals(mode.switchPosition)){
            addAction(() -> startDrive());
            addAction(() -> drive(141)); // Distances in inches, need to be reavaluated w/ encoders thought about, and tested.
            addAction(() -> startTurn());
            addAction(() -> turn(turnAngle));
            addAction(() -> startDrive());
            addAction(() -> drive(36));
            addAction(() -> depositBlock());
        } else if (! mode.startingPosition.equals(mode.switchPosition)) {
            addAction(() -> startDrive());
            addAction(() -> drive(216));
            addAction(() -> startTurn());
            addAction(() -> turn(turnAngle));
            addAction(() -> startDrive());
            addAction(() -> drive(252));
            addAction(() -> startTurn());
            addAction(() -> turn(turnAngle));
            addAction(() -> startDrive());
            addAction(() -> drive(72));
            addAction(() -> startTurn());
            addAction(() -> turn(turnAngle));
            addAction(() -> startDrive());
            addAction(() -> drive(37));
            addAction(() -> depositBlock());
        }
    }

    public void run() {
        final boolean stop = actions.peek().getAsBoolean();
        if(stop) {
            actions.pop();
        }
    }

    public void addAction(final BooleanSupplier action) {
        actions.push(action);
    }

    public boolean drive(final double distance) {
        if(robot.driveTrain.getDistance() < distance) {
            robot.driveTrain.updateSpeed(new ThrottlePosition(0, speed));
            return false;
        }
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
        robot.driveTrain.reset();
        return true;
    }

    public boolean startTurn() {
        yaw = robot.gyro.getYaw();
        return true;
    }
}
