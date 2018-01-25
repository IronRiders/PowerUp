package frc.team4180;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This enum defines the different modes we can do in autonomous time
 * It also provides some methods for checking if the driver has changed modes,
 * and for displaying modes to the driver.
 */
public enum AutonomousMode {
    /**
     * Don't move
     */
    DO_NOTHING,
    /**
     * Slowly move forward
     */
    JUST_DRIVE_FORWARD;

    /**
     * Keeps track of which mode we're in
     */
    private static AutonomousMode currentMode = DO_NOTHING;

    /**
     * Gets the current mode
     * @return the current mode
     */
    public static AutonomousMode getCurrentMode() {
        return currentMode;
    }

    /**
     * Set's the current mode, and logs this through the network tables
     * @param mode The mode to set currentMode to
     */
    public static void setCurrentMode(final AutonomousMode mode) {
        currentMode = mode;
        SmartDashboard.putString("/SmartDashboard/currentlySelectedMode", currentMode.name());
    }

    /**
     * Adds all the modes to NetworkTables, so they can be chosen from on the dashboard
     */
    public static void initializeNetworkTables() {
        final AutonomousMode[] modes = values();
        final String[] modeNames = new String[modes.length];
        for (int i = 0; i < modes.length; i++) {
            modeNames[i] = modes[i].name();
        }
        SmartDashboard.putStringArray("/SmartDashboard/autonomous/modes", modeNames);
    }

    /**
     * Queries NetworkTables to see if the mode has been changed.
     * If so, set the current mode
     * @return The new mode which has been set
     */
    public static AutonomousMode updateMode() {
        final String mode_name = SmartDashboard.getString("/SmartDashboard/autonomous/selected", currentMode.name());
        if(!mode_name.equals(currentMode.name())) {
            final AutonomousMode mode = valueOf(mode_name);
            setCurrentMode(mode);
        }
        return currentMode;
    }
}
