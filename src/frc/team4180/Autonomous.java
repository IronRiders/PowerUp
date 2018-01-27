package frc.team4180;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
    private State state;
    private Timer autoTime;
    private DriveTrain drive;
    private Ramp ramp;

    private double previousTime;
    private int startLocation;

    private static final double WAITING_TIME = 5;
    private static final double DRIVING_TIME_1 = 2;
    private static final double DRIVING_TIME_2 = 0.9;
    private static final double TURNING_TIME = 0.75;

    public Autonomous(DriveTrain drive, Ramp ramp) {
        previousTime = 0;
        state = State.Beginning;
        autoTime = new Timer();
        this.drive = drive;
        this.drive.setBackwards(false);
        this.drive.setGear(true);
        this.ramp = ramp;
        this.ramp.set(DoubleSolenoid.Value.kReverse);
        startLocation = getStartLocation();
    }

    private int getStartLocation(){
        String locationStr = SmartDashboard.getString("DB/String 5", "0");
        int startLocation;
        try {
            startLocation = Integer.parseInt(locationStr);
        } catch (Exception e) {
            startLocation = 0;
        }
        return startLocation;
    }

    public void Periodic() {
        switch (state) {

            case Beginning:
                updateSmartDashboard("Beginning");
                autoTime.start();
                changeState(startLocation == 0 ? State.Done : State.Waiting, 0);
                break;

            case Waiting:
                updateSmartDashboard("Waiting");
                changeState(State.Driving, WAITING_TIME);
                break;

            case Driving:
                updateSmartDashboard("Driving");
                drive.updateSpeed(new LambdaJoystick.ThrottlePosition(0, -0.4));
                if (startLocation == 2) {
                    changeState(State.Done, DRIVING_TIME_1);
                } else {
                    changeState(State.Turning, DRIVING_TIME_2);
                }
                break;

            case Turning:
                updateSmartDashboard(String.format("Turning {%s, %s}", (startLocation - 2) * 0.4, -0.4));
                drive.updateSpeed(new LambdaJoystick.ThrottlePosition((startLocation - 2) * 0.4, -0.75));
                changeState(State.Done, TURNING_TIME);
                break;

            case Done:
                updateSmartDashboard("Done");
                drive.updateSpeed(new LambdaJoystick.ThrottlePosition(0, 0));
                break;
        }
    }

    private void updateSmartDashboard(String message){
        SmartDashboard.putString("DB/String 6", message);
    }

    private void changeState(State s, double time) {
        SmartDashboard.putString("DB/String 7", String.format("%d >= %d", Math.round(autoTime.get()), Math.round(time + previousTime)));
        if (autoTime.get() >= time + previousTime) {
            state = s;
            previousTime = autoTime.get();
        }
    }

    public enum State {
        Beginning, Waiting, Driving, Turning, Done
    }
}
