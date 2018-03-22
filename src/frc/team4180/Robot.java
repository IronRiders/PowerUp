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
    public void robotInit() {
        CameraServer.getInstance().startAutomaticCapture();
        updateSmartDB();

        joystick2.addButton(2, cubeSucker::blow, cubeSucker::neutral);
        joystick2.addButton(1, cubeSucker::suck, cubeSucker::neutral);
        joystick1.addButton(1, cubePusher::extend, cubePusher::reset);

        AutoTime = new Timer();
    }

    @Override
    public void autonomousInit() {
        boolean switchRight = (DriverStation.getInstance().getGameSpecificMessage().charAt(0)) == 'R';
        boolean isRight = SmartDashboard.getString("DB/String 9","Left").charAt(0) == 'R';

        char same = SmartDashboard.getString("DB/String 8","Straight").charAt(0);
        char opp = SmartDashboard.getString("DB/String 7","Baseline").charAt(0);

        autoRoutine = new Autonomous(this, isRight,switchRight,same, opp);
    }

    @Override
    public void autonomousPeriodic() {
        autoRoutine.run();
    }


    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("time", 150);
        positioningSystem.increment();
        joystick1.listen();
        joystick2.listen();
    }

    private void updateSmartDB(){
        SmartDashboard.putString("DB/String 2", "OPPOSITE MODE (B/S/L)");
        SmartDashboard.putString("DB/String 3", "SAME MODE (F/T) --->");
        SmartDashboard.putString("DB/String 4", "START POSITION (R/L) --->");
    }
}
