package frc.team4180;
//test

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.team4180.Ports.*;

public class Robot extends IterativeRobot {


    public final DriveTrain driveTrain = new DriveTrain(LEFT_DRIVING_1, LEFT_DRIVING_2, RIGHT_DRIVING_1, RIGHT_DRIVING_2);
    public final CubeSucker cubeSucker = new CubeSucker(LEFT_FLY_WHEEL, RIGHT_FLY_WHEEL);
    public final CubePusher cubePusher = new CubePusher(SOLENIOD_1, SOLENIOD_2);
    public final ADIS16448_IMU gyro = new ADIS16448_IMU();
    public final BuiltInAccelerometer roboRio = new BuiltInAccelerometer();
    public final PositioningSystem positioningSystem = new PositioningSystem(gyro, roboRio);
    double yaw;
    AutonomousMode autoMode;
//This a test
    private final LambdaJoystick joystick1 = new LambdaJoystick(0, driveTrain::updateSpeed);
    private final LambdaJoystick joystick2 = new LambdaJoystick(1, cubeSucker::updateSpeed);

    public Autonomous autoRoutine;
    public Timer AutoTime;
    private boolean firstPush = true;

    @Override
    public void robotInit()
    {
        CameraServer.getInstance().startAutomaticCapture();

        joystick2.addButton(2, cubeSucker::blow, cubeSucker::neutral);
        joystick2.addButton(1, cubeSucker::suck, cubeSucker::neutral);
        joystick1.addButton(1, cubePusher::extend, cubePusher::reset);

        AutoTime = new Timer();
    }

    @Override
    public void autonomousInit() {
     //   yaw = gyro.getYaw();

       // SmartDashboard.putNumber("time", 150);
        //AutoTime.start();

        driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0,-0.3));//-0.2 good
      //  autoMode = AutonomousMode.getAutonomousMode();
        //autoMode.initialize();
////        autoRoutine = new Autonomous(this, () -> autoRoutine.drive(10), () -> {
////            System.out.println("Debug");
////            return true;
////        });
//        boolean isRightSide = autoMode.startingPosition.
//        autoRoutine = new Autonomous(autoMode, this, )
    }

    @Override
    public void autonomousPeriodic() {
        //boolean turn = autoMode.startingPosition.toString().equals(autoMode.switchPosition.toString());

//        if (turn) {
//            boolean right = true ;//autoMode.startingPosition.equals("LEFT");
//            double degree = right ? 90 : -90;
//            if (Math.abs(gyro.getYaw() - yaw - degree) < 7) {
//                driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0.35, 0));
        if (AutoTime.get()>10) {
            driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0,-0.4 ));
        }

//            }
        /*
        boolean rightPosition = SmartDashboard.getBoolean("right", false);
        boolean switchRight = false;
        switch(DriverStation.getInstance().getGameSpecificMessage().charAt(0)) {
            case 'L':
                switchRight = false;
                break;
            case 'R':
                switchRight = true;
                break;
        }

        if(rightPosition == switchRight && firstPush){
            cubePusher.extend();
            firstPush = false;
        }
        else{
            driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0,0));

        } */
        //autoRoutine.run();

    }


    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("time", 150);
        positioningSystem.increment();
        joystick1.listen();
        joystick2.listen();
    }
}
