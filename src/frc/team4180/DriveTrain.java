package frc.team4180;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SpeedController;

public class DriveTrain {
    private SpeedController leftMotor, rightMotor;
    private boolean backwards = false;
    private DoubleSolenoid gearShifting;
    private boolean state = false;

    public DriveTrain(int leftPort, int rightPort, int gearShiftPort1, int gearShiftPort2) {
        leftMotor = new VictorSP(leftPort);
        rightMotor = new VictorSP(rightPort);
        gearShifting = new DoubleSolenoid(gearShiftPort1, gearShiftPort2);
    }

    public void updateSpeed(LambdaJoystick.ThrottlePosition throttlePosition) {
        double left = -throttlePosition.y - throttlePosition.x;
        double right = throttlePosition.y - throttlePosition.x;
        leftMotor.set(backwards ? left : right);
        rightMotor.set(backwards ? right : left);
    }

    public void toggleGearShifting() {
        setGear(!state);
    }

    public void setGear(boolean b) {
        state = b;
        SmartDashboard.putString("DB/String 8", state ? "Fast" : "Slow");
        gearShifting.set(state ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }

    public void toggleBackwards() {
        backwards = !backwards;
    }

    public void setBackwards(boolean backwards){
        this.backwards = backwards;
    }
}