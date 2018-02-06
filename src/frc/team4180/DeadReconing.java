package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;


public class DeadReconing {
    private DriveTrain driveTrain;
    private ADIS16448_IMU gyro;
    private double degreeOffset = 0; //Gyro

    private final double speed = 0.25;

    public DeadReconing(DriveTrain driveTrain, ADIS16448_IMU gyro) {
        this.driveTrain = driveTrain;
        this.gyro = gyro;
    }

    public void sameSide(boolean right) {
        double degrees = right? -90:90;
        drive(speed, 10, () -> turn(speed, degrees, () -> drive(speed, 2, this::releaseBlock)));
    }

    public void otherSide(boolean right) {
        double degrees = right? -90 : 90;
        drive(speed, 15, () -> turn(speed, degrees, () -> drive(speed, 20, () -> turn(speed, degrees, () -> drive(speed, degrees, this::releaseBlock)))));
    }

    public void drive(double speed, double distance, Runnable nextStep) {
        if (driveTrain.getDistance() > distance) {
            nextStep.run();
        } else {
            LambdaJoystick.ThrottlePosition throttlePosition = new LambdaJoystick.ThrottlePosition(0, speed);
            driveTrain.updateSpeed(throttlePosition);
            degreeOffset = gyro.getYaw();
        }
    }

    public void turn(double speed, double degrees, Runnable nextStep) {
        if (gyro.getYaw() - degreeOffset > degrees) {
            nextStep.run();
        } else {
            LambdaJoystick.ThrottlePosition throttlePosition = new LambdaJoystick.ThrottlePosition(speed, 0);
            driveTrain.updateSpeed(throttlePosition);
            driveTrain.resetDistance();
        }
    }

    public void releaseBlock() {
        //Place holder
    }
}
