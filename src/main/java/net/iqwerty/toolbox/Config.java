package net.iqwerty.toolbox;

/**
 * Created by Michael on 10/10/2016.
 */

public class Config {
	public static final String APP = "Toolbox";
	public static final String PACKAGE_NAME = "net.iqwerty.toolbox";
	public static final String SERVICE_TOGGLE = PACKAGE_NAME + ".toggle";
	public static final String SERVICE_ON = PACKAGE_NAME + ".turnOn";
	public static final String SERVICE_OFF = PACKAGE_NAME + ".turnOff";

	public static final int MORNING = 7;
	public static final int NIGHT = 19;
	public static final int SCREENSHOT_DELAY = 1100;

	public static final int OVERLAY_TRANSITION_NONE = 0;
	public static final int OVERLAY_TRANSITION_SHORT = 1000;
	public static final int OVERLAY_TRANSITION_LONG = 15000;


	public static final int REQUEST_OVERLAY = 54321;
}
