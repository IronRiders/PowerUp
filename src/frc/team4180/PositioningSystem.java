package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class PositioningSystem {
    ADIS16448_IMU BigBoard;
    ADXRS450_Gyro LittleBoard;
    BuiltInAccelerometer RoboRio;

    PositioningSystem(){
        BigBoard = new ADIS16448_IMU();
        LittleBoard = new ADXRS450_Gyro();
        RoboRio = new BuiltInAccelerometer();
    }

}
