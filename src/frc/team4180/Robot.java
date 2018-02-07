package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot 
{
    private final int LEFT_DRIVING = 0; //Placeholder
    private final int RIGHT_DRIVING = 1; //Placeholder
    private final int LEFT_FLY_WHEEL = 2;
    private final int RIGHT_FLY_WHEEL = 3;

    private final int PISTON = 0;

    private LambdaJoystick joystick1;
    private LambdaJoystick joystick2;

    public DriveTrain driveTrain;
    public CubeSucker cubeSucker;
    public CubePusher cubePusher;
    public ADIS16448_IMU gyro;
    private PositioningSystem positioningSystem;

    Autonomous autoRoutine;

    @Override
    public void robotInit()
    {
        driveTrain = new DriveTrain(LEFT_DRIVING, RIGHT_DRIVING);
        cubeSucker = new CubeSucker(LEFT_FLY_WHEEL, RIGHT_FLY_WHEEL);
        cubePusher = new CubePusher(PISTON);
        positioningSystem = new PositioningSystem();

        joystick1 = new LambdaJoystick(0);
        joystick2 = new LambdaJoystick(1);

        joystick1.addButton(3, cubeSucker::Blow, cubeSucker::Neutral);
        joystick1.addButton(1, cubeSucker::Suck, cubeSucker::Neutral);
        joystick2.addButton(2, cubePusher::extend, cubePusher::reset);

        CameraServer.getInstance().startAutomaticCapture();
    }

    @Override
    public void autonomousInit() 
    {
        autoRoutine = new Autonomous(this);
    }

    @Override
    public void autonomousPeriodic()
    {
        try {
            autoRoutine.run();
        } catch (Exception e) {
            SmartDashboard.putString("DB/String 1", e.getMessage());
        }
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
