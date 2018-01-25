package frc.team4180;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot 
{
    private final DriveTrain driveTrain = new DriveTrain(-1, -1, -1, -1);

    @Override
    public void robotInit() 
    {
        AutonomousMode.initializeNetworkTables();
    }

    @Override
    public void autonomousInit() 
    {
    }

    @Override
    public void autonomousPeriodic()
    {
        switch (AutonomousMode.getCurrentMode()) {
            case DO_NOTHING: break;
            case JUST_DRIVE_FORWARD:
                driveTrain.updateSpeed(new LambdaJoystick.ThrottlePosition(0.1, 0.1));
                break;
        }
    }

    @Override
    public void teleopPeriodic() 
    {
    }

    @Override
    public void testPeriodic() 
    {
    }
}
