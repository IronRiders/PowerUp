package frc.team4180;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot 
{
    private final DriveTrain driveTrain = new DriveTrain(-1, -1);
    private final Auto auto = new Auto();
    private final PositioningSystem positioningSystem = new PositioningSystem();

    @Override
    public void robotInit() 
    {
        AutonomousMode.initialize();
    }

    @Override
    public void autonomousInit() 
    {
        auto.init(driveTrain);
    }

    @Override
    public void autonomousPeriodic()
    {
    }

    @Override
    public void teleopPeriodic() 
    {
    }

    @Override
    public void testPeriodic() 
    {
        positioningSystem.riemann();
        positioningSystem.logPosition();
    }

    @Override
    public void testInit()
    {
    }
}
