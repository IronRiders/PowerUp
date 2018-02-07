package frc.team4180;

import edu.wpi.first.wpilibj.Solenoid;

public class CubePusher {

    Solenoid upperPiston;

    public CubePusher(int port){
        upperPiston = new Solenoid(port);
    }

    public void extend() {
        upperPiston.set(true);
    }

    public void reset() {
        upperPiston.set(false);
    }
}