package frc.team4180;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import frc.team4180.LambdaJoystick.ThrottlePosition;
import jaci.pathfinder.modifiers.TankModifier;
import edu.wpi.first.wpilibj.*;


public class MotionProfiling {
    Robot robot;
    Waypoint[] points;
    EncoderFollower left;
    EncoderFollower right;
    final double maxVelocity = 1.7; //change
    final double maxAcceleration = 2.0; //change
    final double maxJerk = 60.0; //change
    final double wheelbaseWidth = 0.5; //change
    final double wheelDiameter = 0.2032; //8 inches to meters
    final int numPulsesPerRevolution = 360; //????verify
    AutonomousMode autoMode;
    Encoder encoder;

    public MotionProfiling(Robot robot, boolean startingRight, boolean switchRight){

        this.robot = robot;
        this.points = setPoints(startingRight, switchRight);
        encoder = robot.driveTrain.getEncoder();
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, maxVelocity, maxAcceleration, maxJerk);
        Trajectory trajectory = Pathfinder.generate(this.points, config);
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

    public Waypoint[] setPoints(boolean startingRight , boolean switchRight){
        Waypoint[] points = new Waypoint[10];
        if(startingRight == switchRight){
            points[0] = new Waypoint(0,6.5,0);
            // more points here ---- figure this out on another day

        }
        else{
            // more points here
        }
        return points;
    }
}

