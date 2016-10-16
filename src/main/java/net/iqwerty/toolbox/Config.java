package net.iqwerty.toolbox;

/**
 * Created by Michael on 10/10/2016.
 */

class Config {
	static final boolean DEBUG = true;

	static final String APP = "Toolbox";
	static final String PACKAGE_NAME = "net.iqwerty.toolbox";
	static final String SERVICE_TOGGLE = service(".toggle");
	static final String SERVICE_ON = service(".turnOn");
	static final String SERVICE_OFF = service(".turnOff");

	static final int MORNING = 7;
	static final int NIGHT = 19;
	static final int SCREENSHOT_DELAY = 1100;
	static final int DEFAULT_FILTER_ALPLHA = 36;
	static final int DEFAULT_FILTER_RED = 255;
	static final int DEFAULT_FILTER_GREEN = 40;
	static final int DEFAULT_FILTER_BLUE = 30;

	static final int OVERLAY_TRANSITION_NONE = 0;
	static final int OVERLAY_TRANSITION_SHORT = 1000;
	static final int OVERLAY_TRANSITION_LONG = 15000;


	static final int REQUEST_OVERLAY = 54321;


	static final String SETTING_OVERLAY_COLOR_ALPHA = setting("alpha");
	static final String SETTING_OVERLAY_COLOR_RED = setting("red");
	static final String SETTING_OVERLAY_COLOR_GREEN = setting("green");
	static final String SETTING_OVERLAY_COLOR_BLUE = setting("blue");


	private static String service(final String suffix) {
		return PACKAGE_NAME + suffix;
	}
	private static String setting(final String key) {
		return "setting_" + key;
	}
}
