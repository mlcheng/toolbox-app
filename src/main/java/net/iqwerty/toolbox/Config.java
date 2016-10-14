package net.iqwerty.toolbox;

/**
 * Created by Michael on 10/10/2016.
 */

class Config {
	static final boolean DEBUG = true;

	static final String APP = "Toolbox";
	static final String PACKAGE_NAME = "net.iqwerty.toolbox";
	static final String SERVICE_TOGGLE = PACKAGE_NAME + ".toggle";
	static final String SERVICE_ON = PACKAGE_NAME + ".turnOn";
	static final String SERVICE_OFF = PACKAGE_NAME + ".turnOff";

	static final int MORNING = 7;
	static final int NIGHT = 19;
	static final int SCREENSHOT_DELAY = 1100;

	static final int OVERLAY_TRANSITION_NONE = 0;
	static final int OVERLAY_TRANSITION_SHORT = 1000;
	static final int OVERLAY_TRANSITION_LONG = 15000;


	static final int REQUEST_OVERLAY = 54321;
}
