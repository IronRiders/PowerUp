package frc.team4180;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Objects of this class represents different states during autonomous time
 */
public class AutonomousMode {
    enum StartingPosition { LEFT, CENTER, RIGHT }
    enum SwitchPosition { LEFT, RIGHT }

    private static SwitchPosition matchDataPosition;
    public final StartingPosition startingPosition;
    public final SwitchPosition switchPosition;

    private AutonomousMode(SwitchPosition switchPosition, StartingPosition startingPosition) {
        this.switchPosition = switchPosition;
        this.startingPosition = startingPosition;
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
        String startingPositionString = SmartDashboard.getString("autonomous/selected_starting_position", null);
        if(startingPositionString == null) {
            startingPositionString = "CENTER";
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
                matchDataPosition =  null;
        }
        if(matchDataPosition != null) {
            SmartDashboard.putString("autonomous/switch_position", matchDataPosition.name());

        }
        final StartingPosition[] startingPositions = StartingPosition.values();
        final String[] startingPositionNames = new String[startingPositions.length];
        for(int i = 0; i < startingPositions.length; i++) {
            startingPositionNames[i] = startingPositions[i].name();
        }
        SmartDashboard.putStringArray("autonomous/starting_positions", startingPositionNames);
    }
}
