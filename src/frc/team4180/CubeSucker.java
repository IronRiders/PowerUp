package frc.team4180;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class  CubeSucker {

    private final SpeedController flyWheelLeft, flyWheelRight;
    private final double speed = 0.2;

    public CubeSucker(final int leftPort, final int rightPort) {
        flyWheelLeft = new Spark(leftPort);
        flyWheelRight = new Spark(rightPort);
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

    public void updateSpeed(final LambdaJoystick.ThrottlePosition throttlePosition) {
        final double left = throttlePosition.y - throttlePosition.x;
        final double right = -throttlePosition.y - throttlePosition.x;
        flyWheelLeft.set(left);
        flyWheelRight.set(right);
    }
}
