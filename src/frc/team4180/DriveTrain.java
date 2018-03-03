package frc.team4180;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4180.LambdaJoystick.ThrottlePosition;

public class DriveTrain {

    private final SpeedController leftMotor1, leftMotor2, rightMotor1, rightMotor2;
    private Encoder encoder;
    DigitalInput a, b;

    public DriveTrain(final int leftPort1, final int leftPort2, final int rightPort1, final int rightPort2) {
            leftMotor1 = new VictorSP(leftPort1);
            leftMotor2 = new VictorSP(leftPort2);
            rightMotor1 = new VictorSP(rightPort1);
            rightMotor2 = new VictorSP(rightPort2);

            encoder = new Encoder(8, 9);
//            encoder.setDistancePerPulse(10);
//        a = new DigitalInput(8);
//        b = new DigitalInput(9);
        }
    int cound =0;
    public void updateSpeed(final ThrottlePosition throttlePosition) {
        final double left = throttlePosition.y - throttlePosition.x;
        final double right = -throttlePosition.y - throttlePosition.x;
        leftMotor1.set(left);
        leftMotor2.set(left);
        rightMotor1.set(right);
        rightMotor2.set(right);

        SmartDashboard.putString("DB/String 5","ate "+encoder.getRate());
        SmartDashboard.putString("DB/String 6","Dis "+encoder.getDistance());
        SmartDashboard.putString("DB/String 7","Raw "+encoder.getRaw());
        SmartDashboard.putString("DB/String 8","get "+encoder.get());

//        SmartDashboard.putString("DB/String 5","A "+a.get());
//        SmartDashboard.putString("DB/String 6","B "+b.get());
        SmartDashboard.putString("DB/String 9",""+cound);
        cound= (cound+1)%1000;
    }

    public double getDistance() {
        return 0;
    }

    public void reset() {

    }
}