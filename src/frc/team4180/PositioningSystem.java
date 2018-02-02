package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class PositioningSystem {
    ADIS16448_IMU bigBoard;
    BuiltInAccelerometer roboRio;
    private double velocityX = 0, velocityY = 0;
    private double posX = 0, posY = 0;
    double accelThresholdX, accelThresholdY;

    public PositioningSystem(){
        bigBoard = new ADIS16448_IMU();
        roboRio = new BuiltInAccelerometer();
        bigBoard.calibrate();
        initThresholds();
    }

    private void initThresholds() {
        final double dt = 1000; //milliseconds
        double t0 = System.currentTimeMillis();
        double sumX = 0, sumY = 0;
        int iterations = 0;
        while(System.currentTimeMillis() - t0 < dt) {
            sumX += bigBoard.getAccelX();
            sumY += bigBoard.getAccelY();
            iterations++;
        }
        accelThresholdX = sumX / iterations;
        accelThresholdY = sumY / iterations;
    }

    public void test() {
        System.out.println(bigBoard.getAccelX());
        System.out.println(bigBoard.getAccelY());
    }

    public double getAccelX() {
        final double accelX = bigBoard.getAccelX();
        return (accelX - accelThresholdX) / 9.8;
    }

    public double getAccelY() {
        final double accelY = bigBoard.getAccelY();
        return (accelY - accelThresholdY) / 9.8;
    }

    public void riemann() {
        final double dt = 0.02;
        final double currAccelerationX = getAccelX();
        velocityX += currAccelerationX * dt;
        posX += velocityX * dt;
        final double currAccelerationY = getAccelY();
        velocityY += currAccelerationY * dt;
        posY += velocityY * dt;
    }

    public void logPosition() {
        System.out.printf("Position: (%.4f, %.4f)\n", posX, posY);
    }
}