package frc.team4180;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This enum defines the different modes we can do in autonomous time
 * It also provides some methods for checking if the driver has changed modes,
 * and for displaying modes to the driver.
 */
public class AutonomousMode {
    enum StartingPosition { LEFT, CENTER, RIGHT }
    enum SwitchPosition {LEFT , RIGHT}

    private StartingPosition startingPosition;
    private SwitchPosition switchPosition;

    public AutonomousMode(StartingPosition startingPosition , SwitchPosition switchPosition) {
        this.startingPosition = startingPosition;
        this.switchPosition = switchPosition;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AutonomousMode &&
                this.startingPosition.equals(((AutonomousMode) obj).startingPosition) &&
                this.switchPosition.equals(((AutonomousMode) obj).switchPosition);
    }

    /**
     * The entry in the backing NetworkTable which holds the driver's selected mode
     */
    private static final NetworkTableEntry selectedMode = SmartDashboard.getEntry("/SmartDashboard/autonomous/selected");

    /**
     * Gets the current mode from the backing network table
     * @return the current mode
     */
    public static AutonomousMode getCurrentMode() {
        return valueOf(selectedMode.getValue().getString());
    }

    /**
     * Sets the current entry in the backing NetworkTable
     * @param mode The mode to set currentMode to
     */
    public static void setCurrentMode(final AutonomousMode mode) {
        selectedMode.setString(mode.name());
    }

    /**
     * Adds all the modes to the backing NetworkTable, so they can be chosen from on the dashboard
     * Also sets the selected mode in the NetworkTable to
     */
    public static void initializeNetworkTables(final AutonomousMode initialMode) {
        final AutonomousMode[] modes = values();
        final String[] modeNames = new String[modes.length];
        for (int i = 0; i < modes.length; i++) {
            modeNames[i] = modes[i].name();
        }
        SmartDashboard.putStringArray("/SmartDashboard/autonomous/modes", modeNames);
        SmartDashboard.putString("/SmartDashboard/autonomous/selected", initialMode.name());
    }
}
