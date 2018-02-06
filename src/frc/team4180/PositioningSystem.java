package frc.team4180;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PositioningSystem {
    private ADIS16448_IMU bigBoard;
    private BuiltInAccelerometer roboRio;
    private Timer time;
    private double lastTime = 0;

    private Vector accleration;
    private Vector rotation;
    private Vector velocity;
    private Vector position;

    private final double ERROR_THRESHOLD = 0.05;

    public PositioningSystem(){
        accleration = new Vector();
        velocity = new Vector();
        position = new Vector();

        bigBoard = new ADIS16448_IMU();
        roboRio = new BuiltInAccelerometer();
        bigBoard.calibrate();
        time = new Timer();
        time.start();
    }

    public void increment(){
        double currentTime = time.get();
        double dTime = currentTime - lastTime;
        lastTime = currentTime;
        if(dTime>0.5) return;
        Vector accel = new Vector(bigBoard.getAccelX(), bigBoard.getAccelY(), bigBoard.getAccelZ());
        Vector avgAcceleration = Vector.getAverage(accel, accleration);
        accleration = accel;
        avgAcceleration.multiply(dTime);
        Vector oldVelocity = velocity.copy();
        if(Math.abs(avgAcceleration.x) > ERROR_THRESHOLD || Math.abs(avgAcceleration.y) > ERROR_THRESHOLD) {
            velocity.add(avgAcceleration);
        }
        Vector avgVelocity = Vector.getAverage(oldVelocity, velocity);
        avgVelocity.multiply(dTime);
        position.add(avgVelocity);
        SmartDashboard.putString("DB/String 0", Double.toString(position.x));
        SmartDashboard.putString("DB/String 1", Double.toString(position.y));
        SmartDashboard.putString("DB/String 2", Double.toString(position.z));
    }

    static class Vector{
        double x, y, z;
        Vector(double x, double y, double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        Vector(){
            this(0, 0, 0);
        }

        void add(Vector v){
            x += v.x;
            y += v.y;
            z += v.z;
        }

        void multiply(double d){
            x *= d;
            y *= d;
            z *= d;
        }

        Vector copy(){
            return new Vector(x,y,z);
        }

        static Vector getAverage(Vector v1, Vector v2){
            return new Vector((v1.x + v2.x) / 2, (v1.y + v2.y) / 2, (v1.z + v2.z) / 2);
        }
    }
}