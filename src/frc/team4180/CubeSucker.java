package frc.team4180;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class CubeSucker {

    private final SpeedController flyWheelLeft, flyWheelRight;
    private final double speed = 0.5;

    public CubeSucker(final int leftPort, final int rightPort) {
        flyWheelLeft = new VictorSP(leftPort);
        flyWheelRight = new VictorSP(rightPort);
    }

    public void suck() {
        spinWheels(speed);
    }

    public void blow() {
        spinWheels(-speed);
    }
    public void neutral() {
        spinWheels(0);
    }

    private void spinWheels(final double speed) {
        flyWheelLeft.set(speed);
        flyWheelRight.set(-speed);
    }
}
