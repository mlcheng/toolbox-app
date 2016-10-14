package net.iqwerty.toolbox;

import android.util.Log;

/**
 * Created by Michael on 10/10/2016.
 */

class Logger {
	static void log(final String message) {
		if (!Config.DEBUG) return;
		
		Log.v(Config.APP, message);
	}
}
