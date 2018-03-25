package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4180.LambdaJoystick.ThrottlePosition;

public class DriveTrain {

    private final SpeedController leftMotor1, leftMotor2, rightMotor1, rightMotor2;
    private final Encoder enco;
    private ADIS16448_IMU gyro;
    boolean rushing = false;

    public DriveTrain(final int leftPort1, final int leftPort2, final int rightPort1, final int rightPort2, ADIS16448_IMU gyro) {
            leftMotor1 = new VictorSP(leftPort1);
            leftMotor2 = new VictorSP(leftPort2);
            rightMotor1 = new VictorSP(rightPort1);
            rightMotor2 = new VictorSP(rightPort2);

            enco = new Encoder(8,9);
            enco.setDistancePerPulse(2.0943/4);

            this.gyro = gyro;
    }

    public void lameDrive(final ThrottlePosition throttlePosition) {
        if(rushing){
            DriveStraight(1);
            return;
        }
        final double right = (-throttlePosition.x - throttlePosition.y)*-1;
        final double left = (throttlePosition.y - throttlePosition.x)*-1;
        leftMotor1.set(left);
        leftMotor2.set(left);
        rightMotor1.set(right);
        rightMotor2.set(right);

    }

    public void ballerDrive(final ThrottlePosition throttlePosition){
        double desAng = ((throttlePosition.x < 0 ? 180 : 360)+(Math.atan(-throttlePosition.y/throttlePosition.x)*180/Math.PI)-90)%360;
        double currentAngel = gyro.getAngleZ() % 360;
        currentAngel = currentAngel < 0 ? (360 - currentAngel) : currentAngel;

        double turnangle = desAng - currentAngel;
        if(turnangle > 180) turnangle = turnangle - 360;
        if(turnangle < -180) turnangle = turnangle + 360;

        turnangle = turnangle/180;

        double speed = Math.sqrt(throttlePosition.x*throttlePosition.x + throttlePosition.y*throttlePosition.y);

        double right = -speed - turnangle;
        double left = speed - turnangle;
        leftMotor1.set(left);
        leftMotor2.set(left);
        rightMotor1.set(right);
        rightMotor2.set(right);

        SmartDashboard.putString("DB/String 0", right+"");
        SmartDashboard.putString("DB/String 1", left+"");

        SmartDashboard.putString("DB/String 5", "ang"+turnangle*180);
        SmartDashboard.putString("DB/String 6", "s"+speed);
    }

    public double getDistance() {
        return enco.getRaw()*-0.0002661;
    }

    public void reset() {
        enco.reset();
    }

    public void DriveStraight(double speed){
        double degrees = gyro.getAngleZ()/100;
        double right = -speed - degrees;
        double left = speed- degrees;
        leftMotor1.set(left);
        leftMotor2.set(left);
        rightMotor1.set(right);
        rightMotor2.set(right);
    }

    public void startRush(){
        reset();
        rushing = true;
    }

    public void endRush(){
        rushing = false;
    }
}