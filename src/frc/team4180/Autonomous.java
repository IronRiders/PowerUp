package frc.team4180;

import java.util.Stack;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4180.LambdaJoystick.ThrottlePosition;

public class Autonomous {

    private Stack<BooleanSupplier> actions; // true to halt, false to continue
    private final Robot robot;
    private Timer autoTime;
    private final double speed = 0.3; // Motor power
    private final double turnSpeed = 0.6; // Motor power

    Autonomous(Robot robot, boolean isRightSide, boolean switchRight, char same, char opp){
        int turnAngle = isRightSide ? -1 : 1;
        this.robot = robot;
        robot.cubePusher.reset();
        this.actions = new Stack<>();
        autoTime = new Timer();
        String autoMode = "None";

        if(isRightSide==switchRight){
            if (same == 'F') {
                autoMode = "[S] Just Forward";
                addAction(() -> depositBlock());
                addAction(() -> drive(6.5));
                addAction(() -> startDrive());
            }
            else if (same == 'T'){
                autoMode = "[S] Forward & Turn";
                addAction(() -> depositBlock());
                addAction(() -> turn(turnAngle*70,turnAngle));
                addAction(() -> startTurn());
                addAction(() -> drive(7));
                addAction(() -> startDrive());
            }
        }else {
            if(opp == 'B'){
                autoMode = "[O] Baseline";
                addAction(() -> drive(6));
                addAction(() -> startDrive());
            }
            else if(opp == 'S'){
                autoMode = "[O] Sort Track";
            }
            else if(opp == 'L'){
                autoMode = "[O] Long Track";
            }
        }
        autoMode = autoMode + " - " + (isRightSide ? "Right" : "Left");
        SmartDashboard.putString("DB/String 6", autoMode);
    }

    public void run() {
        try {
            final boolean stop = actions.peek().getAsBoolean();
            if(stop) {
                actions.pop();
            }
        }catch (Exception e){
            updateDB("Done");
        }
    }

    public void addAction(final BooleanSupplier action) {
        actions.push(action);
    }

    public boolean drive(final double distance) {
        updateDB("Driving: " + distance);
        if(robot.driveTrain.getDistance() < distance) {
            robot.driveTrain.updateSpeed(new ThrottlePosition(0, -speed));
            return false;
        }
        robot.driveTrain.updateSpeed(new ThrottlePosition(0, 0));
        return true;
    }

    public boolean turn(double degrees, int direction) {
        double turn = Math.abs(robot.gyro.getAngleZ() - degrees);
        updateDB("Turning: " + turn);
        if(turn > 5) {
            robot.driveTrain.updateSpeed(new ThrottlePosition(turnSpeed*direction, 0));
            return false;
        }
        robot.driveTrain.updateSpeed(new ThrottlePosition(0, 0));
        return true;
    }

    public boolean wait(double seconds){
        double time = autoTime.get();
        updateDB("Waiting " + seconds + " - " + time);
        return seconds < time;
    }

    public boolean depositBlock() {
        updateDB("Depositing Cube");
        robot.cubePusher.extend();
        return true;
    }

    public boolean startDrive() {
        updateDB("Start Drive");
        robot.driveTrain.reset();
        return true;
    }

    public boolean startTurn() {
        updateDB("Start Turn");
        robot.gyro.reset();
        return true;
    }

    public boolean startWait() {
        updateDB("Start Wait");
        autoTime.reset();
        autoTime.start();
        return true;
    }

    private void updateDB(String s){
        SmartDashboard.putString("DB/String 5", s);
    }

}
