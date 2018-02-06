package frc.team4180;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {
    private SpeedController leftMotor, rightMotor;
    private Encoder encoder;
    final int wheelDiameter = 8;
    final int pulsesPerRevolution = 360;


    public DriveTrain(int leftPort, int rightPort) {
        leftMotor = new VictorSP(leftPort);
        rightMotor = new VictorSP(rightPort);
        encoder = new Encoder(1,0);
        //encoder.reset();
        encoder.setDistancePerPulse(Math.PI * wheelDiameter);
    }

    public void updateSpeed(LambdaJoystick.ThrottlePosition throttlePosition) {
        double left = -throttlePosition.y - throttlePosition.x;
        double right = throttlePosition.y - throttlePosition.x;
        leftMotor.set(right);
        rightMotor.set(left);
    }

    public double getDistance(){
        int encoderTest = encoder.get();
        System.out.print(encoderTest);
        // 8 inches
        double distance = encoder.getDistance();
        //System.out.println(distance/pulsesPerRevolution);
        return distance;
    }
}