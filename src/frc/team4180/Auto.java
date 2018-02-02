package frc.team4180;

public class Auto {
    public void init(DriveTrain driveTrain) {
        // Example code
        final AutonomousMode autonomousMode = AutonomousMode.getAutonomousMode();
        System.out.print("Starting at the ");
        switch(autonomousMode.startingPosition) {
            case LEFT:
                System.out.print("left");
                break;
            case CENTER:
                System.out.print("center");
                break;
            case RIGHT:
                System.out.print("right");
                break;
        }
        System.out.print(", with the switch at the ");
        switch(autonomousMode.switchPosition) {
            case LEFT:
                System.out.print("left");
                break;
            case RIGHT:
                System.out.print("right");
                break;
        }
        System.out.println(".");
    }
}
