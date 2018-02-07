package frc.team4180;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class AutonomousAlexs {
    ArrayList<Callable<Boolean>> actions;
    int actionIndex;

    Robot robot;

    double yaw;
    boolean right;
    double sameSideDistance1;
    double oppositeSideDistance1;
    double turnDegrees = 90;
    double sameSideDistance2;
    double oppositeSideDistance2;
    double oppositeSideDistance3;

    // is right negative or not?

    private final double speed = 0.3;

    public AutonomousAlexs(Robot robot){
        this.robot = robot;

        yaw = robot.gyro.getYaw();

        actionIndex = 0;

        char switchPosition = DriverStation.getInstance().getGameSpecificMessage().charAt(0); {
        }
        final String startingPosition = SmartDashboard.getString("/SmartDashboard/autonomous/selected_starting_position", null).toUpperCase();
        char startingChar = startingPosition.charAt(0);
        right = startingChar == 'L';
        if(startingChar == switchPosition){
            actions.add(()->drive(sameSideDistance1));
            actions.add(()-> turn(turnDegrees));
            actions.add(()->drive(sameSideDistance2));

        } else {
            actions.add(()->drive(oppositeSideDistance1));
            actions.add(()-> turn(turnDegrees));
            actions.add(()->drive(oppositeSideDistance2));
            actions.add(()-> turn(turnDegrees));
            actions.add(()-> drive(oppositeSideDistance3));
        }
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
        degrees = right? -1*degrees : degrees; // not sure if this is the right order
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
