package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import static frc.team4180.Ports.*;

public class Robot extends IterativeRobot {

    private final LambdaJoystick joystick1 = new LambdaJoystick(0);
    private final LambdaJoystick joystick2 = new LambdaJoystick(1);

    public final DriveTrain driveTrain = new DriveTrain(LEFT_DRIVING, RIGHT_DRIVING);
    public final CubeSucker cubeSucker = new CubeSucker(LEFT_FLY_WHEEL, RIGHT_FLY_WHEEL);
    public final CubePusher cubePusher = new CubePusher(PISTON);
    public final ADIS16448_IMU gyro = new ADIS16448_IMU();
    public final BuiltInAccelerometer roboRio = new BuiltInAccelerometer();
    public final PositioningSystem positioningSystem = new PositioningSystem(gyro, roboRio);

    public Autonomous autoRoutine;

    @Override
    public void robotInit() {
        joystick1.addButton(3, cubeSucker::blow, cubeSucker::neutral);
        joystick1.addButton(1, cubeSucker::suck, cubeSucker::neutral);
        joystick2.addButton(2, cubePusher::extend, cubePusher::reset);
    }

    @Override
    public void autonomousInit() {
        // This is an example/not good routine. 10 is not an actual value
        autoRoutine = new Autonomous(this, () -> autoRoutine.drive(10), () -> {
            System.out.println("Debug");
            return true;
        });
    }

    @Override
    public void autonomousPeriodic() {
        autoRoutine.run();
    }

    @Override
    public void teleopPeriodic() {
        positioningSystem.increment();
    }

    @Override
    public void testPeriodic() {
        driveTrain.getDistance();
    }
}
