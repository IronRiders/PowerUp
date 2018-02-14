package frc.team4180;

import edu.wpi.first.wpilibj.Solenoid;

public class CubePusher {

    //what else needs to be added to this class?
    private final Solenoid upperPiston;

    public CubePusher(final int port) {
        upperPiston = new Solenoid(port);
    }

    public void extend() {
        upperPiston.set(true);
    }

    public void reset() {
        upperPiston.set(false);
    }
}