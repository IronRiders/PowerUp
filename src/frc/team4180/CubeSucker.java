package frc.team4180;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class CubeSucker {

    private SpeedController flyWheelLeft, flyWheelRight;
    private final double speed = 0.5;

    public CubeSucker(int leftPort, int rightPort){
        flyWheelLeft = new VictorSP(leftPort);
        flyWheelRight = new VictorSP(rightPort);
    }

    public void Suck()
    {
        spinWheels(speed);
    }
    public void Blow()
    {
        spinWheels(-speed);
    }
    public void Neutral()
    {
        spinWheels(0);
    }

    private void spinWheels(double speed){
        flyWheelLeft.set(speed);
        flyWheelRight.set(-speed);
    }
}
