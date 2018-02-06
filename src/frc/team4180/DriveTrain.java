package frc.team4180;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {


    private SpeedController leftMotor, rightMotor;

    public DriveTrain(int leftPort, int rightPort) {
       // leftMotor = new VictorSP(leftPort);
        //rightMotor = new VictorSP(rightPort);
    }

    public void updateSpeed(LambdaJoystick.ThrottlePosition throttlePosition) {
        double left = -throttlePosition.y - throttlePosition.x;
        double right = throttlePosition.y - throttlePosition.x;
        leftMotor.set(right);
        rightMotor.set(left);
    }
}