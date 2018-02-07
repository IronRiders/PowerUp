package frc.team4180;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class CubeSucker {

    private SpeedController flyWheelLeft, flyWheelRight;

    public CubeSucker(int leftPort, int rightPort){
        flyWheelLeft = new VictorSP(leftPort);
        flyWheelRight = new VictorSP(rightPort);
    }


    public static enum switchState
    {
        Sucking,
        Neutral,
        Blowing
    }

    public void Suck()
    {

    }
    public void Blow()
    {

    }
    public void Neutral()
    {

    }
}
