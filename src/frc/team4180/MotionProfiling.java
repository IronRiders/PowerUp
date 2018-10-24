package frc.team4180;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import frc.team4180.LambdaJoystick.ThrottlePosition;
import jaci.pathfinder.modifiers.TankModifier;
import edu.wpi.first.wpilibj.*;

import java.io.File;


public class MotionProfiling {
    // units in meters
    Robot robot;
    EncoderFollower left;
    EncoderFollower right;
    final double maxVelocity = 2.042; //meters/second
    final double wheelbaseWidth = 0.71; //0.71 meters is width of wheelbase
    final double wheelDiameter = 0.2032; //8 inches to meters
    final int numPulsesPerRevolution = 96; //????verify
    enum StartingPosition { LEFT, RIGHT };
    enum SwitchPosition { LEFT, RIGHT };
    public  StartingPosition startingPosition;
    public  SwitchPosition switchPosition;
    Encoder encoder;

    public MotionProfiling(Robot robot){
        this.robot = robot;
        encoder = robot.driveTrain.getEncoder();
        File file = initializeTrajectory();
        Trajectory trajectory = Pathfinder.readFromCSV(file);
        TankModifier modifier = new TankModifier(trajectory).modify(wheelbaseWidth);
        encoder.reset();
        left.configureEncoder(0, numPulsesPerRevolution , wheelDiameter);
        left.configurePIDVA(1.0, 0.0, 0.15, 1 / maxVelocity, 0); // tweak
        right.configureEncoder(0 , numPulsesPerRevolution , wheelDiameter);
        right.configurePIDVA(1.0, 0.0, 0.15, 1 / maxVelocity, 0); //tweak

    }

    public void pidLoop(){
        double desiredLeft = left.calculate(encoder.get());
        double desiredRight = right.calculate(encoder.get());
        double gyroHeading = robot.gyro.getAngleZ(); //this could be wrong
        double desiredHeading = Pathfinder.r2d(left.getHeading());
        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        double turn = 0.8 * (-1.0/80.0) * angleDifference;

        robot.driveTrain.updateSpeedMotionProfiler(desiredLeft + turn, desiredRight-turn);
    }


    public File initializeTrajectory() {
        switch (SmartDashboard.getString("DB/String 9", "Right").charAt(0)) {
            case 'L':
                startingPosition = StartingPosition.LEFT;
                if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' || SmartDashboard.getString("DB/String 8", "Right").charAt(0) == 'L') {
                    switchPosition = switchPosition.LEFT;
                    left = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/left_left_left_detailed.csv")));
                    right = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/left_left_right_detailed.csv")));
                    return new File("trajectories/left_left_source_detailed.csv");
                } else if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R'|| SmartDashboard.getString("DB/String 8", "Right").charAt(0) == 'R') {
                    switchPosition = SwitchPosition.RIGHT;
                    left = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/left_right_left_detailed.csv")));
                    right = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/left_right_right_detailed.csv")));
                    return new File("trajectories/left_right_source_detailed.csv");

                }
                break;
            case 'R':
                startingPosition = StartingPosition.RIGHT;
                if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' || SmartDashboard.getString("DB/String 8", "Right").charAt(0) == 'L') {
                    switchPosition = switchPosition.LEFT;
                    left = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/right_left_left_detailed.csv")));
                    right = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/right_left_right_detailed.csv")));
                    return new File("trajectories/right_left_source_detailed.csv");

                } //else if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R' || SmartDashboard.getString("DB/String 8", "Right").charAt(0) == 'R') {
                    //switchPosition = SwitchPosition.RIGHT;
                    //left = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/right_right_left_detailed.csv")));
                    //right = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/right_right_right_detailed.csv")));
                    //return new File("trajectories/right_right_source_detailed.csv");

                //}
                break;
            default:
                startingPosition = StartingPosition.RIGHT;
                switchPosition = SwitchPosition.RIGHT;
                left = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/right_right_left_detailed.csv")));
                right = new EncoderFollower(Pathfinder.readFromCSV(new File("trajectories/right_right_right_detailed.csv")));
                return new File("trajectories/right_right_source_detailed.csv");
        }
        return new File("trajectories/right_right_source_detailed.csv");
    }
}

