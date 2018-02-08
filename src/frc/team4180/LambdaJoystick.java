package frc.team4180;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj.Joystick;

public class LambdaJoystick extends Joystick {
    private Button[] buttons = new Button[11];
    private Consumer<ThrottlePosition> joystickListener;

    /**
     * Create a new Joystick Listener using lambdas given the USB port and the throttle listener
     * @param port USB port on the computer that the joystick is plugged in.
     * @param joystickListener Consumer of the Joystick's throttle position (x, y, and z).
     */
    public LambdaJoystick(final int port, final Consumer<ThrottlePosition> joystickListener) {
        super(port);
        this.joystickListener = joystickListener;
    }

    /**
     * Create a new Joystick Listener given the USB port.
     * @param port USB port on the computer that the joystick is plugged in.
     */
    public LambdaJoystick(final int port) {
        this(port, position -> {});
    }

    /**
     * Create a new button listener to run when a button on the joystick is pressed or released.
     * @param buttonNum The number of the button written on the joystick (1 - 10).
     * @param onPress Action to be executed when the button is pressed.
     * @param onRelease Action to be executed when the button is released.
     */
    public void addButton(final int buttonNum, final Runnable onPress, final Runnable onRelease) {
        buttons[buttonNum - 1] = new Button(onPress, onRelease);
    }

    /**
     * Create a new button listener to run when a button on the joystick is pressed.
     * @param buttonNum The number of the button written on the joystick (1 - 10).
     * @param onPress Action to be executed when the button is pressed.
     */
    public void addButton(final int buttonNum, final Runnable onPress) {
        addButton(buttonNum, onPress, () -> {});
    }

    /**
     * Listens to buttons and the throttle and calls action listeners
     */
    public void listen() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null) {
                buttons[i].listen(this.getRawButton(i + 1));
            }
        }
        joystickListener.accept(new ThrottlePosition(buffer(getX()), buffer(getY()), buffer(getZ())));
    }

    /**
     * Accounts for joystick error by rounding small numbers to 0.
     * @param d A joystick value from -1 to 1.
     * @return If d is within 0.03 of 0, d is rounded down to 0.
     */
    private static double buffer(final double d) {
        return (Math.abs(d) > 0.03) ? d : 0;
    }

    /**
     * Button listener holds runnables for when the button is pressed or released.
     */
    private class Button {
        public boolean currentState = false;
        public final Runnable onPress;
        public final Runnable onRelease;

        /**
         * Create a new Button listener given a Runnable for when the button is pressed or released.
         * @param onPress Runnable for when the button is pressed down.
         * @param onRelease Runnable for when the button is released.
         */
        public Button(final Runnable onPress, final Runnable onRelease) {
            this.onPress = onPress;
            this.onRelease = onRelease;
        }

        /**
         * Given the value of the button, the listener calls the appropriate runnable if it has changed.
         * @param newState The current state of the button.
         */
        public void listen(final boolean newState) {
            if (currentState != newState) {
                currentState = newState;
                if(newState) {
                    onPress.run();
                } else {
                    onRelease.run();
                }
            }
        }
    }

    /**
     * Throttle Position holds doubles x, y, and z representing the location of the throttle and dial.
     */
    public static class ThrottlePosition {
        public final double x, y, z;

        /**
         * Create a Throttle position given a (x, y) position of the throttle and the position of the dial (z).
         * @param x The right left position of the joystick (1 to -1).
         * @param y The vertical position of the joystick, 1 forwards, -1 is backwards (1 to -1).
         * @param z Position of the dial on the joystick (1 to -1).
         */
        public ThrottlePosition(final double x, final double y, final double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Create a Throttle position given a (x, y) position of the joystick throttle.
         * @param x The right left position of the joystick (1 to -1).
         * @param y The vertical position of the joystick, 1 forwards, -1 is backwards (1 to -1).
         */
        public ThrottlePosition(final double x, final double y) {
            this(x, y, 0);
        }
    }
}

