package frc.team4180;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4180.LambdaJoystick.ThrottlePosition;

public class DriveTrain {

    private final SpeedController leftMotor1, leftMotor2, rightMotor1, rightMotor2;
    private final Encoder enco;

    public DriveTrain(final int leftPort1, final int leftPort2, final int rightPort1, final int rightPort2) {
            leftMotor1 = new VictorSP(leftPort1);
            leftMotor2 = new VictorSP(leftPort2);
            rightMotor1 = new VictorSP(rightPort1);
            rightMotor2 = new VictorSP(rightPort2);

            enco = new Encoder(8,9);
            enco.setDistancePerPulse(2.0943/4);
    }

    public void updateSpeed(final ThrottlePosition throttlePosition) {
        final double right = (-throttlePosition.x - throttlePosition.y)*-1;
        final double left = (throttlePosition.y - throttlePosition.x)*-1;
        leftMotor1.set(left);
        leftMotor2.set(left);
        rightMotor1.set(right);
        rightMotor2.set(right);

        SmartDashboard.putString("DB/String 1","V 4");
        SmartDashboard.putString("DB/String 2","dist "+getDistance());

    }

    public double getDistance() {
        return enco.getRaw()*-0.0002661;
    }

    public void reset() {
        enco.reset();
    }
}