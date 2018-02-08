package frc.team4180;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {


    private SpeedController leftMotor1, leftMotor2, rightMotor1, rightMotor2;
    private Encoder encoder;
    final int wheelDiameter = 8;
    final int pulsesPerRevolution = 360;


    public DriveTrain(int leftPort1, int leftPort2, int rightPort1, int rightPort2) {
        leftMotor1 = new VictorSP(leftPort1);
        leftMotor2 = new VictorSP(leftPort2);
        rightMotor1 = new VictorSP(rightPort1);
        rightMotor2 = new VictorSP(rightPort2);
        encoder = new Encoder(1,0);
        //encoder.reset();
        encoder.setDistancePerPulse((Math.PI * wheelDiameter) / pulsesPerRevolution);
    }

    public void updateSpeed(LambdaJoystick.ThrottlePosition throttlePosition) {
        double left = -throttlePosition.y - throttlePosition.x;
        double right = throttlePosition.y - throttlePosition.x;
        leftMotor1.set(right);
        leftMotor2.set(right);
        rightMotor1.set(left);
        rightMotor2.set(left);
    }

    public double getDistance(){
        int encoderTest = encoder.get();
        System.out.print(encoderTest);
        // 8 inches
        double distance = encoder.getDistance();
        //System.out.println(distance);
        return distance;
    }

    public void reset(){

    }
}