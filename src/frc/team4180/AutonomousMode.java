package frc.team4180;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Objects of this class represents different states during autonomous time
 */
public class AutonomousMode {
    enum StartingPosition { LEFT, CENTER, RIGHT }
    enum SwitchPosition { LEFT, RIGHT }
    enum State{ FORWORD , TURN_LEFT, TURN_RIGHT , STOP  }


    private static SwitchPosition matchDataPosition;
    public final StartingPosition startingPosition;
    public final SwitchPosition switchPosition;
    public State state;

    private AutonomousMode(SwitchPosition switchPosition, StartingPosition startingPosition) {
        this.switchPosition = switchPosition;
        this.startingPosition = startingPosition;
        this.state = State.STOP;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AutonomousMode &&
                this.startingPosition.equals(((AutonomousMode) obj).startingPosition) &&
                this.switchPosition.equals(((AutonomousMode) obj).switchPosition);
    }

    /**
     * @return the current autonomous mode, fetching the starting position from the dashboard
     */
    public static AutonomousMode getAutonomousMode() {
        final String startingPositionString = SmartDashboard.getString("/SmartDashboard/autonomous/selected_starting_position", null);
        if(startingPositionString == null) {
            throw new IllegalStateException("Invalid starting position from dashboard.");
        }
        final StartingPosition startingPosition = StartingPosition.valueOf(startingPositionString);
        return new AutonomousMode(matchDataPosition, startingPosition);
    }

    /**
     * Uses the gameSpecificMessage to set matchDataPosition
     * Also tells the dashboard all the possible StartingPositions
     */
    public static void initialize() {
        switch(DriverStation.getInstance().getGameSpecificMessage().charAt(0)) {
            case 'L':
                matchDataPosition = SwitchPosition.LEFT;
                break;
            case 'R':
                matchDataPosition = SwitchPosition.RIGHT;
                break;
            default:
                throw new IllegalStateException("Invalid game data");
        }

        final StartingPosition[] startingPositions = StartingPosition.values();
        final String[] startingPositionNames = new String[startingPositions.length];
        for(int i = 0; i < startingPositions.length; i++) {
            startingPositionNames[i] = startingPositions[i].name();
        }
        SmartDashboard.putStringArray("/SmartDashboard/autonomous/starting_positions", startingPositionNames);
    }
}
