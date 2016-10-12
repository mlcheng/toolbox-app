package net.iqwerty.toolbox;

import android.util.Log;

/**
 * Created by Michael on 10/10/2016.
 */

class Logger {
	static int log(final String message) {
		return Log.v(Config.APP, message);
	}
}
