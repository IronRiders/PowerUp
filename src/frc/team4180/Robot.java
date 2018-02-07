package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import static frc.team4180.AutonomousMode.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;

public class Robot extends IterativeRobot 
{
    private final int LEFT_DRIVING = 0; //Placeholder
    private final int RIGHT_DRIVING = 1; //Placeholder
    private LambdaJoystick joystick1;
    private LambdaJoystick joystick2;
    private LambdaJoystick joystick3;
    public DriveTrain driveTrain;
    public CubeSucker cubeSucker;
    public cubePusher cubePusher;
    public ADIS16448_IMU gyro;

    private PositioningSystem positioningSystem;
    @Override
    public void robotInit()
    {
        driveTrain = new DriveTrain(LEFT_DRIVING, RIGHT_DRIVING);
        cubeSucker = new CubeSucker(LEFT_DRIVING, RIGHT_DRIVING);
        cubePusher = new cubePusher(4);
        positioningSystem = new PositioningSystem();
        joystick1 = new LambdaJoystick(0);
        joystick2 = new LambdaJoystick(1);
        joystick3 = new LambdaJoystick(2);
        joystick1.addButton(3, ()->{cubeSucker.Blow();}, ()->{cubeSucker.Neutral();});
        joystick1.addButton(1, ()->{cubeSucker.Suck();}, ()->{cubeSucker.Neutral();});
        joystick3.addButton(2, ()->{cubePusher.extend();}, ()->{cubePusher.reset();});
        CameraServer.getInstance().startAutomaticCapture();
    }

    @Override
    public void autonomousInit() 
    {
        AutonomousMode.initializeNetworkTables(DO_NOTHING);
    }

    @Override
    public void autonomousPeriodic()
    {
        switch (AutonomousMode.getCurrentMode()) {
            case DO_NOTHING: break;
            case DRIVE_FORWARD:
                driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0.1, 0.1));
                break;
        }
    }

    @Override
    public void teleopPeriodic() {

        // test for networktable
//        double x = 0.33;
//        while (x<50) {
//            Timer.delay(1);
//            x += 1;
//            try {
//                SmartDashboard.putNumber("/SmartDashboard/X", x);
//
//            } catch (NullPointerException e) {
//                System.out.print(e);
//            }
//        }



        positioningSystem.increment();
    }

    @Override
    public void testPeriodic() {
        driveTrain.getDistance();
    }

    @Override
    public void testInit() {}

}
