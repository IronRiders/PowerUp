package frc.team4180;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import frc.team4180.LambdaJoystick.ThrottlePosition;

public class DriveTrain {

    private final SpeedController leftMotor, rightMotor;
    private final Encoder encoder;
    private final static int wheelDiameter = 8;
    private final static int pulsesPerRevolution = 360;

    public DriveTrain(final int leftPort, final int rightPort) {
        leftMotor = new VictorSP(leftPort);
        rightMotor = new VictorSP(rightPort);
        encoder = new Encoder(1, 0);
        // encoder.reset();
        encoder.setDistancePerPulse((Math.PI * wheelDiameter) / pulsesPerRevolution);
    }

    public void updateSpeed(final ThrottlePosition throttlePosition) {
        final double left = throttlePosition.y - throttlePosition.x;
        final double right = -throttlePosition.y - throttlePosition.x;
        leftMotor.set(left);
        rightMotor.set(right);
    }

    public double getDistance() {
        // final int encoderTest = encoder.get();
        // System.out.println(encoderTest);
        final double distance = encoder.getDistance();
        // System.out.println(distance);
        return distance;
    }

    public void reset() {

    }
}