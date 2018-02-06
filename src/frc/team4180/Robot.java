package frc.team4180;

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
    private DriveTrain driveTrain;
    private PositioningSystem positioningSystem;
    @Override
    public void robotInit()
    {

       driveTrain = new DriveTrain(LEFT_DRIVING, RIGHT_DRIVING);
       positioningSystem = new PositioningSystem();

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
