package frc.team4180;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class AutonomousAlexs {
    ArrayList<Callable<Boolean>> actions;
    int actionIndex;

    Robot robot;

    double yaw;

    private final double speed = 0.3;

    public AutonomousAlexs(Robot robot){
        this.robot = robot;

        yaw = robot.gyro.getYaw();

        actionIndex = 0;
    }

    public void run() throws Exception {
        boolean stop = actions.get(actionIndex).call();
        if(stop){
            actionIndex++;
        }
    }

    public boolean drive(double distance){
        if(robot.driveTrain.getDistance() < distance) {
            robot.driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0, speed));
            return false;
        }
        return true;
    }

    public boolean turn(double degrees){
        if(Math.abs(robot.gyro.getYaw() - yaw - degrees) < 7){
            robot.driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(speed, 0));
            return false;
        }
        return true;
    }

    public boolean depositBlock(){
        //robot push with piston
        return true;
    }

    public boolean startDrive(){
        robot.driveTrain.reset();
        return true;
    }

    public boolean startTurn(){
        yaw = robot.gyro.getYaw();
        return true;
    }
}
