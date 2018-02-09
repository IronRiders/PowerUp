package frc.team4180;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import frc.team4180.LambdaJoystick.ThrottlePosition;

public class DriveTrain {

    private final SpeedController leftMotor1, leftMotor2, rightMotor1, rightMotor2;
    private final Encoder encoder;
    private final static int wheelDiameter = 8;
    private final static int pulsesPerRevolution = 360;

    public DriveTrain(final int leftPort1, final int leftPort2, final int rightPort1, final int rightPort2) {
        leftMotor1 = new VictorSP(leftPort1);
        leftMotor2 = new VictorSP(leftPort2);
        rightMotor1 = new VictorSP(rightPort1);
        rightMotor2 = new VictorSP(rightPort2);
        encoder = new Encoder(1, 0);
        // encoder.reset();
        encoder.setDistancePerPulse((Math.PI * wheelDiameter) / pulsesPerRevolution);
    }

    public void updateSpeed(final ThrottlePosition throttlePosition) {
        final double left = throttlePosition.y - throttlePosition.x;
        final double right = -throttlePosition.y - throttlePosition.x;
        leftMotor1.set(left);
        leftMotor2.set(left);
        rightMotor1.set(right);
        rightMotor2.set(right);
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