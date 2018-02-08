package frc.team4180;

import edu.wpi.first.wpilibj.Solenoid;

public class CubePusher {

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