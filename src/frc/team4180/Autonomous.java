package frc.team4180;

import java.util.Stack;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4180.LambdaJoystick.ThrottlePosition;

public class Autonomous {

    private Stack<BooleanSupplier> actions; // true to halt, false to continue
    private final Robot robot;
    private final double speed = 0.3; // Motor power
    private final double turnSpeed = 0.6; // Motor power


    //THIS WILL NOT WORK YOU MUST CONVERT THE TYPES!
    Autonomous(Robot robot, boolean isRightSide, boolean switchRight, char same, char opp){
        int turnAngle = isRightSide ? -1 : 1;
        this.robot = robot;
        robot.cubePusher.reset();
        this.actions = new Stack<>();
        if(isRightSide==switchRight){
            if(same=='F') {
                SmartDashboard.putString("DB/String 6","Just Forward - "+ (isRightSide ? "Right":"Left"));
                addAction(() -> depositBlock());
                addAction(() -> drive(6.5));
                addAction(() -> startDrive());
            }
            else if (same == 'T'){
                SmartDashboard.putString("DB/String 6","Forward & Turn - "+ (isRightSide ? "Right":"Left"));

                addAction(() -> depositBlock());
                addAction(() -> turn(turnAngle*70,turnAngle));
                addAction(() -> startTurn());
                addAction(() -> drive(7));
                addAction(() -> startDrive());
            }
        }else {
            if(opp == 'B'){
                SmartDashboard.putString("DB/String 6","Baseline - "+ (isRightSide ? "Right":"Left"));

                addAction(() -> drive(6));
                addAction(() -> startDrive());
            }
            else if(opp == 'S'){
                SmartDashboard.putString("DB/String 6","Sort Track - "+ (isRightSide ? "Right":"Left"));
            }
            else if(opp == 'L'){
                SmartDashboard.putString("DB/String 6","Long Track - "+ (isRightSide ? "Right":"Left"));
            }
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

    public boolean turn(double degrees, int direction) {
        double turn = Math.abs(robot.gyro.getAngleZ() - degrees);
        SmartDashboard.putString("DB/String 5", "turn " + turn);
        if( turn > 5) {
            robot.driveTrain.updateSpeed(new ThrottlePosition(turnSpeed*direction, 0));
            return false;
        }
        robot.driveTrain.updateSpeed(new ThrottlePosition(0, 0));
        return true;
    }

    public boolean depositBlock() {
        robot.cubePusher.extend();
        return true;
    }

    public boolean startDrive() {
        SmartDashboard.putString("DB/String 5", "start drive");
        robot.driveTrain.reset();
        return true;
    }

    public boolean startTurn() {
        robot.gyro.reset();
        return true;
    }

}
