package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import static frc.team4180.Ports.*;

public class Robot extends IterativeRobot {

    private LambdaJoystick joystick1;
    private LambdaJoystick joystick2;

    public ADIS16448_IMU gyro;
    public BuiltInAccelerometer accelerometer;

    public DriveTrain driveTrain;
    public CubeSucker cubeSucker;
    public CubePusher cubePusher;
    private PositioningSystem positioningSystem;

    public Autonomous autoRoutine;

    @Override
    public void robotInit()
    {
        CameraServer.getInstance().startAutomaticCapture();
        gyro = new ADIS16448_IMU();
        accelerometer = new BuiltInAccelerometer();

        driveTrain = new DriveTrain(LEFT_DRIVING, RIGHT_DRIVING);
        cubeSucker = new CubeSucker(LEFT_FLY_WHEEL, RIGHT_FLY_WHEEL);
        cubePusher = new CubePusher(PISTON);
        positioningSystem = new PositioningSystem(gyro, accelerometer);

        joystick1 = new LambdaJoystick(0);
        joystick2 = new LambdaJoystick(1);
        joystick1.addButton(3, cubeSucker::blow, cubeSucker::neutral);
        joystick1.addButton(1, cubeSucker::suck, cubeSucker::neutral);
        joystick2.addButton(2, cubePusher::extend, cubePusher::reset);
    }

    @Override
    public void autonomousInit() 
    {
        autoRoutine = new Autonomous(this);
    }

    @Override
    public void autonomousPeriodic()
    {
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

    @Override
    public void testInit() {}

}
