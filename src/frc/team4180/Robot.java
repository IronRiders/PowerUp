package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

import static frc.team4180.Ports.*;

public class Robot extends IterativeRobot {


    public final DriveTrain driveTrain = new DriveTrain(LEFT_DRIVING_1, LEFT_DRIVING_2, RIGHT_DRIVING_1, RIGHT_DRIVING_2);
    public final CubeSucker cubeSucker = new CubeSucker(LEFT_FLY_WHEEL, RIGHT_FLY_WHEEL);
    public final CubePusher cubePusher = new CubePusher(SOLENIOD_1, SOLENIOD_2);
    public final ADIS16448_IMU gyro = new ADIS16448_IMU();
    public final BuiltInAccelerometer roboRio = new BuiltInAccelerometer();
    public final PositioningSystem positioningSystem = new PositioningSystem(gyro, roboRio);
//This a test
    private final LambdaJoystick joystick1 = new LambdaJoystick(0, driveTrain::updateSpeed);
    private final LambdaJoystick joystick2 = new LambdaJoystick(1, cubeSucker::updateSpeed);

    public Autonomous autoRoutine;
    public Timer AutoTime;

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
        AutoTime.start();
        driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0,0.2));
//        autoRoutine = new Autonomous(this, () -> autoRoutine.drive(10), () -> {
//            System.out.println("Debug");
//            return true;
//        });
    }

    @Override
    public void autonomousPeriodic() {
        if(AutoTime.get()>10){
            driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0,0));
        }
//        autoRoutine.run();
    }

    @Override
    public void teleopPeriodic() {
        positioningSystem.increment();
        joystick1.listen();
        joystick2.listen();
    }
}
