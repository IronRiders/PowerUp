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
    Robot robot;
    EncoderFollower left;
    EncoderFollower right;
    final double maxVelocity = 1.7; //change
    //final double maxAcceleration = 2.0; //change
    //final double maxJerk = 60.0; //change
    final double wheelbaseWidth = 0.5; //change
    final double wheelDiameter = 0.2032; //8 inches to meters
    final int numPulsesPerRevolution = 360; //????verify
    enum StartingPosition { LEFT, CENTER, RIGHT };
    enum SwitchPosition { LEFT, RIGHT };
    public  StartingPosition startingPosition;
    public  SwitchPosition switchPosition;
    Encoder encoder;

    public MotionProfiling(Robot robot){
        this.robot = robot;
        encoder = robot.driveTrain.getEncoder();
        //Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, maxVelocity, maxAcceleration, maxJerk);
        //Trajectory trajectory = Pathfinder.generate(this.points, config);
        File myFile = initializeTrajectory();
        Trajectory trajectory = Pathfinder.readFromCSV(myFile);
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
        double gyroHeading = robot.gyro.getAngleZ(); //this could be wrong, maybe we want angle x or y
        double desiredHeading = Pathfinder.r2d(left.getHeading());
        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        double turn = 0.8 * (-1.0/80.0) * angleDifference;

        robot.driveTrain.updateSpeedMotionProfiler(desiredLeft + turn, desiredRight-turn);
    }

    public File initializeTrajectory() {
        switch (SmartDashboard.getString("DB/String 9", "Left").charAt(0)) {
            case 'L':
                startingPosition = StartingPosition.LEFT;
                if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
                    switchPosition = switchPosition.LEFT;
                    return new File("trajectories/lefttoleft_left_detailed.csv");
                } else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R'){
                    switchPosition = SwitchPosition.RIGHT;
                    return new File("trajectories/lefttoright_left_detailed.csv");
                }
                break;
            case 'R':
                startingPosition = StartingPosition.RIGHT;
                if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
                    switchPosition = switchPosition.LEFT;
                    return new File("trajectories/righttoleft_left_detailed.csv");
                } else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
                    switchPosition = SwitchPosition.RIGHT;
                    return new File("trajectories/righttoright_left_detailed.csv");
                }
                break;
            case 'C':
                startingPosition = StartingPosition.CENTER;
                if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
                    switchPosition = switchPosition.LEFT;
                    return new File("trajectories/centertoleft_left_detailed.csv");
                } else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
                    switchPosition = SwitchPosition.RIGHT;
                    return new File("trajectories/centertoright_left_detailed.csv");
                }
                break;
            default:
                startingPosition = StartingPosition.RIGHT;
        }
        return new File("");
    }
}

