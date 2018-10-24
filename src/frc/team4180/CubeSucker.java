package frc.team4180;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class  CubeSucker {

    private final SpeedController flyWheelLeft, flyWheelRight;
    private final double speed = 0.5;

    CubeSucker(final int leftPort, final int rightPort) {
        flyWheelLeft = new Spark(leftPort);
        flyWheelRight = new Spark(rightPort);
    }
<<<<<<< Updated upstream
    // reduced intake motor speed for girls gen-- this is temporary
    public void suck() { spinWheels(0.5*speed); }

    public void blow() {
        spinWheels(-0.5* speed);
=======

    //reduce intake motor speed because of broken motor for girls gen
    public void suck() {
        spinWheels(0.5*speed);
    }

    public void blow() {
        spinWheels(-speed * 0.5);
>>>>>>> Stashed changes
    }

    public void neutral() {
        spinWheels(0);
    }

    private void spinWheels(final double speed) {
        flyWheelLeft.set(speed);
        flyWheelRight.set(-speed);
    }


    public void updateSpeed(final LambdaJoystick.ThrottlePosition throttlePosition){
        final double left = throttlePosition.y - throttlePosition.x;
        final double right = -throttlePosition.y -  throttlePosition.x;

        flyWheelLeft.set(left);
        flyWheelRight.set(right);
    }
}
