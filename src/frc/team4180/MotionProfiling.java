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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


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
        try{
            SmartDashboard.putString("DB/String 1", "Tried to initialize trajectory");
            initializeTrajectory();
        }
        catch(Exception e){
            System.out.println(e);
        }
        //Trajectory trajectory = Pathfinder.readFromCSV(file);
        //TankModifier modifier = new TankModifier(trajectory).modify(wheelbaseWidth);
        encoder.reset();
        left.configureEncoder(0, numPulsesPerRevolution , wheelDiameter);
        left.configurePIDVA(1.0, 0.0, 0.15, 1 / maxVelocity, 0); // tweak
        right.configureEncoder(0 , numPulsesPerRevolution , wheelDiameter);
        right.configurePIDVA(1.0, 0.0, 0.15, 1 / maxVelocity, 0); //tweak

    }

    public void pidLoop(){
//        double desiredLeft = left.calculate(encoder.get());
//        double desiredRight = right.calculate(encoder.get());
//        double gyroHeading = robot.gyro.getAngleZ(); //this could be wrong
//        double desiredHeading = Pathfinder.r2d(left.getHeading());
//        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
//        double turn = 0.8 * (-1.0/80.0) * angleDifference;

        //robot.driveTrain.updateSpeedMotionProfiler(desiredLeft + turn, desiredRight-turn);
        robot.driveTrain.updateSpeedMotionProfiler(1.0,1.0);
    }


    public void initializeTrajectory() throws URISyntaxException , IOException{
        SmartDashboard.putString("DB/String 1", "Entered initialize trajectory method");
        switch (SmartDashboard.getString("DB/String 9","R").charAt(0)) {
            case 'L':
                SmartDashboard.putString("DB/String 1", "Entered Switch Statement L ");
                if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
                    SmartDashboard.putString("DB/String 1", "Got past game specific message");
                    File file = new File("left_left_left_detailed.csv");
                    SmartDashboard.putString("DB/String 1", "Got past file");
                    left = new EncoderFollower(Pathfinder.readFromCSV(file));
                    file = new File("");
                    right = new EncoderFollower(Pathfinder.readFromCSV(file));
                } else if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
                    File file = new File("left_right_left_detailed.csv");
                    left = new EncoderFollower(Pathfinder.readFromCSV(file));
                    file = new File("left_right_right_detailed.csv");
                    right = new EncoderFollower(Pathfinder.readFromCSV(file));

                }
                break;
            case 'R':
                if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' || SmartDashboard.getString("DB/String 8", "Right").charAt(0) == 'L') {
                    left = new EncoderFollower(Pathfinder.readFromCSV(new File("right_left_left_detailed.csv")));
                    right = new EncoderFollower(Pathfinder.readFromCSV(new File("right_left_right_detailed.csv")));
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
                left = new EncoderFollower(Pathfinder.readFromCSV(new File("right_right_left_detailed.csv")));
                right = new EncoderFollower(Pathfinder.readFromCSV(new File("right_right_right_detailed.csv")));
        }
        //return new File("trajectories/right_right_source_detailed.csv");
    }
}

