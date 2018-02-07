package frc.team4180;

import edu.wpi.first.wpilibj.Solenoid;

public class cubePusher {
    Solenoid upperPiston;


    public cubePusher(int port){
        upperPiston = new Solenoid(port);
    }
    public void extend() {
        upperPiston.set(true);
    }
    public void reset() {
        upperPiston.set(false);
    }
}